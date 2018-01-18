/**
 * Copyright (c) Codice Foundation
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package org.codice.countrycode.mapping;

import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.codice.countrycode.converter.MappingStrategy;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standard.StandardRegistry;
import org.codice.countrycode.standard.StandardRegistryImpl;
import org.codice.countrycode.standards.common.StandardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvMappingStrategy implements MappingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(CsvMappingStrategy.class);

  private static final String DEFAULT_FILE_PATH = "mappings.csv";

  private static final int CSV_STANDARD_LINE_START = 0;

  private static final int CSV_STANDARD_LINE_END = 2;

  private static final int CSV_MAPPINGS_LINE_START = 2;

  private final List<StandardPropertyPair> configStandardPropertyPairs;

  private final Set<Set<CountryCode>> countryCodeMappings;

  private final StandardRegistry standardRegistry;

  private final Path filePath;

  public CsvMappingStrategy() {
    this(DEFAULT_FILE_PATH);
  }

  public CsvMappingStrategy(String file) {
    this(file, StandardRegistryImpl.getInstance());
  }

  public CsvMappingStrategy(String file, StandardRegistry standardRegistry) {
    Validate.notEmpty(file, "argument [file] cannot be null or empty.");
    configStandardPropertyPairs = new ArrayList<>();
    countryCodeMappings = new HashSet<>();
    this.standardRegistry = standardRegistry;

    URL fileUrl = this.getClass().getClassLoader().getResource(file);
    if (fileUrl == null) {
      LOGGER.debug("Unable to get file for [{}].", file);
      throw new IllegalArgumentException(String.format("Unable to get file for [%s]", file));
    }

    try {
      filePath = Paths.get(new URI(fileUrl.toString()));
    } catch (URISyntaxException e) {
      LOGGER.debug("Error creating path to [{}].", file, e);
      throw new IllegalArgumentException(String.format("Unable to get path for file [%s]", file));
    }

    List<String> lines = getFileLines();
    if (!parseConfigStandardsAndMappingProperties(
        lines.subList(CSV_STANDARD_LINE_START, CSV_STANDARD_LINE_END))) {
      LOGGER.debug("Failed to parse standards from [{}].", filePath.toString());
      throw new IllegalStateException(
          String.format("Failed to parse standards from [%s].", filePath.toString()));
    }

    if (!parseMappings(lines.subList(CSV_MAPPINGS_LINE_START, lines.size()))) {
      LOGGER.debug("Failed to parse mappings from [{}].", filePath.toString());
      throw new IllegalStateException(
          String.format("Failed to parse mappings from [%s].", filePath.toString()));
    }
  }

  @Override
  public Set<Set<CountryCode>> getMappings() {
    return ImmutableSet.copyOf(countryCodeMappings);
  }

  @Override
  public Set<Standard> getMappedStandards() {
    return ImmutableSet.copyOf(
        configStandardPropertyPairs
            .stream()
            .map(StandardPropertyPair::getStandard)
            .collect(Collectors.toSet()));
  }

  @Override
  public Set<CountryCode> getMappingFor(final Standard standard, final String value) {
    for (Set<CountryCode> mapping : countryCodeMappings) {
      Optional<StandardPropertyPair> pairOptional =
          configStandardPropertyPairs
              .stream()
              .filter(def -> StandardUtils.equalStandards(def.getStandard(), standard))
              .findFirst();

      if (pairOptional.isPresent()) {
        final StandardPropertyPair standardPropertyPair = pairOptional.get();
        Optional<CountryCode> countryCode =
            mapping
                .stream()
                .filter(
                    cc -> {
                      String format = standardPropertyPair.getMappingProperty();
                      Standard defStandard = standardPropertyPair.getStandard();

                      return cc.getStandard().getFormatNames().contains(format)
                          && StandardUtils.equalStandards(standard, defStandard)
                          && StandardUtils.containsFormatValue(cc, value);
                    })
                .findFirst();

        if (countryCode.isPresent()) {
          Set<CountryCode> mappingCopy = new HashSet<>(mapping);
          for(CountryCode code : mapping) {
            if(StandardUtils.hasStandard(code, standard)) {
              mappingCopy.remove(code);
            }
          }

          return ImmutableSet.copyOf(mappingCopy);
        }
      }
    }

    return Collections.emptySet();
  }

  private boolean parseMappings(List<String> mappings) {
    boolean success = true;

    if (mappings.isEmpty()) {
      LOGGER.error("Configuration [{}] must have at least 1 mapping.", filePath.toString());
      throw new IllegalStateException(
          String.format("Configuration [%s] must have at least 1 mapping.", filePath.toString()));
    }

    for (String mapping : mappings) {
      String[] propertyValues = mapping.split(",");

      if (propertyValues.length <= 1) {
        LOGGER.error("Mapping error, mappings must specify more that one value: [{}]", mapping);
        success = false;
        continue;
      }

      Set<CountryCode> currentMapping = new HashSet<>();
      for (int i = 0; i < propertyValues.length; i++) {
        Standard propertyStandard = configStandardPropertyPairs.get(i).getStandard();
        Optional<StandardPropertyPair> definitionOptional =
            configStandardPropertyPairs
                .stream()
                .filter(def -> StandardUtils.equalStandards(def.getStandard(), propertyStandard))
                .findFirst();
        String propertyValue = propertyValues[i].trim();
        if (StringUtils.isEmpty(propertyValue)) {
          LOGGER.debug(
              "No mapping value provided for standard [{} {}].",
              propertyStandard.getName(),
              propertyStandard.getVersion());
          continue;
        }

        if (!definitionOptional.isPresent()) {
          LOGGER.error("No standard definition for property value [{}].", propertyValue);
          success = false;
          break;
        }

        StandardProvider mappingProvider =
            standardRegistry.lookup(propertyStandard.getName(), propertyStandard.getVersion());
        if (mappingProvider == null) {
          LOGGER.error("Unable to find standard for property value [{}].", propertyValue);
          success = false;
          break;
        }

        Set<CountryCode> providerCodes = mappingProvider.getStandardEntries();
        StandardPropertyPair definition = definitionOptional.get();
        Optional<CountryCode> code = getCountryCodeFor(propertyValue, definition, providerCodes);
        if (!code.isPresent()) {
          LOGGER.error(
              "Standard [{} {}] did not have a code with a [{}] mapping property of value [{}].",
              propertyStandard.getName(),
              propertyStandard.getVersion(),
              definition.getMappingProperty(),
              propertyValue);
          success = false;
          break;
        }

        currentMapping.add(code.get());
      }

      countryCodeMappings.add(currentMapping);
    }

    return success;
  }

  private boolean parseConfigStandardsAndMappingProperties(List<String> lines) {
    boolean success = true;
    String[] configStandards = lines.get(0).split(",");

    if (configStandards.length <= 1) {
      LOGGER.error("CSV configuration file must have at least 2 mapped standards.");
      throw new IllegalStateException(
          "CSV configuration file must have at least 2 mapped standards.");
    }

    List<Standard> definedConfigStandards = new ArrayList<>();
    for (String standard : configStandards) {
      String[] standardParts = standard.split(":");
      if (standardParts.length <= 1) {
        LOGGER.error(
            "Failed to parse standard [{}]. Must specify name and version separated by a colon.",
            standard);
        success = false;
        continue;
      }

      String standardName = standardParts[0].trim();
      String standardVersion = standardParts[1].trim();

      StandardProvider standardProvider = standardRegistry.lookup(standardName, standardVersion);
      if (standardProvider == null) {
        LOGGER.error(
            "Standard [{} {}] is not a supported standard.", standardName, standardVersion);
        success = false;
        continue;
      }

      definedConfigStandards.add(standardProvider.getStandard());
    }

    // return if we failed to parse standards
    if (!success) {
      return false;
    }

    String[] mappingProperties = lines.get(1).split(",");
    if (mappingProperties.length != definedConfigStandards.size()) {
      LOGGER.error(
          "Every defined standard must have a mapping property supported by that standard.");
      throw new IllegalStateException("Every defined standard must have a mapping property.");
    }

    for (int i = 0; i < mappingProperties.length; i++) {
      String mappingProperty = mappingProperties[i].trim();
      Standard defStandard = definedConfigStandards.get(i);

      if (defStandard.getFormatNames().contains(mappingProperty)) {
        configStandardPropertyPairs.add(new StandardPropertyPair(defStandard, mappingProperty));
      } else {
        LOGGER.error(
            "Invalid standard mapping property [{}]. Valid values are {}.",
            mappingProperty,
            defStandard.getFormatNames());
        success = false;
      }
    }

    return success;
  }

  private Optional<CountryCode> getCountryCodeFor(
      String propertyValue, StandardPropertyPair definition, Set<CountryCode> providerCodes) {
    return providerCodes
        .stream()
        .filter(cc -> cc.getAsFormat(definition.getMappingProperty()).equals(propertyValue))
        .findFirst();
  }

  private List<String> getFileLines() {
    List<String> fileLines;

    try {
      try (Stream<String> stream = Files.lines(filePath)) {
        fileLines = stream.collect(Collectors.toList());
      }
    } catch (IOException e) {
      LOGGER.debug("Error parsing CSV configuration file [{}].", filePath.toString(), e);
      throw new IllegalStateException(
          String.format("Error parsing CSV configuration file [%s].", filePath.toString()));
    }

    if (fileLines.isEmpty()) {
      LOGGER.error("File contents empty for [{}].", filePath.toString());
      throw new IllegalStateException(
          String.format("File contents empty for [%s].", filePath.toAbsolutePath()));
    }

    return fileLines;
  }

  private class StandardPropertyPair {
    private final Standard standard;

    private final String mappingProperty;

    StandardPropertyPair(Standard standard, String mappingProperty) {
      this.standard = standard;
      this.mappingProperty = mappingProperty;
    }

    Standard getStandard() {
      return standard;
    }

    String getMappingProperty() {
      return mappingProperty;
    }
  }
}

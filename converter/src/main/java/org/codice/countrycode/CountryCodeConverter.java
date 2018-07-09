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
package org.codice.countrycode;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.codice.countrycode.converter.Converter;
import org.codice.countrycode.converter.MappingStrategy;
import org.codice.countrycode.mapping.CsvMappingStrategy;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standards.common.StandardUtils;
import org.codice.countrycode.standards.iso.Iso3166Standard;

public class CountryCodeConverter implements Converter {

  private final Standard defaultStandard;

  private MappingStrategy mappingStrategy;

  public CountryCodeConverter() {
    this(new Iso3166Standard());
  }

  public CountryCodeConverter(Standard defaultStandard) {
    this(defaultStandard, new CsvMappingStrategy());
  }

  public CountryCodeConverter(Standard defaultStandard, MappingStrategy mappingStrategy) {
    this.defaultStandard = defaultStandard;
    this.mappingStrategy = mappingStrategy;

    if (!mappingStrategyMapsStandard(defaultStandard)) {
      throw new IllegalArgumentException(
          String.format(
              "Provided default standard [%s %s] not supported by default CsvMappingStrategy.",
              defaultStandard.getName(), defaultStandard.getVersion()));
    }
  }

  private boolean mappingStrategyMapsStandard(Standard defaultStandard) {
    for (Standard standard : mappingStrategy.getMappedStandards()) {
      if (standard.equals(defaultStandard)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<CountryCode> fromAlpha2(String alpha2, Standard from, Standard to) {
    return fromProperty(alpha2, from, to);
  }

  @Override
  public Set<CountryCode> fromAlpha3(String alpha3, Standard from, Standard to) {
    return fromProperty(alpha3, from, to);
  }

  @Override
  public Set<CountryCode> fromNumeric(String numeric, Standard from, Standard to) {
    return fromProperty(numeric, from, to);
  }

  @Override
  public Set<Standard> getSupportedStandards() {
    return mappingStrategy.getMappedStandards();
  }

  @Override
  public Standard getSystemDefaultStandard() {
    return defaultStandard;
  }

  private Set<CountryCode> fromProperty(String propertyValue, Standard from, Standard to) {
    if (from.equals(to)) {
      Set<CountryCode> fromCountryCodes =
          mappingStrategy
              .getMappings()
              .stream()
              .flatMap(Set::stream)
              .filter(
                  cc ->
                      StandardUtils.hasStandard(cc, from)
                          && StandardUtils.containsFormatValue(cc, propertyValue))
              .collect(Collectors.toSet());

      return ImmutableSet.copyOf(fromCountryCodes);
    }

    Set<CountryCode> mappings = mappingStrategy.getMappingFor(from, propertyValue);
    return ImmutableSet.copyOf(
        mappings
            .stream()
            .filter(cc -> StandardUtils.hasStandard(cc, to))
            .collect(Collectors.toSet()));
  }
}

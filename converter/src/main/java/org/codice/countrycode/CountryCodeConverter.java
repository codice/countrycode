package org.codice.countrycode;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.Optional;
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
              defaultStandard.getName(),
              defaultStandard.getVersion()));
    }
  }

  private boolean mappingStrategyMapsStandard(Standard defaultStandard) {
    for(Standard standard : mappingStrategy.getMappedStandards()) {
      if(StandardUtils.equalStandards(standard, defaultStandard)) {
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
    if (StandardUtils.equalStandards(from, to)) {
      Optional<Set<CountryCode>> fromCountryCodeWithValue =
          mappingStrategy
              .getMappings()
              .stream()
              .filter(
                  mapping ->
                      mapping
                          .stream()
                          .anyMatch(
                              cc ->
                                  StandardUtils.hasStandard(cc, from)
                                      && StandardUtils.containsFormatValue(cc, propertyValue)))
              .findFirst();

      if (fromCountryCodeWithValue.isPresent()) {
        return ImmutableSet.copyOf(
            fromCountryCodeWithValue
                .get()
                .stream()
                .filter(
                    cc ->
                        StandardUtils.hasStandard(cc, from)
                            && StandardUtils.containsFormatValue(cc, propertyValue))
                .collect(Collectors.toSet()));
      }

      return Collections.emptySet();
    }

    Set<CountryCode> mappings = mappingStrategy.getMappingFor(from, propertyValue);
    return mappings
        .stream()
        .filter(cc -> StandardUtils.hasStandard(cc, to))
        .collect(Collectors.toSet());
  }
}

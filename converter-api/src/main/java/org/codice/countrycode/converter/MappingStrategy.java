package org.codice.countrycode.converter;

import java.util.Set;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;

public interface MappingStrategy {

  /**
   * A Set of mappings supported by this mapping strategy
   *
   * @return the set of country code mappings or an empty set if there are none
   */
  Set<Set<CountryCode>> getMappings();

  /**
   * A set of standards that this mapping strategy supports mappings between.
   *
   * @return a set of standards, or an empty set if none are supported
   */
  Set<Standard> getMappedStandards();

  Set<CountryCode> getMappingFor(Standard standard, String value);
}

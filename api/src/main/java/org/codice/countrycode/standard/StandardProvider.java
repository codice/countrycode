package org.codice.countrycode.standard;

import java.util.Set;

public interface StandardProvider {

  /**
   * The {@link Standard} for which the {@code CountryCode}s belong to.
   *
   * @return the standard, cannot be null
   */
  Standard getStandard();

  /**
   * A set of {@code CountryCode}s for this provider's {@code Standard}. The standard returned by
   * each country code in the set must be equal to this provider's standard.
   *
   * @return country codes for this provider's standard, or empty if there are none or there was an
   *     error reading the source
   */
  Set<CountryCode> getStandardEntries();
}

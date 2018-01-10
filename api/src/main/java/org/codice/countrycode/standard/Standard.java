package org.codice.countrycode.standard;

import java.util.Set;

/**
 * A country code standard. The name and version of this standard should be unique from all other
 * standards.
 */
public interface Standard {

  /**
   * Returns the name of this standard.
   *
   * @return the standard's name, cannot be null or empty string
   */
  String getName();

  /**
   * The version of this country code standard.
   *
   * @return the version, or null if a version is not applicable
   */
  String getVersion();

  /**
   * Returns a list of formats available for this standard. For example, a standard that supports
   * alpha 2 country codes, like ISO 3166-1, may have a format of "alpha2". Other examples include
   * "numeric", "alpha3", etc.
   *
   * @return a set of standard formats
   */
  Set<String> getFormatNames();
}

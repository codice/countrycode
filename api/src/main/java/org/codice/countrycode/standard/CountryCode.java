package org.codice.countrycode.standard;

/** Represents an entry for any given standard (FIPS 10-4, ISO 3166, GENC, etc). */
public interface CountryCode {

  /**
   * Returns the country code in a specific format. Supported format names for this entry are
   * defined by this entry's {@link Standard#getFormatNames()}.
   *
   * @param formatName name of format to get this entry in
   * @return the format, or null if not a supported format
   */
  String getAsFormat(String formatName);

  /**
   * Returns the country name, entity, organization, etc. identified by this {@code CountryCode}.
   * For example, a FIPS country code with an alpha2 code of "AF" would correspond to the name
   * "AFGHANISTAN".
   *
   * @return the name, cannot be null
   */
  String getName();

  /**
   * Returns this {@code CountryCode}s standard
   *
   * @return the standard, cannot return null
   */
  Standard getStandard();
}

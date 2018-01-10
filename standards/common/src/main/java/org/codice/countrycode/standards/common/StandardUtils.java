package org.codice.countrycode.standards.common;

import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;

public class StandardUtils {

  private StandardUtils() {}

  public static boolean hasStandard(CountryCode countryCode, Standard standard) {
    Standard countryCodeStandard = countryCode.getStandard();
    return countryCodeStandard.getName().equals(standard.getName())
        && countryCodeStandard.getVersion().equals(standard.getVersion());
  }

  public static boolean equalStandards(Standard first, Standard second) {
    return first.getName().equalsIgnoreCase(second.getName())
        && first.getVersion().equalsIgnoreCase(second.getVersion());
  }

  public static boolean containsFormatValue(CountryCode countryCode, String value) {
    return countryCode
        .getStandard()
        .getFormatNames()
        .stream()
        .anyMatch(formatName -> countryCode.getAsFormat(formatName).equalsIgnoreCase(value));
  }
}

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

import static org.codice.countrycode.CountryCodeSimple.Format.ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.Format.ALPHA3;
import static org.codice.countrycode.CountryCodeSimple.Format.NUMERIC;
import static org.codice.countrycode.CountryCodeSimple.Standard.FIPS_10_4;
import static org.codice.countrycode.CountryCodeSimple.Standard.GEC_18;
import static org.codice.countrycode.CountryCodeSimple.Standard.GENC_3_0_0;
import static org.codice.countrycode.CountryCodeSimple.Standard.ISO_3166_1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.codice.countrycode.converter.Converter;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standard.StandardRegistry;
import org.codice.countrycode.standard.StandardRegistryImpl;

/**
 * Simple interface for the typical use case of converting between the following standards: ISO
 * 3166-1, GENC 3.0.0, and FIPS 10-4.
 *
 * <pre>
 *   String countryCode = CountryCodeSimple.convert(&quot;CH&quot;, FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3)
 *   assert countryCode.equals(&quot;CHN&quot;);
 * </pre>
 */
public class CountryCodeSimple {

  private static final String FIPS = "FIPS";
  private static final String FIPS_VERSION = "10-4";

  private static final String ISO = "ISO3166";
  private static final String ISO_VERSION = "1";

  private static final String GENC = "GENC";
  private static final String GENC_VERSION = "3.0.0";

  private static final String GEC = "GEC";
  private static final String GEC_VERSION = "18";

  private static final StandardProvider FIPS_STANDARD;
  private static final StandardProvider ISO_STANDARD;
  private static final StandardProvider GENC_STANDARD;
  private static final StandardProvider GEC_STANDARD;

  private static final Converter CONVERTER = new CountryCodeConverter();

  static {
    StandardRegistry registry = StandardRegistryImpl.getInstance();
    FIPS_STANDARD = registry.lookup(FIPS, FIPS_VERSION);
    ISO_STANDARD = registry.lookup(ISO, ISO_VERSION);
    GENC_STANDARD = registry.lookup(GENC, GENC_VERSION);
    GEC_STANDARD = registry.lookup(GEC, GEC_VERSION);
  }

  public enum StandardFormat {
    FIPS_10_4_ALPHA2(FIPS_10_4, ALPHA2),
    GEC_18_ALPHA2(GEC_18, ALPHA2),
    ISO_3166_1_ALPHA2(ISO_3166_1, ALPHA2),
    ISO_3166_1_ALPHA3(ISO_3166_1, ALPHA3),
    ISO_3166_1_NUMERIC(ISO_3166_1, NUMERIC),
    GENC_3_0_0_ALPHA2(GENC_3_0_0, ALPHA2),
    GENC_3_0_0_ALPHA3(GENC_3_0_0, ALPHA3),
    GENC_3_0_0_NUMERIC(GENC_3_0_0, NUMERIC);

    private final Standard standard;
    private final Format format;

    StandardFormat(Standard standard, Format format) {
      this.standard = standard;
      this.format = format;
    }

    public Standard getStandard() {
      return standard;
    }

    public Format getFormat() {
      return format;
    }
  }

  public enum Standard {
    FIPS_10_4(FIPS, FIPS_VERSION, FIPS_STANDARD),
    ISO_3166_1(ISO, ISO_VERSION, ISO_STANDARD),
    GENC_3_0_0(GENC, GENC_VERSION, GENC_STANDARD),
    GEC_18(GEC, GEC_VERSION, GEC_STANDARD);

    private final String name;
    private final String version;
    private final StandardProvider standardProvider;

    Standard(String name, String version, StandardProvider standardProvider) {
      this.name = name;
      this.version = version;
      this.standardProvider = standardProvider;
    }

    private org.codice.countrycode.standard.Standard getStandard() {
      return standardProvider.getStandard();
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }
  }

  public enum Format {
    ALPHA2("alpha2"),
    ALPHA3("alpha3"),
    NUMERIC("numeric");

    private final String formatString;

    Format(String formatString) {
      this.formatString = formatString;
    }
  }

  /** Don't let anyone instantiate this class */
  private CountryCodeSimple() {}

  public static Set<String> convert(String countryCode, StandardFormat from, StandardFormat to) {

    if (countryCode == null || countryCode.isEmpty()) {
      return Collections.emptySet();
    }

    Set<CountryCode> countryCodes;

    if (from.getStandard().equals(to.getStandard())) {
      countryCodes = useProvider(countryCode, from);
    } else {
      countryCodes = useConverter(countryCode, from, to);
    }

    return getAsFormat(countryCodes, to.format);
  }

  private static Set<CountryCode> useProvider(String countryCode, StandardFormat from) {
    for (CountryCode cc : from.standard.standardProvider.getStandardEntries()) {
      String codeAsFormat = cc.getAsFormat(from.format.formatString);
      if (codeAsFormat != null && codeAsFormat.equalsIgnoreCase(countryCode)) {
        return Collections.singleton(cc);
      }
    }
    return Collections.emptySet();
  }

  private static Set<CountryCode> useConverter(
      String countryCode, StandardFormat from, StandardFormat to) {
    switch (from.format) {
      case ALPHA2:
        return CONVERTER.fromAlpha2(
            countryCode, from.standard.getStandard(), to.standard.getStandard());
      case ALPHA3:
        return CONVERTER.fromAlpha3(
            countryCode, from.standard.getStandard(), to.standard.getStandard());
      case NUMERIC:
        return CONVERTER.fromNumeric(
            countryCode, from.standard.getStandard(), to.standard.getStandard());
    }
    return Collections.emptySet();
  }

  private static Set<String> getAsFormat(Set<CountryCode> countryCodes, Format format) {
    Set<String> codes = new HashSet<>();
    for (CountryCode code : countryCodes) {
      String codeAsFormat = code.getAsFormat(format.formatString);
      if (codeAsFormat != null) {
        codes.add(codeAsFormat);
      }
    }
    return codes;
  }
}

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

import static org.codice.countrycode.CountryCodeSimple.StandardFormat.FIPS_10_4_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_ALPHA3;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_NUMERIC;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_ALPHA3;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_NUMERIC;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;

// these are essentially library-wide tests
public class CountryCodeSimpleTest {

  @Test
  public void testConversion() {
    assertEquals(
        "CHN",
        CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3).iterator().next());
    assertEquals(
        "AQ",
        CountryCodeSimple.convert("ATA", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2).iterator().next());
    assertEquals(
        "BS",
        CountryCodeSimple.convert("BHS", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2).iterator().next());
  }

  @Test
  public void testConversion2() {
    Set<String> result = CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("CN", result.iterator().next());
  }

  @Test
  public void testSameStandardWithMentionInMappingsCSV() {
    Set<String> result = CountryCodeSimple.convert("CN", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertEquals(1, result.size());
    assertEquals("CHN", result.iterator().next());
  }

  @Test
  public void testSameStandardWithNoMentionInMappingsCSV() {
    Set<String> result = CountryCodeSimple.convert("BES", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("BQ", result.iterator().next());
  }

  @Test
  public void testInvalidConversionAlpha2ButUsingAlpha3Code() {
    Set<String> result = CountryCodeSimple.convert("CHE", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertEquals(0, result.size());
  }

  // todo this demonstrates a bug where the input format is ignored by the converter impl
  // we should fix this bug
  @Test
  @Ignore
  public void testInvalidConversionFromNumeric() {
    Set<String> result = CountryCodeSimple.convert("CH", GENC_3_0_0_NUMERIC, ISO_3166_1_NUMERIC);
    assertEquals(0, result.size());
  }
}

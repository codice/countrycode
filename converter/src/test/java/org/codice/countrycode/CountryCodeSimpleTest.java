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
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GEC_18_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_ALPHA3;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.GENC_3_0_0_NUMERIC;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_ALPHA2;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_ALPHA3;
import static org.codice.countrycode.CountryCodeSimple.StandardFormat.ISO_3166_1_NUMERIC;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.codice.countrycode.CountryCodeSimple.Format;
import org.codice.countrycode.CountryCodeSimple.Standard;
import org.codice.countrycode.CountryCodeSimple.StandardFormat;
import org.junit.Ignore;
import org.junit.Test;

// these are essentially library-wide tests
public class CountryCodeSimpleTest {

  @Test
  public void testConversion() {
    assertThat(
        "CHN",
        equalTo(
            CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3)
                .iterator()
                .next()));
    assertThat(
        "CHN",
        equalTo(
            CountryCodeSimple.convert("CH", GEC_18_ALPHA2, ISO_3166_1_ALPHA3).iterator().next()));
    assertThat(
        "AQ",
        equalTo(
            CountryCodeSimple.convert("ATA", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2)
                .iterator()
                .next()));
    assertThat(
        "BS",
        equalTo(
            CountryCodeSimple.convert("BHS", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2)
                .iterator()
                .next()));
  }

  @Test
  public void testConversion2() {
    Set<String> result = CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("CN", equalTo(result.iterator().next()));
  }

  @Test
  public void testSameStandardWithMentionInMappingsCSV() {
    Set<String> result = CountryCodeSimple.convert("CN", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertThat(1, equalTo(result.size()));
    assertThat("CHN", equalTo(result.iterator().next()));
  }

  @Test
  public void testSameStandardWithNoMentionInMappingsCSV() {
    Set<String> result = CountryCodeSimple.convert("BES", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("BQ", equalTo(result.iterator().next()));
  }

  @Test
  public void testInvalidConversionAlpha2ButUsingAlpha3Code() {
    Set<String> result = CountryCodeSimple.convert("CHE", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertThat(0, equalTo(result.size()));
  }

  @Test
  public void testConversionNoNumericCode() {
    Set<String> result = CountryCodeSimple.convert("IP", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("CP", equalTo(result.iterator().next()));

    result = CountryCodeSimple.convert("IP", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3);
    assertThat(1, equalTo(result.size()));
    assertThat("CPT", equalTo(result.iterator().next()));

    result = CountryCodeSimple.convert("IP", FIPS_10_4_ALPHA2, ISO_3166_1_NUMERIC);
    assertThat(0, equalTo(result.size()));

    result = CountryCodeSimple.convert("CP", ISO_3166_1_ALPHA2, FIPS_10_4_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("IP", equalTo(result.iterator().next()));

    result = CountryCodeSimple.convert("CPT", ISO_3166_1_ALPHA3, FIPS_10_4_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("IP", equalTo(result.iterator().next()));
  }

  @Test
  public void testConversionNonExistentCode() {
    Set<String> result =
        CountryCodeSimple.convert(
            "ZZ", StandardFormat.ISO_3166_1_ALPHA2, StandardFormat.GENC_3_0_0_ALPHA3);
    assertThat(0, equalTo(result.size()));
  }

  // todo this demonstrates a bug where the input format is ignored by the converter impl
  // we should fix this bug
  @Test
  @Ignore
  public void testInvalidConversionFromNumeric() {
    Set<String> result = CountryCodeSimple.convert("CH", GENC_3_0_0_NUMERIC, ISO_3166_1_NUMERIC);
    assertEquals(0, result.size());
  }

  @Test
  public void testConvertNullReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert(null, FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3);
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertEmptyStringReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert("", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3);
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertNumericBetweenStandards() {
    // Cross-standard numeric conversion not supported by default mappings.csv
    Set<String> result = CountryCodeSimple.convert("156", GENC_3_0_0_NUMERIC, ISO_3166_1_ALPHA3);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertGencAlpha3ToIso() {
    Set<String> result = CountryCodeSimple.convert("CHN", GENC_3_0_0_ALPHA3, ISO_3166_1_ALPHA3);
    assertEquals(1, result.size());
    assertEquals("CHN", result.iterator().next());
  }

  @Test
  public void testConvertGencAlpha2ToFips() {
    Set<String> result = CountryCodeSimple.convert("CN", GENC_3_0_0_ALPHA2, FIPS_10_4_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("CH", result.iterator().next());
  }

  @Test
  public void testConvertIsoAlpha2ToGec() {
    Set<String> result = CountryCodeSimple.convert("CN", ISO_3166_1_ALPHA2, GEC_18_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("CH", result.iterator().next());
  }

  @Test
  public void testConvertGecToGenc() {
    Set<String> result = CountryCodeSimple.convert("CH", GEC_18_ALPHA2, GENC_3_0_0_ALPHA3);
    assertEquals(1, result.size());
    assertEquals("CHN", result.iterator().next());
  }

  @Test
  public void testConvertIsoNumericToIsoAlpha2() {
    Set<String> result = CountryCodeSimple.convert("156", ISO_3166_1_NUMERIC, ISO_3166_1_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("CN", result.iterator().next());
  }

  @Test
  public void testConvertIsoNumericToGencAlpha3() {
    Set<String> result = CountryCodeSimple.convert("156", ISO_3166_1_NUMERIC, GENC_3_0_0_ALPHA3);
    assertEquals(1, result.size());
    assertEquals("CHN", result.iterator().next());
  }

  @Test
  public void testConvertSameStandardAlpha2ToNumeric() {
    Set<String> result = CountryCodeSimple.convert("CN", ISO_3166_1_ALPHA2, ISO_3166_1_NUMERIC);
    assertEquals(1, result.size());
    assertEquals("156", result.iterator().next());
  }

  @Test
  public void testConvertFipsToGenc() {
    Set<String> result = CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, GENC_3_0_0_ALPHA3);
    assertEquals(1, result.size());
    assertEquals("CHN", result.iterator().next());
  }

  @Test
  public void testConvertFipsToGencAlpha2() {
    Set<String> result = CountryCodeSimple.convert("CH", FIPS_10_4_ALPHA2, GENC_3_0_0_ALPHA2);
    assertEquals(1, result.size());
    assertEquals("CN", result.iterator().next());
  }

  @Test
  public void testStandardEnumValues() {
    for (Standard standard : Standard.values()) {
      assertNotNull(standard.getName());
      assertNotNull(standard.getVersion());
    }
  }

  @Test
  public void testFormatEnumToString() {
    assertEquals("alpha2", Format.ALPHA2.toString());
    assertEquals("alpha3", Format.ALPHA3.toString());
    assertEquals("numeric", Format.NUMERIC.toString());
  }

  @Test
  public void testStandardFormatGetters() {
    for (StandardFormat sf : StandardFormat.values()) {
      assertNotNull(sf.getStandard());
      assertNotNull(sf.getFormat());
    }
  }

  @Test
  public void testConvertNonExistentCodeReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert("ZZZ", GENC_3_0_0_ALPHA3, ISO_3166_1_ALPHA3);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertNonExistentFipsReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert("ZZ", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertNonExistentGecReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert("ZZ", GEC_18_ALPHA2, ISO_3166_1_ALPHA3);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertSameStandardNonExistentReturnsEmpty() {
    Set<String> result = CountryCodeSimple.convert("ZZZ", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testConvertGencNumericToFips() {
    // Cross-standard numeric conversion not supported by default mappings.csv
    Set<String> result = CountryCodeSimple.convert("156", GENC_3_0_0_NUMERIC, FIPS_10_4_ALPHA2);
    assertTrue(result.isEmpty());
  }
}

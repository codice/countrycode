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
import static org.codice.countrycode.CountryCodeSimple.convert;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.codice.countrycode.CountryCodeSimple.StandardFormat;
import org.junit.Ignore;
import org.junit.Test;

// these are essentially library-wide tests
public class CountryCodeSimpleTest {

  @Test
  public void testConversion() {
    assertThat(
        "CHN", equalTo(convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA3).iterator().next()));
    assertThat(
        "AQ", equalTo(convert("ATA", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2).iterator().next()));
    assertThat(
        "BS", equalTo(convert("BHS", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2).iterator().next()));
  }

  @Test
  public void testConversion2() {
    Set<String> result = convert("CH", FIPS_10_4_ALPHA2, ISO_3166_1_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("CN", equalTo(result.iterator().next()));
  }

  @Test
  public void testSameStandardWithMentionInMappingsCSV() {
    Set<String> result = convert("CN", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertThat(1, equalTo(result.size()));
    assertThat("CHN", equalTo(result.iterator().next()));
  }

  @Test
  public void testSameStandardWithNoMentionInMappingsCSV() {
    Set<String> result = convert("BES", ISO_3166_1_ALPHA3, ISO_3166_1_ALPHA2);
    assertThat(1, equalTo(result.size()));
    assertThat("BQ", equalTo(result.iterator().next()));
  }

  @Test
  public void testInvalidConversionAlpha2ButUsingAlpha3Code() {
    Set<String> result = convert("CHE", GENC_3_0_0_ALPHA2, GENC_3_0_0_ALPHA3);
    assertThat(0, equalTo(result.size()));
  }

  private List sort(Set<String> s) {
    List<String> arrayList = new ArrayList<>(s);
    Collections.sort(arrayList);
    return arrayList;
  }

  @Test
  public void verifyConversionToSameStandardFormatIsSafe() {
    for (StandardFormat standardFormat : StandardFormat.values()) {
      for (String cc : standardFormat.allCodes()) {
        assertThat(Collections.singleton(cc), equalTo(convert(cc, standardFormat, standardFormat)));
      }
    }
  }

  @Test
  public void verifyGencMistakenAsIsoReturnsSameCodeOrNothingAlpha2() {
    for (String cc : GENC_3_0_0_ALPHA2.allCodes()) {
      Set<String> convert = convert(cc, ISO_3166_1_ALPHA2, GENC_3_0_0_ALPHA2);
      assertThat(
          convert, anyOf(equalTo(Collections.singleton(cc)), equalTo(Collections.emptySet())));
    }
  }

  @Test
  public void verifyGencMistakenAsIsoReturnsSameCodeOrNothingAlpha3() {
    for (String cc : GENC_3_0_0_ALPHA3.allCodes()) {
      Set<String> convert = convert(cc, ISO_3166_1_ALPHA3, GENC_3_0_0_ALPHA3);
      assertThat(
          convert, anyOf(equalTo(Collections.singleton(cc)), equalTo(Collections.emptySet())));
    }
  }

  @Test
  public void printStandardInfo() {
    for (StandardFormat standardFormat : StandardFormat.values()) {
      System.out.println(standardFormat.name() + " " + sort(standardFormat.allCodes()));
    }
  }

  @Test
  public void printISO3toGENC3Failures() {
    for (String cc : ISO_3166_1_ALPHA3.allCodes()) {
      Set<String> convert = convert(cc, ISO_3166_1_ALPHA3, GENC_3_0_0_ALPHA3);
      if (convert.isEmpty()) {
        System.out.println(cc);
      }
    }
  }

  // todo this demonstrates a bug where the input format is ignored by the converter impl
  // we should fix this bug
  @Test
  @Ignore
  public void testInvalidConversionFromNumeric() {
    Set<String> result = convert("CH", GENC_3_0_0_NUMERIC, ISO_3166_1_NUMERIC);
    assertThat(0, equalTo(result.size()));
  }
}

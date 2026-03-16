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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standard.StandardRegistryImpl;
import org.junit.Test;

public class CountryCodeConverterTest {

  @Test
  public void testDefaultConstructor() {
    CountryCodeConverter converter = new CountryCodeConverter();
    assertNotNull(converter.getSystemDefaultStandard());
    assertEquals("ISO3166", converter.getSystemDefaultStandard().getName());
  }

  @Test
  public void testGetSupportedStandards() {
    CountryCodeConverter converter = new CountryCodeConverter();
    Set<Standard> standards = converter.getSupportedStandards();
    assertNotNull(standards);
    assertFalse(standards.isEmpty());
    assertTrue(standards.size() >= 3);
  }

  @Test
  public void testFromAlpha2() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromAlpha2("CN", isoProvider.getStandard(), gencProvider.getStandard());
    assertFalse(result.isEmpty());
  }

  @Test
  public void testFromAlpha3() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromAlpha3("CHN", isoProvider.getStandard(), gencProvider.getStandard());
    assertFalse(result.isEmpty());
  }

  @Test
  public void testFromNumeric() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromNumeric("156", isoProvider.getStandard(), gencProvider.getStandard());
    assertFalse(result.isEmpty());
  }

  @Test
  public void testFromAlpha2SameStandard() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");

    Set<CountryCode> result =
        converter.fromAlpha2("CN", isoProvider.getStandard(), isoProvider.getStandard());
    assertFalse(result.isEmpty());
    for (CountryCode cc : result) {
      assertEquals(isoProvider.getStandard(), cc.getStandard());
    }
  }

  @Test
  public void testFromAlpha2NonExistent() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromAlpha2("ZZ", isoProvider.getStandard(), gencProvider.getStandard());
    assertTrue(result.isEmpty());
  }

  @Test
  public void testFromAlpha3NonExistent() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromAlpha3("ZZZ", isoProvider.getStandard(), gencProvider.getStandard());
    assertTrue(result.isEmpty());
  }

  @Test
  public void testFromNumericNonExistent() {
    CountryCodeConverter converter = new CountryCodeConverter();
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");

    Set<CountryCode> result =
        converter.fromNumeric("999", isoProvider.getStandard(), gencProvider.getStandard());
    assertTrue(result.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithUnsupportedStandard() {
    Standard unsupported =
        new Standard() {
          @Override
          public String getName() {
            return "UNSUPPORTED";
          }

          @Override
          public String getVersion() {
            return "1.0";
          }

          @Override
          public Set<String> getFormatNames() {
            return java.util.Collections.emptySet();
          }
        };
    new CountryCodeConverter(unsupported);
  }
}

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
package org.codice.countrycode.mapping;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Set;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standard.StandardRegistryImpl;
import org.junit.Before;
import org.junit.Test;

public class CsvMappingStrategyIntegrationTest {

  private CsvMappingStrategy strategy;

  @Before
  public void setUp() {
    strategy = new CsvMappingStrategy();
  }

  @Test
  public void testGetMappings() {
    Set<Set<CountryCode>> mappings = strategy.getMappings();
    assertNotNull(mappings);
    assertFalse(mappings.isEmpty());
  }

  @Test
  public void testGetMappedStandards() {
    Set<Standard> standards = strategy.getMappedStandards();
    assertNotNull(standards);
    assertFalse(standards.isEmpty());
    assertTrue(standards.size() >= 3);
  }

  @Test
  public void testGetMappingForValidCode() {
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    Set<CountryCode> result = strategy.getMappingFor(isoProvider.getStandard(), "CHN");
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetMappingForNonExistentCode() {
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    Set<CountryCode> result = strategy.getMappingFor(isoProvider.getStandard(), "ZZZ");
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetMappingForUnconfiguredStandard() {
    Standard unconfigured =
        new Standard() {
          @Override
          public String getName() {
            return "UNKNOWN";
          }

          @Override
          public String getVersion() {
            return "0.0";
          }

          @Override
          public Set<String> getFormatNames() {
            return Collections.singleton("alpha2");
          }
        };
    Set<CountryCode> result = strategy.getMappingFor(unconfigured, "US");
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetMappingForFips() {
    StandardProvider fipsProvider = StandardRegistryImpl.getInstance().lookup("FIPS", "10-4");
    Set<CountryCode> result = strategy.getMappingFor(fipsProvider.getStandard(), "CH");
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetMappingForGenc() {
    StandardProvider gencProvider = StandardRegistryImpl.getInstance().lookup("GENC", "3.0.0");
    Set<CountryCode> result = strategy.getMappingFor(gencProvider.getStandard(), "CHN");
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  public void testGetMappingForGec() {
    StandardProvider gecProvider = StandardRegistryImpl.getInstance().lookup("GEC", "18");
    Set<CountryCode> result = strategy.getMappingFor(gecProvider.getStandard(), "CH");
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  public void testMappingsContainExpectedStandards() {
    Set<Standard> standards = strategy.getMappedStandards();
    boolean hasFips = false;
    boolean hasIso = false;
    boolean hasGenc = false;
    boolean hasGec = false;
    for (Standard s : standards) {
      if ("FIPS".equals(s.getName())) hasFips = true;
      if ("ISO3166".equals(s.getName())) hasIso = true;
      if ("GENC".equals(s.getName())) hasGenc = true;
      if ("GEC".equals(s.getName())) hasGec = true;
    }
    assertTrue(hasFips);
    assertTrue(hasIso);
    assertTrue(hasGenc);
    assertTrue(hasGec);
  }

  @Test
  public void testMappingResultsExcludeSourceStandard() {
    StandardProvider isoProvider = StandardRegistryImpl.getInstance().lookup("ISO3166", "1");
    Standard isoStandard = isoProvider.getStandard();
    Set<CountryCode> result = strategy.getMappingFor(isoStandard, "CHN");
    for (CountryCode cc : result) {
      assertFalse(
          "Mapping result should not contain the source standard",
          cc.getStandard().equals(isoStandard));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNonExistentFile() {
    new CsvMappingStrategy("nonexistent.csv");
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorWithEmptyFile() {
    new CsvMappingStrategy("test-configs/empty_config.csv");
  }
}

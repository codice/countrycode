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

import org.junit.Test;

/**
 * Instantiating the CsvMappingStrategy class causes the mappings.csv file to be read,
 * and its entries validated against the country code standards.
 * <p>
 * These standards are not static. For example, ISO does not have a version number; updates are rolling.
 * That means country codes can be added and dropped by the providers.
 * In such cases, loading the mappings.csv file can cause errors.
 * This unit test exist to catch those errors in the test phase.
 */

public class CsvMappingStrategyTest {

    @Test
    public void testMappingsFile() {
        new CsvMappingStrategy();
    }
}

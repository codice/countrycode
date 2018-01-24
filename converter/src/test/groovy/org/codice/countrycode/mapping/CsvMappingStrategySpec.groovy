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
package org.codice.countrycode.mapping

import org.codice.countrycode.standard.CountryCode
import org.codice.countrycode.standard.Standard
import org.codice.countrycode.standard.StandardProvider
import org.codice.countrycode.standard.StandardRegistry
import spock.lang.Specification

class CsvMappingStrategySpec extends Specification {

    static String STANDARD_NAME_1 = 'name1'

    static String STANDARD_VERSION_1 = 'version1'

    static String MAPPING_PROPERTY_1 = "property1"

    static String STANDARD_NAME_2 = 'name2'

    static String STANDARD_VERSION_2 = 'version2'

    static String MAPPING_PROPERTY_2 = "property2"

    static String STANDARD_NAME_3 = 'name3'

    static String STANDARD_VERSION_3 = 'version3'

    static String MAPPING_PROPERTY_3 = "property3"

    StandardRegistry standardRegistry

    CsvMappingStrategy csvMappingStrategy

    def standard1 = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [MAPPING_PROPERTY_1] as Set)

    def standard2 = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [MAPPING_PROPERTY_2] as Set)

    def standard3 = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, [MAPPING_PROPERTY_3] as Set)

    def 'test successful csv mapping'() {
        setup:
        def cc1 = mockCountryCode(standard1, [(MAPPING_PROPERTY_1): 'value1'])
        def cc2 = mockCountryCode(standard2, [(MAPPING_PROPERTY_2): 'value2'])
        def cc3 = mockCountryCode(standard3, [(MAPPING_PROPERTY_3): 'value3'])

        def standard1CountryCodes = [cc1] as Set
        def standard2CountryCodes = [cc2] as Set
        def standard3CountryCodes = [cc3] as Set

        prepareRegistry(standard1CountryCodes, standard2CountryCodes, standard3CountryCodes)

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/valid_config.csv', standardRegistry)

        then:
        def cc1Mappings = csvMappingStrategy.getMappingFor(standard1, 'value1')
        cc1Mappings.size() == 2
        cc1Mappings.containsAll([cc2, cc3])

        def cc2Mappings = csvMappingStrategy.getMappingFor(standard2, 'value2')
        cc2Mappings.size() == 2
        cc2Mappings.containsAll([cc1, cc3])

        def cc3Mappings = csvMappingStrategy.getMappingFor(standard3, 'value3')
        cc3Mappings.size() == 2
        cc3Mappings.containsAll([cc1, cc2])
    }

    def 'test partial mappings between standards'() {
        setup:
        def s1cc1 = mockCountryCode(standard1, [(MAPPING_PROPERTY_1): 'value4'])
        def s1cc2 = mockCountryCode(standard1, [(MAPPING_PROPERTY_1): 'value7'])

        def s2cc1 = mockCountryCode(standard2, [(MAPPING_PROPERTY_2): 'value2'])
        def s2cc2 = mockCountryCode(standard2, [(MAPPING_PROPERTY_2): 'value5'])

        def s3cc1 = mockCountryCode(standard3, [(MAPPING_PROPERTY_3): 'value3'])
        def s3cc2 = mockCountryCode(standard3, [(MAPPING_PROPERTY_3): 'value9'])

        def standard1CountryCodes = [s1cc1, s1cc2] as Set
        def standard2CountryCodes = [s2cc1, s2cc2] as Set
        def standard3CountryCodes = [s3cc1, s3cc2] as Set

        prepareRegistry(standard1CountryCodes, standard2CountryCodes, standard3CountryCodes)

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/partial_mappings.csv', standardRegistry)

        then:
        def cc1Mappings = csvMappingStrategy.getMappingFor(standard2, 'value2')
        cc1Mappings.size() == 1
        cc1Mappings.containsAll([s3cc1])

        def cc2Mappings = csvMappingStrategy.getMappingFor(standard1, 'value4')
        cc2Mappings.size() == 1
        cc2Mappings.containsAll([s2cc2])

        def cc3Mappings = csvMappingStrategy.getMappingFor(standard3, 'value9')
        cc3Mappings.size() == 1
        cc3Mappings.containsAll([s1cc2])
    }

    def 'test empty config file throws exception'() {
        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/empty_config.csv')

        then:
        thrown(IllegalStateException)
    }

    def 'test property value does not exist for standard throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/valid_config.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test null filepath throws NPE'() {
        when:
        csvMappingStrategy = new CsvMappingStrategy(null)

        then:
        thrown(NullPointerException)
    }

    def 'test empty filepath throws exception'() {
        when:
        csvMappingStrategy = new CsvMappingStrategy('')

        then:
        thrown(IllegalArgumentException)
    }

    def 'test file not found throws exception'() {
        when:
        csvMappingStrategy = new CsvMappingStrategy('file does not exist')

        then:
        thrown(IllegalArgumentException)
    }

    def 'test single standard mapping throws exception'() {
        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/single_standard.csv')

        then:
        thrown(IllegalStateException)
    }

    def 'test single entry in mapping throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/single_mapping_entry.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test standard missing version throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/missing_standard_version.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test missing mapping property throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/missing_mapping_property.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test mapping property doesn\'t exist for standard throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/standard_with_wrong_mapping_property.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test no mappings exist throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/no_mappings.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test unsupported standard throws exception'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/unsupported_standard.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test supported standards correctly retrieved from config'() {
        setup:
        def cc1 = mockCountryCode(standard1, [(MAPPING_PROPERTY_1): 'value1'])
        def cc2 = mockCountryCode(standard2, [(MAPPING_PROPERTY_2): 'value2'])
        def cc3 = mockCountryCode(standard3, [(MAPPING_PROPERTY_3): 'value3'])

        def standard1CountryCodes = [cc1] as Set
        def standard2CountryCodes = [cc2] as Set
        def standard3CountryCodes = [cc3] as Set

        prepareRegistry(standard1CountryCodes, standard2CountryCodes, standard3CountryCodes)

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/valid_config.csv', standardRegistry)

        then:
        csvMappingStrategy.getMappedStandards().size() == 3
        csvMappingStrategy.getMappedStandards().containsAll(standard1, standard2, standard3)
    }

    def 'test mapping contains too many country codes'() {
        setup:
        prepareRegistry()

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/too_many_mappings.csv', standardRegistry)

        then:
        thrown(IllegalStateException)
    }

    def 'test mapping contains same values returns correct mapping set'() {
        setup:
        def cc1 = mockCountryCode(standard1, [(MAPPING_PROPERTY_1): 'value1'])
        def cc2 = mockCountryCode(standard2, [(MAPPING_PROPERTY_2): 'value2'])
        def cc3 = mockCountryCode(standard3, [(MAPPING_PROPERTY_3): 'value2'])

        def standard1CountryCodes = [cc1] as Set
        def standard2CountryCodes = [cc2] as Set
        def standard3CountryCodes = [cc3] as Set

        prepareRegistry(standard1CountryCodes, standard2CountryCodes, standard3CountryCodes)

        when:
        csvMappingStrategy = new CsvMappingStrategy('test-configs/same_value_mappings.csv', standardRegistry)

        then:
        def cc2Mappings = csvMappingStrategy.getMappingFor(standard2, 'value2')
        cc2Mappings.size() == 2
        cc2Mappings.containsAll([cc1, cc3])
    }

    void prepareRegistry(Set provider1Codes = [], Set provider2Codes = [], Set provider3Codes = []) {
        standardRegistry = Mock(StandardRegistry)
        standardRegistry.lookup(STANDARD_NAME_1, STANDARD_VERSION_1) >> mockStandardProvider(standard1, provider1Codes)
        standardRegistry.lookup(STANDARD_NAME_2, STANDARD_VERSION_2) >> mockStandardProvider(standard2, provider2Codes)
        standardRegistry.lookup(STANDARD_NAME_3, STANDARD_VERSION_3) >> mockStandardProvider(standard3, provider3Codes)
    }

    def mockStandardProvider(Standard standard, Set entries) {
        return Mock(StandardProvider) {
            getStandard() >> standard
            getStandardEntries() >> entries
        }
    }

    def mockStandard(String standardName, String standardVersion, Set formatNames) {
        return Mock(Standard) {
            getName() >> standardName
            getVersion() >> standardVersion
            getFormatNames() >> formatNames
        }
    }

    def mockCountryCode(Standard mockStandard, Map formatValues) {
        def mockCc = Mock(CountryCode) {
            getName() >> 'CountryName'
            getStandard() >> mockStandard
        }

        for (Map.Entry<String, String> entry : formatValues.entrySet()) {
            mockCc.getAsFormat(entry.getKey()) >> entry.getValue()
        }

        return mockCc
    }
}

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
package org.codice.countrycode

import org.codice.countrycode.converter.MappingStrategy
import org.codice.countrycode.standard.CountryCode
import org.codice.countrycode.standard.Standard
import spock.lang.Specification

class CountryCodeConverterSpec extends Specification {

    static String STANDARD_NAME_1 = 'name1'

    static String STANDARD_VERSION_1 = 'version1'

    static String STANDARD_NAME_2 = 'name2'

    static String STANDARD_VERSION_2 = 'version2'

    static String STANDARD_NAME_3 = 'name3'

    static String STANDARD_VERSION_3 = 'version3'

    static String ALPHA_3 = 'alpha3'

    static String ALPHA_2 = 'alpha2'

    static String NUMERIC = 'numeric'

    CountryCodeConverter converter

    MappingStrategy mappingStrategy

    def 'test from alpha2'() {
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, ['alpha2'] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, ['alpha3'] as Set)
        def standardThree = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, ['alpha3'] as Set)

        def cc1 = mockCountryCode(standardOne, [(ALPHA_2): 'AB'])
        def cc2 = mockCountryCode(standardTwo, [(ALPHA_3): 'DEF'])
        def cc3 = mockCountryCode(standardThree, [(ALPHA_3): 'GHI'])

        setup:
        mappingStrategy = Mock(MappingStrategy) {
            getMappings() >> [[cc2, cc3] as Set, [cc1, cc2, cc3] as Set]
            getMappedStandards() >> [standardOne, standardTwo, standardThree]
            getMappingFor(_ as Standard, 'AB') >> [cc1, cc2, cc3]
        }

        converter = new CountryCodeConverter(standardTwo, mappingStrategy)

        when:
        def countryCodes = converter.fromAlpha2('AB', standardOne, standardTwo)

        then:
        countryCodes.count {
            it.getAsFormat(ALPHA_3) == 'DEF' &&
                    it.getStandard().getName() == STANDARD_NAME_2 &&
                    it.getStandard().getVersion() == STANDARD_VERSION_2
        } == 1
    }

    def 'test from alpha3'() {
        setup:
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [ALPHA_3] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [ALPHA_3] as Set)
        def standardThree = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, [ALPHA_3] as Set)

        def cc1 = mockCountryCode(standardOne, [(ALPHA_3): 'ABC'])
        def cc2 = mockCountryCode(standardTwo, [(ALPHA_3): 'DEF'])
        def cc3 = mockCountryCode(standardThree, [(ALPHA_3): 'GHI'])

        mappingStrategy = Mock(MappingStrategy) {
            getMappings() >> [[cc1, cc2, cc3] as Set]
            getMappedStandards() >> [standardOne, standardTwo, standardThree]
            getMappingFor(_ as Standard, 'ABC') >> [cc1, cc2, cc3]
        }

        converter = new CountryCodeConverter(standardOne, mappingStrategy)

        when:
        def countryCodes = converter.fromAlpha3('ABC', standardOne, standardThree)

        then:
        countryCodes.count {
            it.getAsFormat(ALPHA_3) == 'GHI' &&
                    it.getStandard().getName() == STANDARD_NAME_3 &&
                    it.getStandard().getVersion() == STANDARD_VERSION_3
        } == 1
    }

    def 'test from numeric'() {
        setup:
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [NUMERIC] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [NUMERIC] as Set)
        def standardThree = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, [NUMERIC] as Set)

        def cc1 = mockCountryCode(standardOne, [(NUMERIC): '001'])
        def cc2 = mockCountryCode(standardTwo, [(NUMERIC): '002'])
        def cc3 = mockCountryCode(standardThree, [(NUMERIC): '003'])

        mappingStrategy = Mock(MappingStrategy) {
            getMappings() >> [[cc1, cc2, cc3] as Set]
            getMappedStandards() >> [standardOne, standardTwo, standardThree]
            getMappingFor(_ as Standard, '001') >> [cc1, cc2, cc3]
        }

        converter = new CountryCodeConverter(standardOne, mappingStrategy)

        when:
        def countryCodes = converter.fromNumeric('001', standardOne, standardThree)

        then:
        countryCodes.count {
            it.getAsFormat(NUMERIC) == '003' &&
                    it.getStandard().getName() == STANDARD_NAME_3 &&
                    it.getStandard().getVersion() == STANDARD_VERSION_3
        } == 1
    }

    def 'test from and to standards equal but no cc with value present'() {
        setup:
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [ALPHA_3] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [ALPHA_3] as Set)
        def standardThree = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, [ALPHA_3] as Set)

        def cc1 = mockCountryCode(standardOne, [(ALPHA_3): 'ABC'])
        def cc2 = mockCountryCode(standardTwo, [(ALPHA_3): 'DEF'])
        def cc3 = mockCountryCode(standardThree, [(ALPHA_3): 'GHI'])

        mappingStrategy = Mock(MappingStrategy) {
            getMappings() >> [[cc1, cc2, cc3] as Set, [cc2, cc3] as Set]
            getMappedStandards() >> [standardOne, standardTwo, standardThree]
            getMappingFor(_ as Standard, 'ABC') >> [cc1, cc2, cc3]
        }

        converter = new CountryCodeConverter(standardOne, mappingStrategy)

        when:
        def countryCodes = converter.fromAlpha3('NOTEXIST', standardOne, standardOne)

        then:
        countryCodes.size() == 0
    }

    def 'test to and from standards equal returns cc with format value'() {
        setup:
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [ALPHA_3] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [ALPHA_3] as Set)
        def standardThree = mockStandard(STANDARD_NAME_3, STANDARD_VERSION_3, [ALPHA_3] as Set)

        def cc1 = mockCountryCode(standardOne, [(ALPHA_3): 'ABC'])
        def cc2 = mockCountryCode(standardTwo, [(ALPHA_3): 'DEF'])
        def cc3 = mockCountryCode(standardThree, [(ALPHA_3): 'GHI'])

        mappingStrategy = Mock(MappingStrategy) {
            getMappings() >> [[cc1, cc2, cc3] as Set, [cc2, cc3] as Set]
            getMappedStandards() >> [standardOne, standardTwo, standardThree]
            getMappingFor(_ as Standard, 'ABC') >> [cc1, cc2, cc3]
        }

        converter = new CountryCodeConverter(standardOne, mappingStrategy)

        when:
        def countryCodes = converter.fromAlpha3('ABC', standardOne, standardOne)

        then:
        countryCodes.count {
            it.getAsFormat(ALPHA_3) == 'ABC' &&
                    it.getStandard().getName() == STANDARD_NAME_1 &&
                    it.getStandard().getVersion() == STANDARD_VERSION_1
        } == 1
    }

    def 'test default standard unsupported by mapping strategy'() {
        setup:
        def standardOne = mockStandard(STANDARD_NAME_1, STANDARD_VERSION_1, [NUMERIC] as Set)
        def standardTwo = mockStandard(STANDARD_NAME_2, STANDARD_VERSION_2, [NUMERIC] as Set)

        mappingStrategy = Mock(MappingStrategy) {
            getMappedStandards() >> [standardOne]
        }

        when:
        converter = new CountryCodeConverter(standardTwo, mappingStrategy)

        then:
        thrown(IllegalArgumentException)

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

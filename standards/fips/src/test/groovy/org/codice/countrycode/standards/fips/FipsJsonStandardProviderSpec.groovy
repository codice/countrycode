package org.codice.countrycode.standards.fips

import spock.lang.Specification

class FipsJsonStandardProviderSpec extends Specification {

    def 'test fips standard provider'() {
        expect:
        new FipsJsonStandardProvider().getStandardEntries().size() == 271
    }
}

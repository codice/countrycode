package org.codice.countrycode.standards.iso

import spock.lang.Specification

class Iso3166StandardProviderSpec extends Specification {

    def 'test iso3166 provider'() {
        expect:
        new Iso3166StandardProvider().getStandardEntries().size() == 249
    }
}

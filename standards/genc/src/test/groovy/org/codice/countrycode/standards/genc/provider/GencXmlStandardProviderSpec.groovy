package org.codice.countrycode.standards.genc.provider

import spock.lang.Specification

class GencXmlStandardProviderSpec extends Specification {

    def 'test genc provider'() {
        expect:
        new GencXmlStandardProvider().getStandardEntries().size() == 280
    }
}

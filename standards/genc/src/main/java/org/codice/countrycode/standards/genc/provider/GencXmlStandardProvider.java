package org.codice.countrycode.standards.genc.provider;

import static org.codice.countrycode.standards.genc.provider.GencStandard.ALPHA_2;
import static org.codice.countrycode.standards.genc.provider.GencStandard.ALPHA_3;
import static org.codice.countrycode.standards.genc.provider.GencStandard.NUMERIC;

import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standards.common.CountryCodeBuilder;
import org.codice.countrycode.standards.genc.GENCStandardBaseline;
import org.codice.countrycode.standards.genc.GeopoliticalEntityEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads a local copy of the GENC 3.0.0 standard downloaded from
 * https://nsgreg.nga.mil/doc/view?i=2507
 */
public class GencXmlStandardProvider implements StandardProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(GencXmlStandardProvider.class);

  private static final String GENC_CODES_FILE = "GENC Standard Ed3.0.xml";

  private static final String CHARSET_NAME = "UTF-8";

  private final Standard standard;

  private final Set<CountryCode> standardEntries;

  public GencXmlStandardProvider() {
    standardEntries = new HashSet<>();
    standard = new GencStandard();
    init();
  }

  @Override
  public Standard getStandard() {
    return standard;
  }

  @Override
  public Set<CountryCode> getStandardEntries() {
    return ImmutableSet.copyOf(standardEntries);
  }

  private void init() {
    String xml;
    try {
      xml =
          IOUtils.toString(
              getClass().getClassLoader().getResourceAsStream(GENC_CODES_FILE), CHARSET_NAME);
    } catch (IOException e) {
      LOGGER.debug("Failed to read file [{}]. No country codes will be provided.", GENC_CODES_FILE);
      return;
    }

    try {
      Unmarshaller unmarshaller =
          JAXBContext.newInstance(GENCStandardBaseline.class).createUnmarshaller();

      GENCStandardBaseline gencStandardBaseline =
          (GENCStandardBaseline) unmarshaller.unmarshal(new StringReader(xml));
      List<GeopoliticalEntityEntry> geopoliticalEntityEntries =
          gencStandardBaseline.getGeopoliticalEntityEntries();

      for (GeopoliticalEntityEntry entry : geopoliticalEntityEntries) {
        CountryCode countryCode =
            new CountryCodeBuilder(getStandard(), entry.getName())
                .formatValue(ALPHA_2, entry.getEncoding().getChar2Code())
                .formatValue(ALPHA_3, entry.getEncoding().getChar3Code())
                .formatValue(NUMERIC, entry.getEncoding().getNumericCode())
                .build();
        standardEntries.add(countryCode);
      }
    } catch (JAXBException e) {
      LOGGER.debug(
          "Error parsing XML file [{}]. No country codes will be provided.", GENC_CODES_FILE);
    }
  }
}

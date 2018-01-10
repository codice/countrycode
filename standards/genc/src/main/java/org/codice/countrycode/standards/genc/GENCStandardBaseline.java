package org.codice.countrycode.standards.genc;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(
  name = "GENCStandardBaseline",
  namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0"
)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({GeopoliticalEntityEntry.class})
public class GENCStandardBaseline {

  @XmlElement(name = "authority")
  private String authority;

  @XmlElement(name = "baseline")
  private String baseline;

  @XmlElement(name = "promulgationDate")
  private Date promulgationDate;

  @XmlElement(name = "GeopoliticalEntityEntry")
  private List<GeopoliticalEntityEntry> geopoliticalEntityEntries;

  @XmlElement
  private String schemaLocation;

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }

  public String getBaseline() {
    return baseline;
  }

  public void setBaseline(String baseline) {
    this.baseline = baseline;
  }

  public Date getPromulgationDate() {
    return promulgationDate;
  }

  public void setPromulgationDate(Date promulgationDate) {
    this.promulgationDate = promulgationDate;
  }

  public List<GeopoliticalEntityEntry> getGeopoliticalEntityEntries() {
    return geopoliticalEntityEntries;
  }

  public void setGeopoliticalEntityEntries(
      List<GeopoliticalEntityEntry> geopoliticalEntityEntries) {
    this.geopoliticalEntityEntries = geopoliticalEntityEntries;
  }

  public void setSchemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
  }
}

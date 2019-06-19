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
package org.codice.countrycode.standards.genc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeUriSet {

  @XmlElement(
    name = "codespaceURNBasedShort",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private String codespaceURNBasedShort;

  @XmlElement(
    name = "codespaceURL",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private String codespaceURL;

  @XmlElement(
    name = "codespaceURNBased",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private String codespaceURNBased;

  @XmlElement(
    name = "codespaceURN",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private String codespaceURN;

  public void setCodespaceURNBasedShort(String codespaceURNBasedShort) {
    this.codespaceURNBasedShort = codespaceURNBasedShort;
  }

  public void setCodespaceURL(String codespaceURL) {
    this.codespaceURL = codespaceURL;
  }

  public void setCodespaceURNBased(String codespaceURNBased) {
    this.codespaceURNBased = codespaceURNBased;
  }

  public void setCodespaceURN(String codespaceURN) {
    this.codespaceURN = codespaceURN;
  }

  public String getCodespaceURNBasedShort() {
    return codespaceURNBasedShort;
  }

  public String getCodespaceURL() {
    return codespaceURL;
  }

  public String getCodespaceURNBased() {
    return codespaceURNBased;
  }

  public String getCodespaceURN() {
    return codespaceURN;
  }
}

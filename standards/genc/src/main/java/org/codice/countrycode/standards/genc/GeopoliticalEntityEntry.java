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

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
  name = "GeopoliticalEntityEntry",
  namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class GeopoliticalEntityEntry {

  @XmlElement private String entryType;

  @XmlElement(name = "encoding")
  private Encoding encoding;

  @XmlElement private String name;

  @XmlElement private String shortName;

  @XmlElement private List<Division> division;

  @XmlElement private String entryDate;

  @XmlElement private String usRecognition;

  @XmlElement private String entryNotesOnNaming;

  @XmlElement private String fullName;

  @XmlElement private String entryNotesOnTerritory;

  @XmlElement private String gencStatus;

  @XmlElement private String usRecognitionNote;

  @XmlElement private List<LocalShortName> localShortName;

  public void setEntryType(String entryType) {
    this.entryType = entryType;
  }

  public void setEncoding(Encoding encoding) {
    this.encoding = encoding;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public void setDivision(List<Division> division) {
    this.division = division;
  }

  public void setEntryDate(String entryDate) {
    this.entryDate = entryDate;
  }

  public void setUsRecognition(String usRecognition) {
    this.usRecognition = usRecognition;
  }

  public void setEntryNotesOnNaming(String entryNotesOnNaming) {
    this.entryNotesOnNaming = entryNotesOnNaming;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setEntryNotesOnTerritory(String entryNotesOnTerritory) {
    this.entryNotesOnTerritory = entryNotesOnTerritory;
  }

  public void setGencStatus(String gencStatus) {
    this.gencStatus = gencStatus;
  }

  public void setUsRecognitionNote(String usRecognitionNote) {
    this.usRecognitionNote = usRecognitionNote;
  }

  public void setLocalShortName(List<LocalShortName> localShortName) {
    this.localShortName = localShortName;
  }

  public String getEntryType() {
    return entryType;
  }

  public Encoding getEncoding() {
    return encoding;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public List<Division> getDivisions() {
    return division;
  }

  public String getEntryDate() {
    return entryDate;
  }

  public String getUsRecognition() {
    return usRecognition;
  }

  public String getEntryNotesOnNaming() {
    return entryNotesOnNaming;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEntryNotesOnTerritory() {
    return entryNotesOnTerritory;
  }

  public String getGencStatus() {
    return gencStatus;
  }

  public String getUsRecognitionNote() {
    return usRecognitionNote;
  }

  public List<LocalShortName> getLocalShortNames() {
    return localShortName;
  }
}

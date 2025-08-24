/*
 * Copyright (C) 2015-2025 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.businesscard.generic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Predicate;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.clone.ICloneable;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.datetime.web.PDTWebDateHelper;
import com.helger.json.IHasJson;
import com.helger.json.IJson;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.JsonObject;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Generic business entity.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PDBusinessEntity implements IHasJson, Serializable, ICloneable <PDBusinessEntity>
{
  private final ICommonsList <PDName> m_aNames = new CommonsArrayList <> ();
  private String m_sCountryCode;
  private String m_sGeoInfo;
  private final ICommonsList <PDIdentifier> m_aIDs = new CommonsArrayList <> ();
  private final ICommonsList <String> m_aWebsiteURIs = new CommonsArrayList <> ();
  private final ICommonsList <PDContact> m_aContacts = new CommonsArrayList <> ();
  private String m_sAdditionalInfo;
  private LocalDate m_aRegistrationDate;

  public PDBusinessEntity ()
  {}

  public PDBusinessEntity (@Nullable final ICommonsList <PDName> aNames,
                           @Nullable final String sCountryCode,
                           @Nullable final String sGeoInfo,
                           @Nullable final ICommonsList <PDIdentifier> aIDs,
                           @Nullable final ICommonsList <String> aWebsiteURIs,
                           @Nullable final ICommonsList <PDContact> aContacts,
                           @Nullable final String sAdditionalInfo,
                           @Nullable final LocalDate aRegDate)
  {
    names ().setAll (aNames);
    setCountryCode (sCountryCode);
    setGeoInfo (sGeoInfo);
    identifiers ().setAll (aIDs);
    websiteURIs ().setAll (aWebsiteURIs);
    contacts ().setAllMapped (aContacts, PDContact::getClone);
    setAdditionalInfo (sAdditionalInfo);
    setRegistrationDate (aRegDate);
  }

  /**
   * @return All names of the entity. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <PDName> names ()
  {
    return m_aNames;
  }

  /**
   * @return The country code. Should not be <code>null</code>.
   */
  @Nullable
  public final String getCountryCode ()
  {
    return m_sCountryCode;
  }

  public final boolean hasCountryCode ()
  {
    return StringHelper.isNotEmpty (m_sCountryCode);
  }

  /**
   * Sets the value of the countryCode property.
   *
   * @param sCountryCode
   *        The country code to use. Should not be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final PDBusinessEntity setCountryCode (@Nullable final String sCountryCode)
  {
    m_sCountryCode = sCountryCode;
    return this;
  }

  /**
   * @return The geographical information. May be <code>null</code>.
   */
  @Nullable
  public final String getGeoInfo ()
  {
    return m_sGeoInfo;
  }

  public final boolean hasGeoInfo ()
  {
    return StringHelper.isNotEmpty (m_sGeoInfo);
  }

  /**
   * @param sGeoInfo
   *        Geographical information. May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final PDBusinessEntity setGeoInfo (@Nullable final String sGeoInfo)
  {
    m_sGeoInfo = sGeoInfo;
    return this;
  }

  /**
   * @return Identifier lists. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <PDIdentifier> identifiers ()
  {
    return m_aIDs;
  }

  /**
   * @return All Website URIs of the entity. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <String> websiteURIs ()
  {
    return m_aWebsiteURIs;
  }

  /**
   * @return Mutable list of all contacts. Never <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <PDContact> contacts ()
  {
    return m_aContacts;
  }

  /**
   * @return The optional additional information. May be <code>null</code>.
   */
  @Nullable
  public final String getAdditionalInfo ()
  {
    return m_sAdditionalInfo;
  }

  public final boolean hasAdditionalInfo ()
  {
    return StringHelper.isNotEmpty (m_sAdditionalInfo);
  }

  /**
   * Set the additional information / free text.
   *
   * @param sAdditionalInfo
   *        Additional information to be used (free text). May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final PDBusinessEntity setAdditionalInfo (@Nullable final String sAdditionalInfo)
  {
    m_sAdditionalInfo = sAdditionalInfo;
    return this;
  }

  /**
   * @return The optional registration date. May be <code>null</code>.
   */
  @Nullable
  public final LocalDate getRegistrationDate ()
  {
    return m_aRegistrationDate;
  }

  public final boolean hasRegistrationDate ()
  {
    return m_aRegistrationDate != null;
  }

  /**
   * Sets the value of the registration date property.
   *
   * @param aRegDate
   *        The registration date. May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final PDBusinessEntity setRegistrationDate (@Nullable final LocalDate aRegDate)
  {
    m_aRegistrationDate = aRegDate;
    return this;
  }

  /**
   * This method clones all values from <code>this</code> to the passed object. All data in the
   * parameter object is overwritten!
   *
   * @param ret
   *        The target object to clone to. May not be <code>null</code>.
   */
  public void cloneTo (@Nonnull final PDBusinessEntity ret)
  {
    ret.m_aNames.setAllMapped (m_aNames, PDName::getClone);
    ret.m_sCountryCode = m_sCountryCode;
    ret.m_sGeoInfo = m_sGeoInfo;
    ret.m_aIDs.setAllMapped (m_aIDs, PDIdentifier::getClone);
    ret.m_aWebsiteURIs.setAll (m_aWebsiteURIs);
    ret.m_aContacts.setAllMapped (m_aContacts, PDContact::getClone);
    ret.m_sAdditionalInfo = m_sAdditionalInfo;
    ret.m_aRegistrationDate = m_aRegistrationDate;
  }

  @Nonnull
  @ReturnsMutableCopy
  public PDBusinessEntity getClone ()
  {
    final PDBusinessEntity ret = new PDBusinessEntity ();
    cloneTo (ret);
    return ret;
  }

  @Nonnull
  public IMicroElement getAsMicroXML (@Nullable final String sNamespaceURI,
                                      @Nonnull @Nonempty final String sElementName)
  {
    final IMicroElement ret = new MicroElement (sNamespaceURI, sElementName);
    for (final PDName aName : m_aNames)
      ret.addChild (aName.getAsMicroXML (sNamespaceURI, "name"));
    ret.setAttribute ("countrycode", m_sCountryCode);
    if (hasGeoInfo ())
      ret.addElementNS (sNamespaceURI, "geoinfo").addText (m_sGeoInfo);
    for (final PDIdentifier aID : m_aIDs)
      ret.addChild (aID.getAsMicroXML (sNamespaceURI, "id"));
    for (final String sWebsiteURI : m_aWebsiteURIs)
      ret.addElementNS (sNamespaceURI, "website").addText (sWebsiteURI);
    for (final PDContact aContact : m_aContacts)
      ret.addChild (aContact.getAsMicroXML (sNamespaceURI, "contact"));
    if (hasAdditionalInfo ())
      ret.addElementNS (sNamespaceURI, "additionalinfo").addText (m_sAdditionalInfo);
    if (hasRegistrationDate ())
      ret.addElementNS (sNamespaceURI, "regdate").addText (PDTWebDateHelper.getAsStringXSD (m_aRegistrationDate));
    return ret;
  }

  @Nonnull
  public IJsonObject getAsJson ()
  {
    final IJsonObject ret = new JsonObject ();
    ret.add ("name", new JsonArray ().addAllMapped (m_aNames, PDName::getAsJson));
    ret.add ("countrycode", m_sCountryCode);
    if (hasGeoInfo ())
      ret.add ("geoinfo", m_sGeoInfo);
    if (m_aIDs.isNotEmpty ())
      ret.add ("id", new JsonArray ().addAllMapped (m_aIDs, PDIdentifier::getAsJson));
    if (m_aWebsiteURIs.isNotEmpty ())
      ret.add ("website", new JsonArray ().addAll (m_aWebsiteURIs));
    if (m_aContacts.isNotEmpty ())
      ret.add ("contact", new JsonArray ().addAllMapped (m_aContacts, PDContact::getAsJson));
    if (hasAdditionalInfo ())
      ret.add ("additionalinfo", m_sAdditionalInfo);
    if (hasRegistrationDate ())
      ret.add ("regdate", PDTWebDateHelper.getAsStringXSD (m_aRegistrationDate));
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PDBusinessEntity rhs = (PDBusinessEntity) o;
    return EqualsHelper.equals (m_aNames, rhs.m_aNames) &&
           EqualsHelper.equals (m_sCountryCode, rhs.m_sCountryCode) &&
           EqualsHelper.equals (m_sGeoInfo, rhs.m_sGeoInfo) &&
           EqualsHelper.equals (m_aIDs, rhs.m_aIDs) &&
           EqualsHelper.equals (m_aWebsiteURIs, rhs.m_aWebsiteURIs) &&
           EqualsHelper.equals (m_aContacts, rhs.m_aContacts) &&
           EqualsHelper.equals (m_sAdditionalInfo, rhs.m_sAdditionalInfo) &&
           EqualsHelper.equals (m_aRegistrationDate, rhs.m_aRegistrationDate);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNames)
                                       .append (m_sCountryCode)
                                       .append (m_sGeoInfo)
                                       .append (m_aIDs)
                                       .append (m_aWebsiteURIs)
                                       .append (m_aContacts)
                                       .append (m_sAdditionalInfo)
                                       .append (m_aRegistrationDate)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("names", m_aNames)
                                       .append ("countryCode", m_sCountryCode)
                                       .append ("geographicalInformation", m_sGeoInfo)
                                       .append ("identifier", m_aIDs)
                                       .append ("websiteURI", m_aWebsiteURIs)
                                       .append ("contact", m_aContacts)
                                       .append ("additionalInformation", m_sAdditionalInfo)
                                       .append ("registrationDate", m_aRegistrationDate)
                                       .getToString ();
  }

  @Nullable
  public static PDBusinessEntity of (@Nullable final IJsonObject aJson)
  {
    if (aJson == null)
      return null;

    final ICommonsList <PDName> aNames = CommonsArrayList.createFiltered (aJson.getAsArray ("name"),
                                                                          (Predicate <IJson>) IJson::isObject,
                                                                          x -> PDName.of (x.getAsObject ()));
    final ICommonsList <PDIdentifier> aIDs = CommonsArrayList.createFiltered (aJson.getAsArray ("id"),
                                                                              (Predicate <IJson>) IJson::isObject,
                                                                              x -> PDIdentifier.of (x.getAsObject ()));
    final ICommonsList <String> aWebsiteURIs = CommonsArrayList.createFiltered (aJson.getAsArray ("website"),
                                                                                (Predicate <IJson>) IJson::isValue,
                                                                                x -> x.getAsValue ().getAsString ());
    final ICommonsList <PDContact> aContacts = CommonsArrayList.createFiltered (aJson.getAsArray ("contact"),
                                                                                (Predicate <IJson>) IJson::isObject,
                                                                                x -> PDContact.of (x.getAsObject ()));
    return new PDBusinessEntity (aNames,
                                 aJson.getAsString ("countrycode"),
                                 aJson.getAsString ("geoinfo"),
                                 aIDs,
                                 aWebsiteURIs,
                                 aContacts,
                                 aJson.getAsString ("additionalinfo"),
                                 PDTWebDateHelper.getLocalDateFromXSD (aJson.getAsString ("regdate")));
  }
}

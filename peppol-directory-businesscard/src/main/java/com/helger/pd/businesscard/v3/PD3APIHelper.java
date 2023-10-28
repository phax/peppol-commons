/*
 * Copyright (C) 2015-2023 Philip Helger (www.helger.com)
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
package com.helger.pd.businesscard.v3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.pd.businesscard.generic.PDBusinessCard;
import com.helger.pd.businesscard.generic.PDBusinessEntity;
import com.helger.pd.businesscard.generic.PDContact;
import com.helger.pd.businesscard.generic.PDIdentifier;
import com.helger.pd.businesscard.generic.PDName;

/**
 * Helper class for easier BC V3 creation.
 *
 * @author Philip Helger
 */
@Immutable
public final class PD3APIHelper
{
  private PD3APIHelper ()
  {}

  @Nonnull
  public static PD3IdentifierType createIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final PD3IdentifierType ret = new PD3IdentifierType ();
    ret.setScheme (sScheme);
    ret.setValue (sValue);
    return ret;
  }

  @Nullable
  public static PDIdentifier createIdentifier (@Nonnull final PD3IdentifierType aID)
  {
    ValueEnforcer.notNull (aID, "ID");
    return new PDIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static PD3ContactType createContact (@Nullable final String sType,
                                              @Nullable final String sName,
                                              @Nullable final String sPhoneNumber,
                                              @Nullable final String sEmail)
  {
    final PD3ContactType ret = new PD3ContactType ();
    ret.setType (sType);
    ret.setName (sName);
    ret.setPhoneNumber (sPhoneNumber);
    ret.setEmail (sEmail);
    return ret;
  }

  @Nonnull
  public static PDContact createContact (@Nonnull final PD3ContactType aContact)
  {
    ValueEnforcer.notNull (aContact, "Contact");
    return new PDContact (aContact.getType (), aContact.getName (), aContact.getPhoneNumber (), aContact.getEmail ());
  }

  @Nonnull
  public static PD3MultilingualNameType createName (@Nullable final String sName, @Nullable final String sLanguage)
  {
    final PD3MultilingualNameType ret = new PD3MultilingualNameType ();
    ret.setValue (sName);
    ret.setLanguage (sLanguage);
    return ret;
  }

  @Nonnull
  public static PDName createName (@Nonnull final PD3MultilingualNameType aName)
  {
    ValueEnforcer.notNull (aName, "Name");
    return new PDName (aName.getValue (), aName.getLanguage ());
  }

  @Nonnull
  public static PDBusinessEntity createBusinessEntity (@Nonnull final PD3BusinessEntityType aBE)
  {
    ValueEnforcer.notNull (aBE, "BusinessEntity");
    final PDBusinessEntity ret = new PDBusinessEntity ();
    ret.names ().setAllMapped (aBE.getName (), PD3APIHelper::createName);
    ret.setCountryCode (aBE.getCountryCode ());
    ret.setGeoInfo (aBE.getGeographicalInformation ());
    ret.identifiers ().setAllMapped (aBE.getIdentifier (), PD3APIHelper::createIdentifier);
    ret.websiteURIs ().setAll (aBE.getWebsiteURI ());
    ret.contacts ().setAllMapped (aBE.getContact (), PD3APIHelper::createContact);
    ret.setAdditionalInfo (aBE.getAdditionalInformation ());
    ret.setRegistrationDate (aBE.getRegistrationDate ());
    return ret;
  }

  @Nonnull
  public static PDBusinessCard createBusinessCard (@Nonnull final PD3BusinessCardType aBC)
  {
    ValueEnforcer.notNull (aBC, "BusinessCard");
    final PDBusinessCard ret = new PDBusinessCard ();
    ret.setParticipantIdentifier (createIdentifier (aBC.getParticipantIdentifier ()));
    ret.businessEntities ().setAllMapped (aBC.getBusinessEntity (), PD3APIHelper::createBusinessEntity);
    return ret;
  }
}

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
package com.helger.peppol.businesscard.v1;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.peppol.businesscard.generic.PDBusinessCard;
import com.helger.peppol.businesscard.generic.PDBusinessEntity;
import com.helger.peppol.businesscard.generic.PDContact;
import com.helger.peppol.businesscard.generic.PDIdentifier;
import com.helger.peppol.businesscard.generic.PDName;

/**
 * Helper class for easier BC V1 creation.
 *
 * @author Philip Helger
 */
@Immutable
public final class PD1APIHelper
{
  private PD1APIHelper ()
  {}

  @Nonnull
  public static PD1IdentifierType createIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final PD1IdentifierType ret = new PD1IdentifierType ();
    ret.setScheme (sScheme);
    ret.setValue (sValue);
    return ret;
  }

  @Nullable
  public static PDIdentifier createIdentifier (@Nonnull final PD1IdentifierType aID)
  {
    ValueEnforcer.notNull (aID, "ID");
    return new PDIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static PD1ContactType createContact (@Nullable final String sType,
                                              @Nullable final String sName,
                                              @Nullable final String sPhoneNumber,
                                              @Nullable final String sEmail)
  {
    final PD1ContactType ret = new PD1ContactType ();
    ret.setType (sType);
    ret.setName (sName);
    ret.setPhoneNumber (sPhoneNumber);
    ret.setEmail (sEmail);
    return ret;
  }

  @Nonnull
  public static PDContact createContact (@Nonnull final PD1ContactType aContact)
  {
    ValueEnforcer.notNull (aContact, "Contact");
    return new PDContact (aContact.getType (), aContact.getName (), aContact.getPhoneNumber (), aContact.getEmail ());
  }

  @Nonnull
  public static PDBusinessEntity createBusinessEntity (@Nonnull final PD1BusinessEntityType aBE)
  {
    ValueEnforcer.notNull (aBE, "BusinessEntity");
    final PDBusinessEntity ret = new PDBusinessEntity ();
    // No language available
    ret.names ().add (new PDName (aBE.getName ()));
    ret.setCountryCode (aBE.getCountryCode ());
    ret.setGeoInfo (aBE.getGeographicalInformation ());
    ret.identifiers ().setAllMapped (aBE.getIdentifier (), PD1APIHelper::createIdentifier);
    ret.websiteURIs ().setAll (aBE.getWebsiteURI ());
    ret.contacts ().setAllMapped (aBE.getContact (), PD1APIHelper::createContact);
    ret.setAdditionalInfo (aBE.getAdditionalInformation ());
    ret.setRegistrationDate (aBE.getRegistrationDate ());
    return ret;
  }

  @Nonnull
  public static PDBusinessCard createBusinessCard (@Nonnull final PD1BusinessCardType aBC)
  {
    ValueEnforcer.notNull (aBC, "BusinessCard");
    final PDBusinessCard ret = new PDBusinessCard ();
    ret.setParticipantIdentifier (createIdentifier (aBC.getParticipantIdentifier ()));
    ret.businessEntities ().setAllMapped (aBC.getBusinessEntity (), PD1APIHelper::createBusinessEntity);
    return ret;
  }
}

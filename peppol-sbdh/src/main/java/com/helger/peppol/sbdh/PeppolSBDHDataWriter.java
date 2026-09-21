/*
 * Copyright (C) 2014-2026 Philip Helger
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
package com.helger.peppol.sbdh;

import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.unece.cefact.namespaces.sbdh.BusinessScope;
import org.unece.cefact.namespaces.sbdh.Scope;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocumentHeader;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.string.StringHelper;
import com.helger.edelivery.sbdh.AbstractSBDHDataWriter;

/**
 * Convert a Peppol SBDH document to a regular SBDH document.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolSBDHDataWriter extends AbstractSBDHDataWriter <PeppolSBDHData, PeppolSBDHDataWriter>
{
  public PeppolSBDHDataWriter ()
  {}

  @Override
  protected void checkData (@NonNull final PeppolSBDHData aData)
  {
    if (!aData.areAllFieldsSet ())
      throw new IllegalArgumentException ("Not all data fields are set!");
  }

  @Override
  protected void fillBusinessScope (@NonNull final StandardBusinessDocumentHeader aSBDH,
                                    @NonNull final PeppolSBDHData aData)
  {
    final BusinessScope aBusinessScope = new BusinessScope ();
    {
      final Scope aScope = new Scope ();
      aScope.setType (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID);
      aScope.setInstanceIdentifier (StringHelper.trim (aData.getDocumentTypeValue ()));
      // The scheme was added in Spec v1.1
      aScope.setIdentifier (StringHelper.trim (aData.getDocumentTypeScheme ()));
      aBusinessScope.addScope (aScope);
    }

    {
      final Scope aScope = new Scope ();
      aScope.setType (CPeppolSBDH.SCOPE_PROCESS_ID);
      aScope.setInstanceIdentifier (StringHelper.trim (aData.getProcessValue ()));
      // The scheme was added in Spec v1.1
      aScope.setIdentifier (StringHelper.trim (aData.getProcessScheme ()));
      aBusinessScope.addScope (aScope);
    }

    {
      // The Country C1 was added in Spec v2.0
      final Scope aScope = new Scope ();
      aScope.setType (CPeppolSBDH.SCOPE_COUNTRY_C1);
      aScope.setInstanceIdentifier (StringHelper.trim (aData.getCountryC1 ()));
      aBusinessScope.addScope (aScope);
    }

    if (aData.hasMLSToValue ())
    {
      // The MLS_TO was added in Peppol MLS spec
      final Scope aScope = new Scope ();
      aScope.setType (CPeppolSBDH.SCOPE_MLS_TO);
      aScope.setInstanceIdentifier (StringHelper.trim (aData.getMLSToValue ()));
      aScope.setIdentifier (StringHelper.trim (aData.getMLSToScheme ()));
      aBusinessScope.addScope (aScope);
    }

    if (aData.hasMLSType ())
    {
      // The MLS_TYPE was added in Peppol MLS spec
      final Scope aScope = new Scope ();
      aScope.setType (CPeppolSBDH.SCOPE_MLS_TYPE);
      aScope.setInstanceIdentifier (aData.getMLSType ().getID ());
      aBusinessScope.addScope (aScope);
    }

    // Add the additional attributes
    for (final Map.Entry <String, String> aEntry : aData.additionalAttributes ().entrySet ())
    {
      final Scope aScope = new Scope ();
      aScope.setType (StringHelper.trim (aEntry.getKey ()));
      // XSD requires InstanceIdentifier
      aScope.setInstanceIdentifier (StringHelper.getNotNull (aEntry.getValue ()).trim ());
      aBusinessScope.addScope (aScope);
    }

    aSBDH.setBusinessScope (aBusinessScope);
  }
}

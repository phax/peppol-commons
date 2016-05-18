/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.participant;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.IPeppolIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.validator.IdentifierValidator;

/**
 * Base interface for a PEPPOL read-only participant identifier.
 *
 * @author philip
 */
public interface IPeppolParticipantIdentifier extends IPeppolIdentifier, IParticipantIdentifier
{
  default boolean isDefaultScheme ()
  {
    return IdentifierHelper.hasDefaultParticipantIdentifierScheme (this);
  }

  /**
   * @return <code>true</code> if the identifier is valid according to the
   *         internal and external validation rules as defined by
   *         {@link com.helger.peppol.identifier.validator.IParticipantIdentifierValidatorSPI}
   *         implementations.
   */
  default boolean isValid ()
  {
    return IdentifierValidator.isValidParticipantIdentifier (this);
  }

  /**
   * Extract the issuing agency ID from the passed participant identifier value.
   * <br>
   * Example: extract the <code>0088</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code>
   *
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  default String getIssuingAgencyID ()
  {
    return IdentifierHelper.getIssuingAgencyIDFromParticipantIDValue (this);
  }

  /**
   * Extract the local participant ID from the passed participant identifier
   * value.<br>
   * Example: extract the <code>123456</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code>
   *
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  default String getLocalParticipantID ()
  {
    return IdentifierHelper.getLocalParticipantIDFromParticipantIDValue (this);
  }
}

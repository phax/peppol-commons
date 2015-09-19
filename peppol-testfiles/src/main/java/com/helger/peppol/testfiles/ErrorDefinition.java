/**
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
package com.helger.peppol.testfiles;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.error.EErrorLevel;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

public final class ErrorDefinition implements Comparable <ErrorDefinition>
{
  private final EErrorLevel m_eLevel;
  private final String m_sErrorCode;

  private ErrorDefinition (@Nonnull final EErrorLevel eLevel, @Nonnull @Nonempty final String sErrorCode)
  {
    ValueEnforcer.notNull (eLevel, "Level");
    ValueEnforcer.notEmpty (sErrorCode, "ErrorCode");
    m_eLevel = eLevel;
    m_sErrorCode = sErrorCode;
  }

  @Nonnull
  public EErrorLevel getLevel ()
  {
    return m_eLevel;
  }

  @Nonnull
  @Nonempty
  public String getErrorCode ()
  {
    return m_sErrorCode;
  }

  public int compareTo (@Nonnull final ErrorDefinition rhs)
  {
    int i = m_eLevel.compareTo (rhs.m_eLevel);
    if (i == 0)
      i = m_sErrorCode.compareTo (rhs.m_sErrorCode);
    return i;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ErrorDefinition rhs = (ErrorDefinition) o;
    return m_eLevel.equals (rhs.m_eLevel) && m_sErrorCode.equals (rhs.m_sErrorCode);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eLevel).append (m_sErrorCode).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("level", m_eLevel).append ("errorCode", m_sErrorCode).toString ();
  }

  @Nonnull
  public static ErrorDefinition createWarning (@Nonnull @Nonempty final String sErrorCode)
  {
    return new ErrorDefinition (EErrorLevel.WARN, sErrorCode);
  }

  @Nonnull
  public static ErrorDefinition createError (@Nonnull @Nonempty final String sErrorCode)
  {
    return new ErrorDefinition (EErrorLevel.FATAL_ERROR, sErrorCode);
  }
}

/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.url;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.base.tostring.ToStringGenerator;

/**
 * Checked exception to be thrown if DNS resolution fails.
 *
 * @author Philip Helger
 * @since 8.2.0
 */
public class SMPDNSResolutionException extends Exception
{
  /**
   * Contains the allowed error codes for this exception
   *
   * @author Philip Helger
   * @since 12.3.2
   */
  public enum EErrorCode implements IHasID <String>
  {
    /**
     * The provided DNS domain name is invalid.
     */
    DOMAIN_NAME_SYNTAX_ERROR ("dname-err"),
    /**
     * Error resolving the necessary DNS record.
     */
    DNS_RESOLVING_ERROR ("dnsresolve-err"),
    /**
     * The resolved SMP URI is invalid.
     */
    RESOLVED_URI_SYNTAX_ERROR ("resolveduri-err");

    @NonNull
    private final String m_sID;

    EErrorCode (@NonNull @Nonempty final String sID)
    {
      m_sID = sID;
    }

    @NonNull
    @Nonempty
    public String getID ()
    {
      return m_sID;
    }

    @Nullable
    public static EErrorCode getFromIDOrNull (@Nullable final String sID)
    {
      return EnumHelper.getFromIDOrNull (EErrorCode.class, sID);
    }
  }

  private final @NonNull EErrorCode m_eErrorCode;

  public SMPDNSResolutionException (@NonNull final EErrorCode eErrorCode, @NonNull final String sMessage)
  {
    super (sMessage);
    m_eErrorCode = ValueEnforcer.notNull (eErrorCode, "ErrorCode");
  }

  public SMPDNSResolutionException (@NonNull final EErrorCode eErrorCode,
                                    @NonNull final String sMessage,
                                    @Nullable final Throwable aCause)
  {
    super (sMessage, aCause);
    m_eErrorCode = ValueEnforcer.notNull (eErrorCode, "ErrorCode");
  }

  /**
   * @return The reason for the exception. Never <code>null</code>.
   * @since 12.3.2
   */
  @NonNull
  public final EErrorCode getErrorCode ()
  {
    return m_eErrorCode;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("ErrorCode", m_eErrorCode).getToString ();
  }
}

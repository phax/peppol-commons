/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.peppol.smlclient.tls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.http.tls.ETLSVersion;
import com.helger.http.tls.ITLSConfigurationMode;
import com.helger.http.tls.TLSConfigurationMode;

/**
 * TLS cipher suite configuration modes for CEF SML starting in 2020.
 *
 * @author Philip Helger
 * @since 8.0.2
 */
public enum ETLSConfigurationMode_CEF_SML implements IHasID <String>, ITLSConfigurationMode
{
  HIGH ("high",
        new ETLSVersion [] { ETLSVersion.TLS_12 },
        new String [] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
                        "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256",
                        "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384" }),
  MEDIUM ("medium",
          new ETLSVersion [] { ETLSVersion.TLS_12 },
          new String [] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
                          "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256",
                          "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
                          "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
                          "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA" });

  private final String m_sID;
  private final TLSConfigurationMode m_aMode;

  private ETLSConfigurationMode_CEF_SML (@Nonnull @Nonempty final String sID,
                                         @Nonnull @Nonempty final ETLSVersion [] aTLSVersions,
                                         @Nonnull @Nonempty final String [] aCipherSuites)
  {
    m_sID = sID;
    m_aMode = new TLSConfigurationMode (aTLSVersions, aCipherSuites);
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllCipherSuites ()
  {
    return m_aMode.getAllCipherSuites ();
  }

  @Nonnull
  @ReturnsMutableCopy
  @Override
  public String [] getAllCipherSuitesAsArray ()
  {
    return m_aMode.getAllCipherSuitesAsArray ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <ETLSVersion> getAllTLSVersions ()
  {
    return m_aMode.getAllTLSVersions ();
  }

  @Nonnull
  @ReturnsMutableCopy
  @Override
  public ICommonsList <String> getAllTLSVersionIDs ()
  {
    return m_aMode.getAllTLSVersionIDs ();
  }

  @Nonnull
  @ReturnsMutableCopy
  @Override
  public String [] getAllTLSVersionIDsAsArray ()
  {
    return m_aMode.getAllTLSVersionIDsAsArray ();
  }

  @Nullable
  public static ETLSConfigurationMode_CEF_SML getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ETLSConfigurationMode_CEF_SML.class, sID);
  }
}

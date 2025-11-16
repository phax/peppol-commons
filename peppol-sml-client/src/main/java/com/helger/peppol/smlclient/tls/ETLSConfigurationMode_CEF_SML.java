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
package com.helger.peppol.smlclient.tls;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.collection.commons.ICommonsList;
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

  ETLSConfigurationMode_CEF_SML (@NonNull @Nonempty final String sID,
                                 @NonNull @Nonempty final ETLSVersion [] aTLSVersions,
                                 @NonNull @Nonempty final String [] aCipherSuites)
  {
    m_sID = sID;
    m_aMode = new TLSConfigurationMode (aTLSVersions, aCipherSuites);
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllCipherSuites ()
  {
    return m_aMode.getAllCipherSuites ();
  }

  @NonNull
  @ReturnsMutableCopy
  @Override
  public String [] getAllCipherSuitesAsArray ()
  {
    return m_aMode.getAllCipherSuitesAsArray ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <ETLSVersion> getAllTLSVersions ()
  {
    return m_aMode.getAllTLSVersions ();
  }

  @NonNull
  @ReturnsMutableCopy
  @Override
  public ICommonsList <String> getAllTLSVersionIDs ()
  {
    return m_aMode.getAllTLSVersionIDs ();
  }

  @NonNull
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

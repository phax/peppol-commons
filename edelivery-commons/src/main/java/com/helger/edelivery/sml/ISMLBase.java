/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.edelivery.sml;

import java.io.Serializable;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.MustImplementEqualsAndHashcode;
import com.helger.base.name.IHasDisplayName;
import com.helger.base.type.ITypedObject;

/**
 * The network neutral part of an SML definition. It contains the data that is needed to <b>use</b>
 * an SML - the DNS zone to be queried and whether a client certificate is required. Everything that
 * is needed to <b>manage</b> entries in an SML is network specific and therefore not contained
 * here.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@MustImplementEqualsAndHashcode
public interface ISMLBase extends ITypedObject <String>, IHasDisplayName, Serializable
{
  /**
   * @return The "shorthand" display name like "SML" or "SMK".
   */
  @NonNull
  @Nonempty
  String getDisplayName ();

  /**
   * @return The DNS zone on which this SML is operating. Never <code>null</code>. It must be
   *         ensured that the value consists only of lower case characters!<br>
   *         Example: <code>sml.peppolcentral.org</code>
   */
  @NonNull
  @Nonempty
  String getDNSZone ();

  /**
   * @return <code>true</code> if this SML requires a client certificate for access,
   *         <code>false</code> otherwise. Only a locally running SML software may not require a
   *         client certificate.
   */
  boolean isClientCertificateRequired ();
}

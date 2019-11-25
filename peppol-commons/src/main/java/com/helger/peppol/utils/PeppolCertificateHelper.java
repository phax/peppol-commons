/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import java.security.cert.X509Certificate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * A specific Peppol certificate helper
 *
 * @author Philip Helger
 * @since 7.0.3
 */
@Immutable
public final class PeppolCertificateHelper
{
  private PeppolCertificateHelper ()
  {}

  @Nullable
  public static String getSubjectCN (@Nullable final X509Certificate aCert) throws InvalidNameException
  {
    return aCert != null ? getCN (aCert.getSubjectX500Principal ().getName ()) : null;
  }

  @Nullable
  public static String getCN (@Nullable final String sPrincipal) throws InvalidNameException
  {
    if (sPrincipal != null)
      for (final Rdn aRdn : new LdapName (sPrincipal).getRdns ())
        if (aRdn.getType ().equalsIgnoreCase ("CN"))
          return (String) aRdn.getValue ();
    return null;
  }
}

package com.helger.peppol.utils;

import java.security.cert.X509Certificate;

import javax.annotation.Nullable;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * A specific Peppol certificate helper
 *
 * @author Philip Helger
 * @since 7.0.3
 */
public class PeppolCertificateHelper
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

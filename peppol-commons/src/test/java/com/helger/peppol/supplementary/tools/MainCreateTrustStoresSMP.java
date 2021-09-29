package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.utils.PeppolKeyStoreHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.keystore.LoadedKeyStore;

public class MainCreateTrustStoresSMP
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateTrustStoresSMP.class);

  public static void main (final String [] args) throws Exception
  {
    for (final String sType : new String [] { "pilot", "prod" })
    {
      final KeyStore aSMPTrustStore = EKeyStoreType.JKS.getKeyStore ();
      // null stream means: create new key store
      aSMPTrustStore.load (null, null);

      for (final String sTS : new String [] { "directory", "sml", "2018/" + sType })
      {
        final LoadedKeyStore aLKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.JKS,
                                                                 "truststore/" + sTS + "-truststore.jks",
                                                                 PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
        final Enumeration <String> aAliases = aLKS.getKeyStore ().aliases ();
        while (aAliases.hasMoreElements ())
        {
          final String sAlias = aAliases.nextElement ();
          // No key password
          aSMPTrustStore.setEntry (sAlias, aLKS.getKeyStore ().getEntry (sAlias, null), null);
        }
      }

      final File fDest = new File ("src/main/resources/truststore/2018/smp-" + sType + "-truststore.jks");
      try (final OutputStream aFOS = new FileOutputStream (fDest))
      {
        aSMPTrustStore.store (aFOS, PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD.toCharArray ());
      }
      LOGGER.info ("Wrote " + fDest.getPath ());
    }
  }
}

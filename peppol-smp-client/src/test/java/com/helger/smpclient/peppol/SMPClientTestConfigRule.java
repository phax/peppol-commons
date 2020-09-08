package com.helger.smpclient.peppol;

import org.junit.rules.ExternalResource;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.config.Config;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.config.source.res.ConfigurationSourceProperties;
import com.helger.smpclient.config.SMPClientConfiguration;

public final class SMPClientTestConfigRule extends ExternalResource
{
  private IConfig m_aOldConfig;

  @Override
  protected void before () throws Throwable
  {
    final MultiConfigurationValueProvider aVP = ConfigFactory.createDefaultValueProvider ();
    aVP.addConfigurationSource (new ConfigurationSourceProperties (new ClassPathResource ("smp-client-test.properties")));
    m_aOldConfig = SMPClientConfiguration.setConfig (new Config (aVP));
    SMPClientConfiguration.applyAllNetworkSystemProperties ();
  }

  @Override
  protected void after ()
  {
    SMPClientConfiguration.setConfig (m_aOldConfig);
  }
}

/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.smpclient.peppol;

import org.junit.rules.ExternalResource;

import com.helger.config.ConfigFactory;
import com.helger.config.fallback.ConfigWithFallback;
import com.helger.config.fallback.IConfigWithFallback;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.config.source.resource.properties.ConfigurationSourceProperties;
import com.helger.io.resource.ClassPathResource;
import com.helger.smpclient.config.SMPClientConfiguration;

public final class SMPClientTestConfigRule extends ExternalResource
{
  private IConfigWithFallback m_aOldConfig;

  @Override
  protected void before () throws Throwable
  {
    final MultiConfigurationValueProvider aVP = ConfigFactory.createDefaultValueProvider ();
    aVP.addConfigurationSource (new ConfigurationSourceProperties (new ClassPathResource ("smp-client-test.properties")));
    m_aOldConfig = SMPClientConfiguration.setConfig (new ConfigWithFallback (aVP));
  }

  @Override
  protected void after ()
  {
    SMPClientConfiguration.setConfig (m_aOldConfig);
  }
}

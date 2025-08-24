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
package com.helger.peppol.smlclient;

import com.helger.annotation.concurrent.Immutable;
import com.helger.config.Config;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.config.source.resource.properties.ConfigurationSourceProperties;
import com.helger.io.resource.ClassPathResource;

import jakarta.annotation.Nullable;

/**
 * This class manages the special test configuration file for this project. The configuration file
 * is located in <code>src/test/resources/sml-client-test.properties</code>
 *
 * @author Philip Helger
 */
@Immutable
public final class MockSMLClientConfig
{
  private static final IConfig TEST_CONFIG;
  static
  {
    final MultiConfigurationValueProvider aCVP = ConfigFactory.createDefaultValueProvider ();
    aCVP.addConfigurationSource (new ConfigurationSourceProperties (new ClassPathResource ("sml-client-test.properties")));
    TEST_CONFIG = Config.create (aCVP);
  }

  private MockSMLClientConfig ()
  {}

  @Nullable
  public static String getKeyStorePath ()
  {
    return TEST_CONFIG.getAsString ("keystore.path");
  }

  @Nullable
  public static char [] getKeyStorePassword ()
  {
    return TEST_CONFIG.getAsCharArray ("keystore.password");
  }
}

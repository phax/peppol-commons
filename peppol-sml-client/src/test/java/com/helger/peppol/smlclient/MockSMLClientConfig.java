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
package com.helger.peppol.smlclient;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.settings.exchange.configfile.ConfigFile;
import com.helger.settings.exchange.configfile.ConfigFileBuilder;

/**
 * This class manages the special test configuration file for this project. The
 * configuration file is located in
 * <code>src/test/resources/sml-client-test.properties</code>
 *
 * @author Philip Helger
 */
@Immutable
public final class MockSMLClientConfig
{
  private static final ConfigFile s_aConfig = new ConfigFileBuilder ().addPath ("private-sml-client-test.properties")
                                                                      .addPath ("sml-client-test.properties")
                                                                      .build ();

  // init
  static
  {
    // Apply system properties
    s_aConfig.applyAllNetworkSystemProperties ();
  }

  private MockSMLClientConfig ()
  {}

  @Nullable
  public static String getKeyStorePath ()
  {
    return s_aConfig.getAsString ("keystore.path");
  }

  @Nullable
  public static String getKeyStorePassword ()
  {
    return s_aConfig.getAsString ("keystore.password");
  }
}

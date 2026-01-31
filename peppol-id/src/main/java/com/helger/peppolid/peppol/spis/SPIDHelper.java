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
package com.helger.peppolid.peppol.spis;

import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.cache.regex.RegExHelper;

/**
 * Helper class for dealing with Peppol Service Provider IDs.
 *
 * @author Philip Helger
 * @since 12.3.6
 */
@Immutable
public final class SPIDHelper
{
  private static final String _MAIN_ID = "[0-9]{6}";
  public static final String REGEX_MAIN_ID = "^" + _MAIN_ID + "$";

  private static final String _USE_CASE_ID = "[0-9A-Z_]{3,12}";
  public static final String REGEX_USE_CASE_ID = "^(?i)" + _USE_CASE_ID + "$";

  private static final String _SERVICE_PROVIDER_SUFFIX = "[0-9A-Z\\-\\._~]{3,24}";
  public static final String REGEX_SERVICE_PROVIDER_SUFFIX = "^(?i)" + _SERVICE_PROVIDER_SUFFIX + "$";

  public static final String REGEX_COMPLETE = "^(?i)" +
                                              _MAIN_ID +
                                              "(-" +
                                              _USE_CASE_ID +
                                              "(\\." +
                                              _SERVICE_PROVIDER_SUFFIX +
                                              ")?)?$";

  private SPIDHelper ()
  {}

  public static boolean isValidMainID (@Nullable final String s)
  {
    return s != null && s.length () == 6 && RegExHelper.stringMatchesPattern (REGEX_MAIN_ID, s);
  }

  public static boolean isValidUseCaseID (@Nullable final String s)
  {
    return s != null &&
           s.length () >= 3 &&
           s.length () <= 12 &&
           RegExHelper.stringMatchesPattern (REGEX_USE_CASE_ID, s);
  }

  public static boolean isValidServiceProviderSuffix (@Nullable final String s)
  {
    return s != null &&
           s.length () >= 3 &&
           s.length () <= 24 &&
           RegExHelper.stringMatchesPattern (REGEX_SERVICE_PROVIDER_SUFFIX, s);
  }

  public static boolean isValidSPID (@Nullable final String s)
  {
    return s != null && s.length () >= 6 && s.length () <= 44 && RegExHelper.stringMatchesPattern (REGEX_COMPLETE, s);
  }
}

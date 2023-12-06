/*
 * Copyright (C) 2023 Philip Helger
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
package com.helger.peppol.mlr;

import javax.annotation.concurrent.Immutable;

/**
 * Constants for Peppol MLR (Message Level Response)
 *
 * @author Philip Helger
 */
@Immutable
public class CPeppolMLR
{
  public static final String MLR_CUSTOMIZATION_ID = "urn:fdc:peppol.eu:poacc:trns:mlr:3";
  public static final String MLR_PROFILE_ID = "urn:fdc:peppol.eu:poacc:bis:mlr:3";

  private CPeppolMLR ()
  {}
}

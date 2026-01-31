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
package com.helger.peppolid.peppol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.Month;

import com.helger.datetime.helper.PDTFactory;

/**
 * This element is specific to the Peppol Policy for use of Identifiers 4.3.0 and should only be
 * used carefully before May 15th, 2025.
 *
 * @author Philip Helger
 */
@Deprecated (forRemoval = true, since = "12.3.0")
@Retention (RetentionPolicy.SOURCE)
@Target ({ ElementType.TYPE,
           ElementType.METHOD,
           ElementType.CONSTRUCTOR,
           ElementType.FIELD,
           ElementType.LOCAL_VARIABLE })
public @interface Pfuoi430
{
  /** The first date (incl.) this specification is valid */
  static LocalDate VALID_FROM = PDTFactory.createLocalDate (2025, Month.MAY, 15);

  /** The last date (incl.) this specification is valid */
  static LocalDate VALID_UNTIL = PDTFactory.createLocalDate (2025, Month.OCTOBER, 31);

  String value() default "";
}

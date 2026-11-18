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
package com.helger.peppol.smlclient.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an SML client API (type, method, constructor or enum constant) that requires at least the
 * provided version of the BDMSL (DomiSML) implementation on the server side. Calling it against an
 * older SML fails, usually with a SOAP fault, because the operation or the behaviour is unknown
 * there. APIs without this annotation work with all supported BDMSL versions. Search for this
 * annotation to find all APIs that need a certain BDMSL version.
 *
 * @author Philip Helger
 * @since 13.3.0
 */
@Documented
@Retention (RetentionPolicy.SOURCE)
@Target ({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
public @interface SinceBDMSL
{
  /**
   * @return The minimum required BDMSL version, e.g. <code>"5.1"</code>.
   */
  String value();
}

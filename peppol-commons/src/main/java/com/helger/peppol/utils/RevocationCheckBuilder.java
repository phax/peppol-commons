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
package com.helger.peppol.utils;

import javax.annotation.concurrent.NotThreadSafe;

import com.helger.security.revocation.AbstractRevocationCheckBuilder;

/**
 * A generic revocation check builder that works with arbitrary certificates.
 * Contains no specific pre-configuration.
 *
 * @author Philip Helger
 * @since 9.6.0 a top-level class
 */
@NotThreadSafe
public class RevocationCheckBuilder extends AbstractRevocationCheckBuilder <RevocationCheckBuilder>
{}

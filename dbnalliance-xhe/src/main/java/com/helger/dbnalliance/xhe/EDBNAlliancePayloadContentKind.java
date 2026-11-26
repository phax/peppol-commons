/*
 * Copyright (C) 2024-2026 Philip Helger
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
package com.helger.dbnalliance.xhe;

/**
 * The kind of content of a DBNAlliance XHE payload (<code>XHE/Payloads/Payload/PayloadContent</code>).
 *
 * @author Philip Helger
 * @since 13.4.0
 */
public enum EDBNAlliancePayloadContentKind
{
  /** The content is an XML element */
  XML,
  /** The content is text, determined by a textual MIME type */
  TEXT,
  /** The content is Base64 encoded binary data */
  BINARY,
  /** There is no payload content */
  NONE
}

/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.xsds.peppol.smp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.io.resource.ClassPathResource;

/**
 * Test class for class {@link CSMPDataTypes}.
 *
 * @author Philip Helger
 */
public final class CSMPDataTypesTest
{
  @Test
  public void testBasic ()
  {
    assertTrue (CSMPDataTypes.getXSDResourcePeppolSMP ().exists ());
    assertTrue (CSMPDataTypes.getXSDResourcePeppolSMP ().canRead ());
    for (final ClassPathResource aRes : CSMPDataTypes.getAllXSDResources ())
      assertTrue (aRes.exists ());
  }
}

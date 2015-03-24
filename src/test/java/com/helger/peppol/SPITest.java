/**
 * Copyright (C) 2012-2015 winenet GmbH - www.winenet.at
 * All Rights Reserved
 *
 * This file is part of the winenet-Kellerbuch software.
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is
 * strictly prohibited.
 */
package com.helger.peppol;

import org.junit.Test;

import com.helger.commons.mock.PHTestUtils;

public final class SPITest
{
  @Test
  public void testBasic () throws Exception
  {
    PHTestUtils.testIfAllSPIImplementationsAreValid ();
  }
}

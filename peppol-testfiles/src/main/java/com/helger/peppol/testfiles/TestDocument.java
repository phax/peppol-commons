/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppol.testfiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class represents a single test document
 *
 * @author Philip Helger
 */
public final class TestDocument
{
  private final String m_sFilename;
  private final ICommonsSet <ErrorDefinition> m_aExpectedErrors = new CommonsHashSet <> ();

  public TestDocument (@Nonnull @Nonempty final String sFilename, @Nullable final ErrorDefinition... aExpectedErrors)
  {
    ValueEnforcer.notEmpty (sFilename, "Filename");

    m_sFilename = sFilename;
    if (aExpectedErrors != null)
      for (final ErrorDefinition aExpectedError : aExpectedErrors)
        if (aExpectedError != null)
          m_aExpectedErrors.add (aExpectedError);
  }

  /**
   * @return The filename of the underlying resources. Neither <code>null</code>
   *         nor empty.
   */
  @Nonnull
  @Nonempty
  public String getFilename ()
  {
    return m_sFilename;
  }

  /**
   * @return The expected validation errors. Never <code>null</code> but maybe
   *         empty.
   */
  @Nonnull
  @ReturnsMutableCopy
  public ICommonsSet <ErrorDefinition> getAllExpectedErrors ()
  {
    return m_aExpectedErrors.getClone ();
  }

  /**
   * @return <code>true</code> if at least one expected error is contained
   */
  public boolean hasExpectedErrors ()
  {
    return m_aExpectedErrors.isNotEmpty ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("filename", m_sFilename).append ("expectedErrors", m_aExpectedErrors).getToString ();
  }
}

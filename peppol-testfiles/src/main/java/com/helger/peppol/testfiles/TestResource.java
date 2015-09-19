/**
 * Copyright (C) 2015 Philip Helger
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

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class represents a single test resource
 *
 * @author Philip Helger
 */
public final class TestResource
{
  private final IReadableResource m_aRes;
  private final Set <ErrorDefinition> m_aExpectedErrors = new HashSet <ErrorDefinition> ();

  public TestResource (@Nonnull final IReadableResource aRes, @Nullable final Set <ErrorDefinition> aExpectedErrors)
  {
    ValueEnforcer.notNull (aRes, "Resource");

    m_aRes = aRes;
    if (aExpectedErrors != null)
      for (final ErrorDefinition aExpectedError : aExpectedErrors)
        if (aExpectedError != null)
          m_aExpectedErrors.add (aExpectedError);
  }

  /**
   * @return The resource of the document. Never <code>null</code>.
   */
  @Nonnull
  public IReadableResource getResource ()
  {
    return m_aRes;
  }

  /**
   * @return The filename of the underlying resources. Neither <code>null</code>
   *         nor empty.
   */
  @Nonnull
  @Nonempty
  public String getFilename ()
  {
    return m_aRes.getPath ();
  }

  /**
   * @return The expected validation errors. Never <code>null</code> but maybe
   *         empty.
   */
  @Nonnull
  @ReturnsMutableCopy
  public Set <ErrorDefinition> getAllExpectedErrors ()
  {
    return CollectionHelper.newSet (m_aExpectedErrors);
  }

  /**
   * @return <code>true</code> if at least one expected error is contained
   */
  public boolean hasExpectedErrors ()
  {
    return !m_aExpectedErrors.isEmpty ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("resource", m_aRes)
                                       .append ("expectedErrors", m_aExpectedErrors)
                                       .toString ();
  }

  @Nonnull
  public static TestResource createGoodCase (@Nonnull final String sClassPathResource)
  {
    return new TestResource (new ClassPathResource (sClassPathResource), null);
  }
}

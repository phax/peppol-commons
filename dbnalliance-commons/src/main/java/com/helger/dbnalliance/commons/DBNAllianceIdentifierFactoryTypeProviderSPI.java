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
package com.helger.dbnalliance.commons;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.style.IsSPIImplementation;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.peppolid.factory.IIdentifierFactoryType;
import com.helger.peppolid.factory.IIdentifierFactoryTypeProviderSPI;

/**
 * Provide the DBNAlliance identifier factory type to the central registry.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@IsSPIImplementation
public final class DBNAllianceIdentifierFactoryTypeProviderSPI implements IIdentifierFactoryTypeProviderSPI
{
  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <IIdentifierFactoryType> getAllIdentifierFactoryTypes ()
  {
    return new CommonsArrayList <> (EDBNAllianceIdentifierType.values ());
  }
}

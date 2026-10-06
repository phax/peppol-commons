/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.phosssmp;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.MustImplementEqualsAndHashcode;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsIterable;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.collection.helper.CollectionSort;
import com.helger.json.IHasJson;
import com.helger.json.IJsonArray;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;

/**
 * This represents a managed list of {@link SGCustomProperty}. The order is undefined but the name
 * uniqueness is verified. Names are case sensitive. Copied from phoss SMP.
 *
 * @author Philip Helger
 * @since 13.2.0
 */
@NotThreadSafe
@MustImplementEqualsAndHashcode
public class SGCustomPropertyList implements IHasJson, ICommonsIterable <SGCustomProperty>
{
  private final ICommonsOrderedMap <String, SGCustomProperty> m_aList = new CommonsLinkedHashMap <> ();

  public SGCustomPropertyList ()
  {}

  public SGCustomPropertyList (@Nullable final Iterable <SGCustomProperty> aProperties)
  {
    if (aProperties != null)
      for (final var aProp : aProperties)
        add (aProp);
  }

  public SGCustomPropertyList (@Nullable final SGCustomProperty @Nullable... aProperties)
  {
    if (aProperties != null)
      for (final var aProp : aProperties)
        add (aProp);
  }

  @NonNull
  public EChange add (@NonNull final SGCustomProperty aCustomProperty)
  {
    ValueEnforcer.notNull (aCustomProperty, "CustomProperty");

    final String sName = aCustomProperty.getName ();
    if (m_aList.containsKey (sName))
      return EChange.UNCHANGED;
    m_aList.put (sName, aCustomProperty);
    return EChange.CHANGED;
  }

  public boolean containsName (@Nullable final String sName)
  {
    return sName != null && m_aList.containsKey (sName);
  }

  @NonNull
  public EChange remove (@Nullable final String sName)
  {
    if (!SGCustomProperty.isValidName (sName))
      return EChange.UNCHANGED;
    return EChange.valueOf (m_aList.remove (sName) != null);
  }

  @Nullable
  public String getValue (@Nullable final String sCustomPropertyName)
  {
    if (sCustomPropertyName == null)
      return null;
    final SGCustomProperty aProperty = m_aList.get (sCustomPropertyName);
    return aProperty == null ? null : aProperty.getValue ();
  }

  public void forEach (@NonNull final Consumer <? super SGCustomProperty> aConsumer)
  {
    ValueEnforcer.notNull (aConsumer, "Consumer");
    m_aList.forEachValue (aConsumer);
  }

  public void forEach (@Nullable final Predicate <? super SGCustomProperty> aFilter,
                       @NonNull final Consumer <? super SGCustomProperty> aConsumer)
  {
    ValueEnforcer.notNull (aConsumer, "Consumer");
    m_aList.forEachValue (aFilter, aConsumer);
  }

  @Nonnegative
  public int size ()
  {
    return m_aList.size ();
  }

  public boolean isEmpty ()
  {
    return m_aList.isEmpty ();
  }

  public boolean isNotEmpty ()
  {
    return m_aList.isNotEmpty ();
  }

  @NonNull
  public IJsonArray getAsJson ()
  {
    return new JsonArray ().addAllMapped (m_aList.values (), SGCustomProperty::getAsJson);
  }

  @NonNull
  public Iterator <SGCustomProperty> iterator ()
  {
    return m_aList.values ().iterator ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <SGCustomProperty> getSorted (@NonNull final Comparator <? super SGCustomProperty> aComparator)
  {
    return CollectionSort.getSorted (m_aList.values (), aComparator);
  }

  @NonNull
  @ReturnsMutableCopy
  public SGCustomPropertyList getFiltered (@NonNull final Predicate <? super SGCustomProperty> aFilter)
  {
    return new SGCustomPropertyList (m_aList.copyOfValues (aFilter));
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final SGCustomPropertyList rhs = (SGCustomPropertyList) o;
    return m_aList.equals (rhs.m_aList);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aList).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("List", m_aList).getToString ();
  }

  @NonNull
  @ReturnsMutableObject
  public static SGCustomPropertyList fromJson (@NonNull final IJsonArray aJson)
  {
    final SGCustomPropertyList ret = new SGCustomPropertyList ();
    for (final IJsonObject aObj : aJson.iteratorObjects ())
      ret.add (SGCustomProperty.fromJson (aObj));
    return ret;
  }
}

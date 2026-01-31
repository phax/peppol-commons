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
package com.helger.peppol.businesscard.generic;

import java.io.Serializable;
import java.util.function.Predicate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.clone.ICloneable;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.json.IHasJson;
import com.helger.json.IJson;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.JsonObject;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;

/**
 * Generic business card.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PDBusinessCard implements IHasJson, Serializable, ICloneable <PDBusinessCard>
{
  private PDIdentifier m_aParticipantIdentifier;
  private final ICommonsList <PDBusinessEntity> m_aBusinessEntities = new CommonsArrayList <> ();

  public PDBusinessCard ()
  {}

  public PDBusinessCard (@Nullable final PDIdentifier aParticipantIdentifier,
                         @Nullable final ICommonsList <PDBusinessEntity> aEntities)
  {
    setParticipantIdentifier (aParticipantIdentifier);
    businessEntities ().setAll (aEntities);
  }

  /**
   * Gets the value of the participantIdentifier property.
   *
   * @return possible object is {@link PDIdentifier }
   */
  @Nullable
  public final PDIdentifier getParticipantIdentifier ()
  {
    return m_aParticipantIdentifier;
  }

  /**
   * Sets the value of the participantIdentifier property.
   *
   * @param aParticipantIdentifier
   *        allowed object is {@link PDIdentifier }
   * @return this for chaining
   */
  @NonNull
  public final PDBusinessCard setParticipantIdentifier (@Nullable final PDIdentifier aParticipantIdentifier)
  {
    m_aParticipantIdentifier = aParticipantIdentifier;
    return this;
  }

  /**
   * @return Mutable list of business entities.
   */
  @NonNull
  @ReturnsMutableObject
  public final ICommonsList <PDBusinessEntity> businessEntities ()
  {
    return m_aBusinessEntities;
  }

  /**
   * This method clones all values from <code>this</code> to the passed object. All data in the
   * parameter object is overwritten!
   *
   * @param ret
   *        The target object to clone to. May not be <code>null</code>.
   */
  public void cloneTo (@NonNull final PDBusinessCard ret)
  {
    ret.m_aParticipantIdentifier = m_aParticipantIdentifier;
    ret.m_aBusinessEntities.setAllMapped (m_aBusinessEntities, PDBusinessEntity::getClone);
  }

  @Override
  @NonNull
  @ReturnsMutableCopy
  public PDBusinessCard getClone ()
  {
    final PDBusinessCard ret = new PDBusinessCard ();
    cloneTo (ret);
    return ret;
  }

  @NonNull
  public IMicroElement getAsMicroXML (@Nullable final String sNamespaceURI,
                                      @NonNull @Nonempty final String sElementName)
  {
    final IMicroElement ret = new MicroElement (sNamespaceURI, sElementName);
    ret.addChild (m_aParticipantIdentifier.getAsMicroXML (sNamespaceURI, "participant"));
    for (final PDBusinessEntity aEntity : m_aBusinessEntities)
      ret.addChild (aEntity.getAsMicroXML (sNamespaceURI, "entity"));
    return ret;
  }

  @NonNull
  public IJsonObject getAsJson ()
  {
    final IJsonObject ret = new JsonObject ();
    ret.add ("participant", m_aParticipantIdentifier.getAsJson ());
    ret.add ("entity", new JsonArray ().addAllMapped (m_aBusinessEntities, PDBusinessEntity::getAsJson));
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PDBusinessCard rhs = ((PDBusinessCard) o);
    return EqualsHelper.equals (m_aParticipantIdentifier, rhs.m_aParticipantIdentifier) &&
           EqualsHelper.equals (m_aBusinessEntities, rhs.m_aBusinessEntities);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aParticipantIdentifier).append (m_aBusinessEntities).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ParticipantIdentifier", m_aParticipantIdentifier)
                                       .append ("Entities", m_aBusinessEntities)
                                       .getToString ();
  }

  @NonNull
  public static PDBusinessCard of (@NonNull final IJsonObject aJson)
  {
    final PDIdentifier aParticipantID = PDIdentifier.of (aJson.getAsObject ("participant"));
    final ICommonsList <PDBusinessEntity> aBusinessEntities = CommonsArrayList.createFiltered (aJson.getAsArray ("entity"),
                                                                                               (Predicate <IJson>) IJson::isObject,
                                                                                               x -> PDBusinessEntity.of (x.getAsObject ()));
    return new PDBusinessCard (aParticipantID, aBusinessEntities);
  }
}

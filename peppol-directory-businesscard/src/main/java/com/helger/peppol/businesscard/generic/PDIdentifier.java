/*
 * Copyright (C) 2015-2025 Philip Helger (www.helger.com)
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.json.IHasJson;
import com.helger.json.IJsonObject;
import com.helger.json.JsonObject;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;

/**
 * Generic identifier.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PDIdentifier implements IHasJson, Serializable, ICloneable <PDIdentifier>
{
  private String m_sScheme;
  private String m_sValue;

  public PDIdentifier ()
  {}

  public PDIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  /**
   * @return The identifier scheme. May be <code>null</code>.
   */
  @Nullable
  public final String getScheme ()
  {
    return m_sScheme;
  }

  /**
   * Set the identifier scheme.
   *
   * @param sScheme
   *        The scheme. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public final PDIdentifier setScheme (@Nullable final String sScheme)
  {
    m_sScheme = sScheme;
    return this;
  }

  /**
   * @return The identifier value. May be <code>null</code>.
   */
  @Nullable
  public final String getValue ()
  {
    return m_sValue;
  }

  /**
   * Set the identifier value.
   *
   * @param sValue
   *        The value. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public final PDIdentifier setValue (@Nullable final String sValue)
  {
    m_sValue = sValue;
    return this;
  }

  /**
   * This method clones all values from <code>this</code> to the passed object.
   * All data in the parameter object is overwritten!
   *
   * @param ret
   *        The target object to clone to. May not be <code>null</code>.
   */
  public void cloneTo (@NonNull final PDIdentifier ret)
  {
    ret.m_sScheme = m_sScheme;
    ret.m_sValue = m_sValue;
  }

  @NonNull
  @ReturnsMutableCopy
  public PDIdentifier getClone ()
  {
    final PDIdentifier ret = new PDIdentifier ();
    cloneTo (ret);
    return ret;
  }

  @NonNull
  public IMicroElement getAsMicroXML (@Nullable final String sNamespaceURI, @NonNull @Nonempty final String sElementName)
  {
    final IMicroElement ret = new MicroElement (sNamespaceURI, sElementName);
    ret.setAttribute ("scheme", m_sScheme);
    ret.setAttribute ("value", m_sValue);
    return ret;
  }

  @NonNull
  public static IJsonObject getAsJson (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final IJsonObject ret = new JsonObject ();
    ret.add ("scheme", sScheme);
    ret.add ("value", sValue);
    return ret;
  }

  @NonNull
  public IJsonObject getAsJson ()
  {
    return getAsJson (m_sScheme, m_sValue);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PDIdentifier rhs = (PDIdentifier) o;
    return EqualsHelper.equals (m_sScheme, rhs.m_sScheme) && EqualsHelper.equals (m_sValue, rhs.m_sValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sScheme).append (m_sValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Scheme", m_sScheme).append ("Value", m_sValue).getToString ();
  }

  @Nullable
  public static PDIdentifier of (@Nullable final IJsonObject aJson)
  {
    return aJson == null ? null : new PDIdentifier (aJson.getAsString ("scheme"), aJson.getAsString ("value"));
  }
}

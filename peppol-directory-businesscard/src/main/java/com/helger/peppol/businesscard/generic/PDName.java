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

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.json.IHasJson;
import com.helger.json.IJsonObject;
import com.helger.json.JsonObject;
import com.helger.text.locale.LocaleHelper;
import com.helger.text.locale.language.LanguageCache;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Generic name.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PDName implements IHasJson, Serializable, ICloneable <PDName>
{
  private String m_sName;
  private String m_sLanguageCode;

  public static boolean isValidLanguageCode (@Nullable final String s)
  {
    return s == null || (s.length () == 2 && LanguageCache.getInstance ().containsLanguage (s));
  }

  public PDName ()
  {}

  public PDName (@Nonnull @Nonempty final String sName)
  {
    this (sName, (String) null);
  }

  public PDName (@Nonnull @Nonempty final String sName, @Nullable final String sLanguageCode)
  {
    setName (sName);
    ValueEnforcer.isTrue (isValidLanguageCode (sLanguageCode),
                          () -> "'" + sLanguageCode + "' is invalid language code");
    m_sLanguageCode = LocaleHelper.getValidLanguageCode (sLanguageCode);
  }

  /**
   * @return The name. May neither be <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public final String getName ()
  {
    return m_sName;
  }

  /**
   * Set the name itself.
   *
   * @param sName
   *        The name to use. May neither be <code>null</code> nor empty.
   * @return this for chaining
   */
  @Nonnull
  public final PDName setName (@Nonnull @Nonempty final String sName)
  {
    ValueEnforcer.notEmpty (sName, "Name");
    m_sName = sName;
    return this;
  }

  /**
   * @return The language code. May be <code>null</code>.
   */
  @Nullable
  public final String getLanguageCode ()
  {
    return m_sLanguageCode;
  }

  public final boolean hasLanguageCode ()
  {
    return StringHelper.isNotEmpty (m_sLanguageCode);
  }

  public final boolean hasNoLanguageCode ()
  {
    return StringHelper.isEmpty (m_sLanguageCode);
  }

  /**
   * Set the language code to use.
   *
   * @param sLanguageCode
   *        The language code to use. May be <code>null</code>. If non-<code>null</code> it must be
   *        syntactically a valid lanaguage code.
   * @return this for chaining
   * @see #isValidLanguageCode(String)
   */
  @Nonnull
  public final PDName setLanguageCode (@Nullable final String sLanguageCode)
  {
    ValueEnforcer.isTrue (isValidLanguageCode (sLanguageCode),
                          () -> "'" + sLanguageCode + "' is invalid language code");
    m_sLanguageCode = LocaleHelper.getValidLanguageCode (sLanguageCode);
    return this;
  }

  /**
   * This method clones all values from <code>this</code> to the passed object. All data in the
   * parameter object is overwritten!
   *
   * @param ret
   *        The target object to clone to. May not be <code>null</code>.
   */
  public void cloneTo (@Nonnull final PDName ret)
  {
    ret.m_sName = m_sName;
    ret.m_sLanguageCode = m_sLanguageCode;
  }

  @Nonnull
  @ReturnsMutableCopy
  public PDName getClone ()
  {
    final PDName ret = new PDName ();
    cloneTo (ret);
    return ret;
  }

  @Nonnull
  public IMicroElement getAsMicroXML (@Nullable final String sNamespaceURI,
                                      @Nonnull @Nonempty final String sElementName)
  {
    final IMicroElement ret = new MicroElement (sNamespaceURI, sElementName);
    ret.setAttribute ("name", m_sName);
    ret.setAttribute ("language", m_sLanguageCode);
    return ret;
  }

  @Nonnull
  public IJsonObject getAsJson ()
  {
    final IJsonObject ret = new JsonObject ();
    ret.add ("name", m_sName);
    if (m_sLanguageCode != null)
      ret.add ("language", m_sLanguageCode);
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PDName rhs = (PDName) o;
    return m_sName.equals (rhs.m_sName) && EqualsHelper.equals (m_sLanguageCode, rhs.m_sLanguageCode);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sName).append (m_sLanguageCode).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Name", m_sName)
                                       .appendIfNotNull ("LanguageCode", m_sLanguageCode)
                                       .getToString ();
  }

  @Nullable
  public static PDName of (@Nullable final IJsonObject aJson)
  {
    return aJson == null ? null : new PDName (aJson.getAsString ("name"), aJson.getAsString ("language"));
  }
}

package com.helger.peppol.utils;

import java.security.Key;

import javax.annotation.Nullable;
import javax.xml.crypto.KeySelectorResult;

import com.helger.commons.string.ToStringGenerator;

/**
 * Special implements of {@link KeySelectorResult} with a constant, nullable
 * key.
 *
 * @author Philip Helger
 */
public final class ConstantKeySelectorResult implements KeySelectorResult
{
  private final Key m_aKey;

  public ConstantKeySelectorResult (@Nullable final Key aKey)
  {
    m_aKey = aKey;
  }

  @Nullable
  public Key getKey ()
  {
    return m_aKey;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("key", m_aKey).toString ();
  }
}

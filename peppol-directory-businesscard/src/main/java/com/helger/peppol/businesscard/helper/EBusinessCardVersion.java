package com.helger.peppol.businesscard.helper;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * Different business card syntax versions. In a separate file since 12.2.1
 *
 * @author Philip Helger
 */
public enum EBusinessCardVersion implements IHasID <String>
{
  V1 ("v1"),
  V2 ("v2"),
  V3 ("v3");

  static final EBusinessCardVersion LATEST = V3;

  private final String m_sID;

  EBusinessCardVersion (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static EBusinessCardVersion getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EBusinessCardVersion.class, sID);
  }
}

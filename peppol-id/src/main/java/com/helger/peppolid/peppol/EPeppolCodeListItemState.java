package com.helger.peppolid.peppol;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * Code list item state. Since code lists v8.0.
 *
 * @author Philip Helger
 * @since 8.7.0
 */
public enum EPeppolCodeListItemState implements IHasID <String>
{
  ACTIVE ("act"),
  DEPRECATED ("dep"),
  REMOVED ("rem");

  private final String m_sID;

  EPeppolCodeListItemState (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public boolean isActive ()
  {
    return this == ACTIVE;
  }

  public boolean isDeprecated ()
  {
    return this == DEPRECATED;
  }

  public boolean isRemoved ()
  {
    return this == REMOVED;
  }

  @Nullable
  public static EPeppolCodeListItemState getFromIDOrNull (@Nullable final String s)
  {
    return EnumHelper.getFromIDOrNull (EPeppolCodeListItemState.class, s);
  }
}

package com.helger.peppol.mlr;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.state.ISuccessIndicator;

public enum EPeppolMLRState implements IHasID <String>, ISuccessIndicator
{
  ACCEPTANCE ("AP"),
  ACKNOWLEDGING ("AB"),
  REJECTION ("RE");

  private final String m_sID;

  EPeppolMLRState (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public boolean isSuccess ()
  {
    return this == ACCEPTANCE || this == ACKNOWLEDGING;
  }

  public boolean isFailure ()
  {
    return this == REJECTION;
  }

  @Nullable
  public static EPeppolMLRState getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLRState.class, sID);
  }
}

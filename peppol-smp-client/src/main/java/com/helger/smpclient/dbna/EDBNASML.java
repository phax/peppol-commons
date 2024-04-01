package com.helger.smpclient.dbna;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;

/**
 * The list of supported DBNA SML zones
 *
 * @author Philip Helger
 * @since 9.3.4
 */
public enum EDBNASML
{
  PRODUCTION ("sml.dbnalliance.net."),
  TEST ("sml.dbnalliance.com."),
  PILOT ("sml.digitalbusinessnetworks.info.");

  private final String m_sZoneName;

  EDBNASML (@Nonnull @Nonempty final String sZoneName)
  {
    m_sZoneName = sZoneName;
  }

  @Nonnull
  @Nonempty
  public String getZoneName ()
  {
    return m_sZoneName;
  }
}

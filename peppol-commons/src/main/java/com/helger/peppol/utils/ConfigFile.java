/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.utils;

import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.state.EChange;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.system.SystemProperties;
import com.helger.commons.traits.IConvertibleByKeyTrait;
import com.helger.commons.typeconvert.TypeConverterException;
import com.helger.settings.ISettings;
import com.helger.settings.Settings;
import com.helger.settings.exchange.ISettingsPersistence;
import com.helger.settings.exchange.properties.SettingsPersistenceProperties;

/**
 * Used for accessing configuration files based on properties. By default first
 * the private (not checked-in) version of the config file called
 * <code>private-config.properties</code> is accessed. If no such file is
 * present, the default config file (which is also in the SCM) is accessed by
 * the name<code>config.properties</code>.<br>
 * Additionally you can create a new instance with a custom file path.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public class ConfigFile implements IConvertibleByKeyTrait <String>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ConfigFile.class);

  private final IReadableResource m_aReadResource;
  private final ISettings m_aSettings;

  /**
   * Constructor for the settings that were read
   *
   * @param aRes
   *        The resource that was read. May be <code>null</code>.
   * @param aSettings
   *        The settings that were read. May be <code>null</code>.
   */
  protected ConfigFile (@Nullable final IReadableResource aRes, @Nullable final ISettings aSettings)
  {
    m_aReadResource = aRes;
    m_aSettings = aSettings;
  }

  /**
   * @return <code>true</code> if reading succeeded, <code>false</code> if
   *         reading failed (warning was already logged)
   */
  public boolean isRead ()
  {
    return m_aSettings != null;
  }

  /**
   * @return The resource from which the config file was read. May be
   *         <code>null</code> if reading failed.
   */
  @Nullable
  public IReadableResource getReadResource ()
  {
    return m_aReadResource;
  }

  /**
   * @return The underlying {@link ISettings} object. May be <code>null</code>
   *         if reading failed.
   */
  @Nullable
  public ISettings getSettings ()
  {
    return m_aSettings;
  }

  @Nullable
  public Object getValue (@Nullable final String sFieldName)
  {
    return m_aSettings == null ? null : m_aSettings.getValue (sFieldName);
  }

  // FIXME part of ph-commons >= 8.0.1
  @Nullable
  public char [] getAsCharArray (@Nullable final String aKey) throws TypeConverterException
  {
    return getConvertedValue (aKey, char [].class);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <String, Object> getAllEntries ()
  {
    if (m_aSettings == null)
      return new CommonsHashMap <> ();
    return m_aSettings.getAllEntries ();
  }

  /**
   * This is a utility method, that applies all Java network/proxy system
   * properties which are present in the configuration file. It does it only
   * when the configuration file was read correctly.
   *
   * @see PeppolTechnicalSetup#getAllJavaNetSystemProperties()
   */
  public void applyAllNetworkSystemProperties ()
  {
    if (isRead ())
      for (final String sProperty : PeppolTechnicalSetup.getAllJavaNetSystemProperties ())
      {
        final String sConfigFileValue = getAsString (sProperty);
        if (sConfigFileValue != null)
        {
          SystemProperties.setPropertyValue (sProperty, sConfigFileValue);
          s_aLogger.info ("Set Java network/proxy system property: " + sProperty + "=" + sConfigFileValue);
        }
      }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ReadResource", m_aReadResource)
                                       .append ("Settings", m_aSettings)
                                       .toString ();
  }

  @Nonnull
  public static ConfigFile create (@Nonnull @Nonempty final String sConfigPath)
  {
    return create (new CommonsArrayList <> (sConfigPath));
  }

  @Nonnull
  public static ConfigFile create (@Nonnull @Nonempty final String... aConfigPaths)
  {
    return create (new CommonsArrayList <> (aConfigPaths));
  }

  private static final class TrimmedValueSettings extends Settings
  {
    public TrimmedValueSettings (@Nonnull @Nonempty final String sName)
    {
      super (sName);
    }

    @Override
    @Nonnull
    public EChange setValue (@Nonnull @Nonempty final String sFieldName, @Nullable final Object aNewValue)
    {
      return super.setValue (sFieldName, StringHelper.trim ((String) aNewValue));
    }
  }

  @Nonnull
  public static ConfigFile create (@Nonnull @Nonempty final Iterable <String> aConfigPaths)
  {
    ValueEnforcer.notEmptyNoNullValue (aConfigPaths, "ConfigPaths");

    final ISettingsPersistence aSPP = new SettingsPersistenceProperties (x -> new TrimmedValueSettings (x));
    IReadableResource aRes = null;
    ISettings aSettings = null;
    for (final String sConfigPath : aConfigPaths)
    {
      // Try to get the input stream for the passed property file name
      aRes = new ClassPathResource (sConfigPath);
      InputStream aIS = aRes.getInputStream ();
      if (aIS == null)
      {
        // Fallback to file system - maybe this helps...
        aRes = new FileSystemResource (sConfigPath);
        aIS = aRes.getInputStream ();
      }
      if (aIS != null)
      {
        aSettings = aSPP.readSettings (aIS);
        if (aSettings != null)
          break;
      }
    }

    if (aSettings == null)
      s_aLogger.warn ("Failed to resolve config file paths: " + aConfigPaths);

    return new ConfigFile (aSettings != null ? aRes : null, aSettings);
  }
}

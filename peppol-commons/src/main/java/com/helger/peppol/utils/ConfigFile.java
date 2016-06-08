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

import java.io.IOException;
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
import com.helger.commons.collection.ext.ICommonsOrderedMap;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.lang.NonBlockingProperties;
import com.helger.commons.state.ESuccess;
import com.helger.commons.string.StringParser;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.system.SystemProperties;

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
public class ConfigFile
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ConfigFile.class);

  private IReadableResource m_aReadResource;
  private final NonBlockingProperties m_aProps = new NonBlockingProperties ();

  /**
   * Constructor with a single file path to read.
   *
   * @param sConfigPath
   *        The path to the config file to be read. Must be classpath-relative.
   */
  public ConfigFile (@Nonnull @Nonempty final String sConfigPath)
  {
    this (new CommonsArrayList <> (sConfigPath));
  }

  public ConfigFile (@Nonnull @Nonempty final String... aConfigPaths)
  {
    this (new CommonsArrayList <> (aConfigPaths));
  }

  /**
   * Constructor for explicitly specifying a file path to read.
   *
   * @param aConfigPaths
   *        The array of paths to the config files to be read. Must be
   *        classpath-relative. The first file that can be read will be used and
   *        the others will be ignored.
   */
  public ConfigFile (@Nonnull @Nonempty final Iterable <String> aConfigPaths)
  {
    ValueEnforcer.notEmptyNoNullValue (aConfigPaths, "ConfigPaths");

    boolean bRead = false;
    for (final String sConfigPath : aConfigPaths)
      if (_readConfigFile (sConfigPath).isSuccess ())
      {
        bRead = true;
        break;
      }

    if (!bRead)
    {
      // No config file found at all
      s_aLogger.warn ("Failed to resolve config file paths: " + aConfigPaths);
    }
  }

  @Nonnull
  private ESuccess _readConfigFile (@Nonnull final String sPath)
  {
    // Try to get the input stream for the passed property file name
    IReadableResource aRes = new ClassPathResource (sPath);
    InputStream aIS = aRes.getInputStream ();
    if (aIS == null)
    {
      // Fallback to file system - maybe this helps...
      aRes = new FileSystemResource (sPath);
      aIS = aRes.getInputStream ();
    }
    if (aIS != null)
    {
      try
      {
        // Does not close the input stream!
        m_aProps.load (aIS);
        if (s_aLogger.isDebugEnabled ())
          s_aLogger.debug ("Loaded configuration from '" + sPath + "': " + m_aProps.keySet ());
        m_aReadResource = aRes;
        return ESuccess.SUCCESS;
      }
      catch (final IOException ex)
      {
        s_aLogger.error ("Failed to read config file '" + sPath + "'", ex);
      }
      finally
      {
        // Manually close the input stream!
        StreamHelper.close (aIS);
      }
    }
    return ESuccess.FAILURE;
  }

  /**
   * @return <code>true</code> if reading succeeded, <code>false</code> if
   *         reading failed (warning was already logged)
   */
  public boolean isRead ()
  {
    return m_aReadResource != null;
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
   * Get the string from the configuration files
   *
   * @param sKey
   *        The key to search
   * @return <code>null</code> if no such value is in the configuration file.
   */
  @Nullable
  public final String getString (@Nonnull final String sKey)
  {
    return getString (sKey, null);
  }

  /**
   * Get the string from the configuration files
   *
   * @param sKey
   *        The key to search
   * @param sDefault
   *        The default value to be returned if the value was not found. May be
   *        <code>null</code>.
   * @return the passed default value if no such value is in the configuration
   *         file.
   */
  @Nullable
  public final String getString (@Nonnull final String sKey, @Nullable final String sDefault)
  {
    final String sValue = m_aProps.getProperty (sKey);
    return sValue != null ? sValue.trim () : sDefault;
  }

  @Nullable
  public final char [] getCharArray (@Nonnull final String sKey)
  {
    return getCharArray (sKey, null);
  }

  @Nullable
  public final char [] getCharArray (@Nonnull final String sKey, @Nullable final char [] aDefault)
  {
    final String ret = getString (sKey, null);
    return ret == null ? aDefault : ret.toCharArray ();
  }

  public final boolean getBoolean (@Nonnull final String sKey, final boolean bDefault)
  {
    return StringParser.parseBool (getString (sKey), bDefault);
  }

  public final int getInt (@Nonnull final String sKey, final int nDefault)
  {
    return StringParser.parseInt (getString (sKey), nDefault);
  }

  public final long getLong (@Nonnull final String sKey, final long nDefault)
  {
    return StringParser.parseLong (getString (sKey), nDefault);
  }

  /**
   * @return A set with all keys contained in the configuration file
   */
  @Nonnull
  @ReturnsMutableCopy
  public final ICommonsSet <String> getAllKeys ()
  {
    // Convert from Set<Object> to Set<String>
    return m_aProps.copyOfKeySet ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public final ICommonsOrderedMap <String, String> getAllEntries ()
  {
    // Convert from Map<Object,Object> to Map<String, String>
    return m_aProps.getClone ();
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
        final String sConfigFileValue = getString (sProperty);
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
    return new ToStringGenerator (this).append ("readResource", m_aReadResource).append ("props", m_aProps).toString ();
  }
}

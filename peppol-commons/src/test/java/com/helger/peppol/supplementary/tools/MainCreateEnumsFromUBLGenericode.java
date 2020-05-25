/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.io.file.FileSystemRecursiveIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.genericode.Genericode10CodeListMarshaller;
import com.helger.genericode.Genericode10Helper;
import com.helger.genericode.v10.CodeListDocument;
import com.helger.genericode.v10.Column;
import com.helger.genericode.v10.Row;
import com.helger.genericode.v10.SimpleCodeList;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JCodeModelException;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JEnumConstant;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JOp;
import com.helger.jcodemodel.JVar;
import com.helger.jcodemodel.writer.JCMWriter;

/**
 * Convert <code>src/main/resources/codelists/ubl</code> files to Java enums.
 *
 * @author Philip Helger
 */
public class MainCreateEnumsFromUBLGenericode
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateEnumsFromUBLGenericode.class);
  private static final String COLID_NAME = "name";
  private static final String COLID_CODE = "code";
  private static final JCodeModel s_aCodeModel = new JCodeModel ();

  private static void _createGenericode10 (final File aFile, final CodeListDocument aCodeList10) throws JCodeModelException
  {
    final SimpleCodeList aSimpleCodeList = aCodeList10.getSimpleCodeList ();
    if (aSimpleCodeList == null)
    {
      LOGGER.info ("  does not contain a SimpleCodeList!");
      return;
    }
    final Column aColCode = Genericode10Helper.getColumnOfID (aCodeList10.getColumnSet (), COLID_CODE);
    if (aColCode == null)
    {
      LOGGER.info ("  No '" + COLID_CODE + "' column found");
      return;
    }
    if (!Genericode10Helper.isKeyColumn (aCodeList10.getColumnSet (), COLID_CODE))
    {
      LOGGER.info ("  Column '" + COLID_CODE + "' is not a key");
      return;
    }
    final Column aColName = Genericode10Helper.getColumnOfID (aCodeList10.getColumnSet (), COLID_NAME);
    if (aColName == null)
    {
      LOGGER.info ("  No '" + COLID_NAME + "' column found");
      return;
    }

    final JDefinedClass jEnum = s_aCodeModel._package ("com.helger.peppol.codelist")
                                            ._enum ("E" +
                                                    RegExHelper.getAsIdentifier (aCodeList10.getIdentification ()
                                                                                            .getShortName ()
                                                                                            .getValue ()))
                                            ._implements (s_aCodeModel.ref (IHasID.class).narrow (String.class))
                                            ._implements (IHasDisplayName.class);
    jEnum.javadoc ().add ("This file is generated from Genericode file " + aFile.getName () + ". Do NOT edit!");

    for (final Row aRow : aCodeList10.getSimpleCodeList ().getRow ())
    {
      final String sCode = Genericode10Helper.getRowValue (aRow, COLID_CODE);
      final String sName = Genericode10Helper.getRowValue (aRow, COLID_NAME);

      String sIdentifier = RegExHelper.getAsIdentifier (sName.toUpperCase (Locale.US));
      sIdentifier = StringHelper.replaceAllRepeatedly (sIdentifier, "__", "_");
      final JEnumConstant jEnumConst = jEnum.enumConstant (sIdentifier);
      jEnumConst.arg (JExpr.lit (sCode));
      jEnumConst.arg (JExpr.lit (sName));
    }

    // fields
    final JFieldVar fID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
    final JFieldVar fDisplayName = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sDisplayName");

    // Constructor
    final JMethod jCtor = jEnum.constructor (JMod.PRIVATE);
    JVar jID = jCtor.param (JMod.FINAL, String.class, "sID");
    jID.annotate (Nonnull.class);
    jID.annotate (Nonempty.class);
    final JVar jDisplayName = jCtor.param (JMod.FINAL, String.class, "sDisplayName");
    jDisplayName.annotate (Nonnull.class);
    jCtor.body ().assign (fID, jID).assign (fDisplayName, jDisplayName);

    // public String getID ()
    JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getID");
    m.annotate (Nonnull.class);
    m.annotate (Nonempty.class);
    m.body ()._return (fID);

    // public String getDisplayName ()
    m = jEnum.method (JMod.PUBLIC, String.class, "getDisplayName");
    m.annotate (Nonnull.class);
    m.body ()._return (fDisplayName);

    // public static E... getFromIDOrNull (@Nullable String sID)
    m = jEnum.method (JMod.PUBLIC | JMod.STATIC, jEnum, "getFromIDOrNull");
    m.annotate (Nullable.class);
    jID = m.param (JMod.FINAL, String.class, "sID");
    jID.annotate (Nullable.class);
    m.body ()._return (s_aCodeModel.ref (EnumHelper.class).staticInvoke ("getFromIDOrNull").arg (JExpr.dotClass (jEnum)).arg (jID));

    // public static String getDisplayNameFromIDOrNull (@Nullable String sID)
    m = jEnum.method (JMod.PUBLIC | JMod.STATIC, String.class, "getDisplayNameFromIDOrNull");
    m.annotate (Nullable.class);
    jID = m.param (JMod.FINAL, String.class, "sID");
    jID.annotate (Nullable.class);
    final JVar jValue = m.body ().decl (JMod.FINAL, jEnum, "eValue", jEnum.staticInvoke ("getFromIDOrNull").arg (jID));
    m.body ()._return (JOp.cond (JOp.eq (jValue, JExpr._null ()), JExpr._null (), jValue.invoke ("getDisplayName")));
  }

  public static void main (final String [] args) throws JCodeModelException, IOException
  {
    for (final File aFile : new FileSystemRecursiveIterator ("src/main/resources/codelists/ubl").withFilter (IFileFilter.filenameEndsWith (".gc")))
    {
      LOGGER.info (aFile.getName ());
      final CodeListDocument aCodeList10 = new Genericode10CodeListMarshaller ().read (new FileSystemResource (aFile));
      if (aCodeList10 != null)
        _createGenericode10 (aFile, aCodeList10);
    }
    new JCMWriter (s_aCodeModel).build (new File ("src/main/java"));
  }
}

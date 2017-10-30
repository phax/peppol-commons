/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JEnumConstant;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JOp;
import com.helger.jcodemodel.JVar;

public class MainCreateEnumsFromGenericode
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainCreateEnumsFromGenericode.class);
  private static final String COLID_NAME = "name";
  private static final String COLID_CODE = "code";
  private static final JCodeModel s_aCodeModel = new JCodeModel ();

  private static void _createGenericode10 (final File aFile,
                                           final CodeListDocument aCodeList10) throws JClassAlreadyExistsException
  {
    final SimpleCodeList aSimpleCodeList = aCodeList10.getSimpleCodeList ();
    if (aSimpleCodeList == null)
    {
      s_aLogger.info ("  does not contain a SimpleCodeList!");
      return;
    }
    final Column aColCode = Genericode10Helper.getColumnOfID (aCodeList10.getColumnSet (), COLID_CODE);
    if (aColCode == null)
    {
      s_aLogger.info ("  No '" + COLID_CODE + "' column found");
      return;
    }
    if (!Genericode10Helper.isKeyColumn (aCodeList10.getColumnSet (), COLID_CODE))
    {
      s_aLogger.info ("  Column '" + COLID_CODE + "' is not a key");
      return;
    }
    final Column aColName = Genericode10Helper.getColumnOfID (aCodeList10.getColumnSet (), COLID_NAME);
    if (aColName == null)
    {
      s_aLogger.info ("  No '" + COLID_NAME + "' column found");
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
    m.body ()
     ._return (s_aCodeModel.ref (EnumHelper.class)
                           .staticInvoke ("getFromIDOrNull")
                           .arg (JExpr.dotclass (jEnum))
                           .arg (jID));

    // public static String getDisplayNameFromIDOrNull (@Nullable String sID)
    m = jEnum.method (JMod.PUBLIC | JMod.STATIC, String.class, "getDisplayNameFromIDOrNull");
    m.annotate (Nullable.class);
    jID = m.param (JMod.FINAL, String.class, "sID");
    jID.annotate (Nullable.class);
    final JVar jValue = m.body ().decl (JMod.FINAL, jEnum, "eValue", jEnum.staticInvoke ("getFromIDOrNull").arg (jID));
    m.body ()._return (JOp.cond (JOp.eq (jValue, JExpr._null ()), JExpr._null (), jValue.invoke ("getDisplayName")));
  }

  public static void main (final String [] args) throws JClassAlreadyExistsException, IOException
  {
    for (final File aFile : new FileSystemRecursiveIterator ("src/main/resources/codelists/ubl").withFilter (IFileFilter.filenameEndsWith (".gc")))
    {
      s_aLogger.info (aFile.getName ());
      final CodeListDocument aCodeList10 = new Genericode10CodeListMarshaller ().read (new FileSystemResource (aFile));
      if (aCodeList10 != null)
        _createGenericode10 (aFile, aCodeList10);
    }
    s_aCodeModel.build (new File ("src/main/java"));
  }
}

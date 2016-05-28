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
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.charset.CCharset;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.CommonsHashSet;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.io.file.SimpleFileIO;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.microdom.IMicroDocument;
import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.MicroDocument;
import com.helger.commons.microdom.serialize.MicroWriter;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.version.Version;
import com.helger.commons.xml.serialize.write.XMLWriter;
import com.helger.commons.xml.serialize.write.XMLWriterSettings;
import com.helger.genericode.Genericode10CodeListMarshaller;
import com.helger.genericode.Genericode10Helper;
import com.helger.genericode.excel.ExcelReadOptions;
import com.helger.genericode.excel.ExcelSheetToCodeList10;
import com.helger.genericode.v10.CodeListDocument;
import com.helger.genericode.v10.Row;
import com.helger.genericode.v10.UseType;
import com.helger.jcodemodel.JArray;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JDocComment;
import com.helger.jcodemodel.JEnumConstant;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JForEach;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;
import com.helger.jcodemodel.writer.FileCodeWriter;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.peppol.CPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.IPeppolPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.part.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.part.OpenPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.part.PeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.issuingagency.IIdentifierIssuingAgency;
import com.helger.peppol.identifier.peppol.process.IPeppolPredefinedProcessIdentifier;

/**
 * Utility class to create the Genericode files from the Excel code list. Also
 * creates Java source files with the predefined identifiers.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class MainCreateCodelistsFilesFromExcel
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainCreateCodelistsFilesFromExcel.class);
  private static final Version CODELIST_VERSION = new Version (1, 2, 2);
  private static final String EXCEL_FILE = "src/main/codelists/PEPPOL Code Lists 1.2.2-unofficial.xls";
  private static final String SHEET_PARTICIPANT = "Participant";
  private static final String SHEET_DOCUMENT = "Document";
  private static final String SHEET_PROCESS = "Process";
  private static final String RESULT_DIRECTORY = "src/main/resources/codelists/";
  private static final String RESULT_PACKAGE_PREFIX = "com.helger.peppol.";
  private static final JCodeModel s_aCodeModel = new JCodeModel ();
  private static JDefinedClass s_jEnumPredefinedDoc;

  private static void _writeGenericodeFile (@Nonnull final CodeListDocument aCodeList, @Nonnull final String sFilename)
  {
    final Document aDoc = new Genericode10CodeListMarshaller ().getAsDocument (aCodeList);
    if (aDoc == null)
      throw new IllegalStateException ("Failed to serialize code list");
    final OutputStream aFOS = FileHelper.getOutputStream (sFilename);
    if (XMLWriter.writeToStream (aDoc, aFOS, XMLWriterSettings.DEFAULT_XML_SETTINGS).isFailure ())
      throw new IllegalStateException ("Failed to write file " + sFilename);
    s_aLogger.info ("Wrote Genericode file " + sFilename);
  }

  private static void _writeValidationPartyIdFile (final Sheet aParticipantSheet) throws URISyntaxException
  {
    // Read excel file
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "code", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "name", UseType.OPTIONAL, "string", false);

    // Convert to GeneriCode
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aParticipantSheet,
                                                                                       aReadOptions,
                                                                                       "Scheme Agency",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("PEPPOL"),
                                                                                       new URI ("PEPPOL-" +
                                                                                                CODELIST_VERSION.getAsString ()),
                                                                                       new URI ("PartyID.gc"));
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PartyID.gc");
  }

  private static void _emitIdentifierIssuingAgency (final Sheet aParticipantSheet) throws URISyntaxException
  {
    // Read excel file
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "schemeid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (1, "iso6523", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "schemeagency", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (3, "deprecated", UseType.REQUIRED, "boolean", false);
    aReadOptions.addColumn (4, "since", UseType.REQUIRED, "string", false);
    aReadOptions.addColumn (6, "structure", UseType.OPTIONAL, "string", false);

    // Convert to GeneriCode
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aParticipantSheet,
                                                                                       aReadOptions,
                                                                                       "PeppolIdentifierIssuingAgencies",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("urn:peppol.eu:names:identifier:issuingagencies"),
                                                                                       new URI ("urn:peppol.eu:names:identifier:issuingagencies-1.0"),
                                                                                       null);
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PeppolIdentifierIssuingAgencies.gc");

    _writeValidationPartyIdFile (aParticipantSheet);

    // Save data also as XML
    final IMicroDocument aDoc = new MicroDocument ();
    aDoc.appendComment ("This file was automatically generated. Do NOT edit!");
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sCode = Genericode10Helper.getRowValue (aRow, "schemeid");
      final String sAgency = Genericode10Helper.getRowValue (aRow, "schemeagency");
      final String sISO6523 = Genericode10Helper.getRowValue (aRow, "iso6523");
      final String sDeprecated = Genericode10Helper.getRowValue (aRow, "deprecated");
      final boolean bDeprecated = StringParser.parseBool (sDeprecated, false);
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");
      final String sStructure = Genericode10Helper.getRowValue (aRow, "structure");

      final IMicroElement eAgency = eRoot.appendElement ("issuingAgency");
      eAgency.setAttribute ("schemeid", sCode);
      eAgency.setAttribute ("agencyname", sAgency);
      eAgency.setAttribute ("iso6523", sISO6523);
      if (bDeprecated)
        eAgency.setAttribute ("deprecated", Boolean.TRUE.toString ());
      eAgency.setAttribute ("since", sSince);

      if (StringHelper.hasText (sStructure))
      {
        final IMicroElement eStructure = eAgency.appendElement ("structure");
        eStructure.appendText (sStructure);
      }
    }
    final String sXML = MicroWriter.getXMLString (aDoc);
    SimpleFileIO.writeFile (new File (RESULT_DIRECTORY + "PeppolIdentifierIssuingAgencies.xml"),
                            sXML,
                            CCharset.CHARSET_UTF_8_OBJ);

    // Create Java source
    try
    {
      final JDefinedClass jEnum = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "identifier.issuingagency")
                                              ._enum ("EPredefinedIdentifierIssuingAgency")
                                              ._implements (IIdentifierIssuingAgency.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add ("This file is generated. Do NOT edit!");

      // enum constants
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sSchemeID = Genericode10Helper.getRowValue (aRow, "schemeid");
        final String sAgency = Genericode10Helper.getRowValue (aRow, "schemeagency");
        final String sISO6523 = Genericode10Helper.getRowValue (aRow, "iso6523");
        final String sDeprecated = Genericode10Helper.getRowValue (aRow, "deprecated");
        final boolean bDeprecated = StringParser.parseBool (sDeprecated, false);
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");

        if (StringHelper.hasNoText (sSchemeID))
          throw new IllegalArgumentException ("schemeID");
        if (sSchemeID.indexOf (' ') >= 0)
          throw new IllegalArgumentException ("Scheme IDs are not supposed to contain spaces!");
        if (StringHelper.hasNoText (sISO6523))
          throw new IllegalArgumentException ("ISO6523Code");
        if (!RegExHelper.stringMatchesPattern ("[0-9]{4}", sISO6523))
          throw new IllegalArgumentException ("The ISO 6523 code '" + sISO6523 + "' does not consist of 4 numbers");

        final JEnumConstant jEnumConst = jEnum.enumConstant (RegExHelper.getAsIdentifier (sSchemeID));
        jEnumConst.arg (JExpr.lit (sSchemeID));
        jEnumConst.arg (sAgency == null ? JExpr._null () : JExpr.lit (sAgency));
        jEnumConst.arg (JExpr.lit (sISO6523));
        jEnumConst.arg (bDeprecated ? JExpr.TRUE : JExpr.FALSE);
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));

        jEnumConst.javadoc ()
                  .add ("Prefix <code>" + sISO6523 + "</code>, scheme ID <code>" + sSchemeID + "</code><br>\n");
        if (bDeprecated)
          jEnumConst.javadoc ()
                    .add ("<b>This item is deprecated and should not be used to issue new identifiers!</b><br>\n");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sSince);
      }

      // fields
      final JFieldVar fSchemeID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sSchemeID");
      final JFieldVar fSchemeAgency = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sSchemeAgency");
      final JFieldVar fISO6523 = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sISO6523");
      final JFieldVar fDeprecated = jEnum.field (JMod.PRIVATE | JMod.FINAL, boolean.class, "m_bDeprecated");
      final JFieldVar fSince = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aSince");

      // Constructor
      final JMethod jCtor = jEnum.constructor (JMod.PRIVATE);
      final JVar jSchemeID = jCtor.param (JMod.FINAL, String.class, "sSchemeID");
      jSchemeID.annotate (Nonnull.class);
      jSchemeID.annotate (Nonempty.class);
      final JVar jSchemeAgency = jCtor.param (JMod.FINAL, String.class, "sSchemeAgency");
      jSchemeAgency.annotate (Nullable.class);
      final JVar jISO6523 = jCtor.param (JMod.FINAL, String.class, "sISO6523");
      jISO6523.annotate (Nonnull.class);
      jISO6523.annotate (Nonempty.class);
      final JVar jDeprecated = jCtor.param (JMod.FINAL, boolean.class, "bDeprecated");
      final JVar jSince = jCtor.param (JMod.FINAL, Version.class, "aSince");
      jSince.annotate (Nonnull.class);
      jCtor.body ()
           .assign (fSchemeID, jSchemeID)
           .assign (fSchemeAgency, jSchemeAgency)
           .assign (fISO6523, jISO6523)
           .assign (fDeprecated, jDeprecated)
           .assign (fSince, jSince);

      // public String getSchemeID ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getSchemeID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fSchemeID);

      // public String getSchemeAgency ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getSchemeAgency");
      m.annotate (Nullable.class);
      m.body ()._return (fSchemeAgency);

      // public String getISO6523Code ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getISO6523Code");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fISO6523);

      // public String createIdentifierValue (String)
      final JMethod mCreateIdentifierValue = jEnum.method (JMod.PUBLIC, String.class, "createIdentifierValue");
      mCreateIdentifierValue.annotate (Nonnull.class);
      mCreateIdentifierValue.annotate (Nonempty.class);
      JVar jValue = mCreateIdentifierValue.param (JMod.FINAL, String.class, "sIdentifier");
      jValue.annotate (Nonnull.class);
      jValue.annotate (Nonempty.class);
      mCreateIdentifierValue.body ()._return (fISO6523.plus (JExpr.lit (":")).plus (jValue));

      // public SimpleParticipantIdentifier createIdentifierValue (String)
      m = jEnum.method (JMod.PUBLIC, SimpleParticipantIdentifier.class, "createParticipantIdentifier");
      m.annotate (Nonnull.class);
      jValue = m.param (JMod.FINAL, String.class, "sIdentifier");
      jValue.annotate (Nonnull.class);
      jValue.annotate (Nonempty.class);
      m.body ()
       ._return (s_aCodeModel.ref (SimpleParticipantIdentifier.class)
                             .staticInvoke ("createWithDefaultScheme")
                             .arg (JExpr.invoke (mCreateIdentifierValue).arg (jValue)));

      // public boolean isDeprecated ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDeprecated");
      m.body ()._return (fDeprecated);

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.body ()._return (fSince);
    }
    catch (final Exception ex)
    {
      s_aLogger.warn ("Failed to create source", ex);
    }
  }

  private static void _emitDocumentIdentifiers (final Sheet aDocumentSheet) throws URISyntaxException
  {
    // Create GeneriCode file
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "name", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (1, "docid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "since", UseType.REQUIRED, "string", false);
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aDocumentSheet,
                                                                                       aReadOptions,
                                                                                       "PeppolDocumentTypeIdentifier",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("urn:peppol.eu:names:identifier:document"),
                                                                                       new URI ("urn:peppol.eu:names:identifier:document-1.0"),
                                                                                       null);
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PeppolDocumentTypeIdentifier.gc");

    // Save as XML
    final IMicroDocument aDoc = new MicroDocument ();
    aDoc.appendComment ("This file was automatically generated. Do NOT edit!");
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sDocID = Genericode10Helper.getRowValue (aRow, "docid");
      final String sName = Genericode10Helper.getRowValue (aRow, "name");
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");

      final IMicroElement eAgency = eRoot.appendElement ("document");
      eAgency.setAttribute ("id", sDocID);
      eAgency.setAttribute ("name", sName);
      eAgency.setAttribute ("since", sSince);
    }
    final String sXML = MicroWriter.getXMLString (aDoc);
    SimpleFileIO.writeFile (new File (RESULT_DIRECTORY + "PeppolDocumentTypeIdentifier.xml"),
                            sXML,
                            CCharset.CHARSET_UTF_8_OBJ);

    // Create Java source
    try
    {
      s_jEnumPredefinedDoc = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "identifier.doctype")
                                         ._enum ("EPredefinedDocumentTypeIdentifier")
                                         ._implements (IPeppolPredefinedDocumentTypeIdentifier.class);
      s_jEnumPredefinedDoc.annotate (CodingStyleguideUnaware.class);
      s_jEnumPredefinedDoc.javadoc ().add ("This file is generated. Do NOT edit!");

      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();

      // Add all enum constants
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sDocID = Genericode10Helper.getRowValue (aRow, "docid");
        final String sName = Genericode10Helper.getRowValue (aRow, "name");
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");

        // Split ID in it's pieces
        IPeppolDocumentTypeIdentifierParts aDocIDParts;
        Class <?> aDocTypeIDPartsClass;
        try
        {
          aDocIDParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocID);
          aDocTypeIDPartsClass = PeppolDocumentTypeIdentifierParts.class;
        }
        catch (final IllegalArgumentException ex)
        {
          aDocIDParts = OpenPeppolDocumentTypeIdentifierParts.extractFromString (sDocID);
          aDocTypeIDPartsClass = OpenPeppolDocumentTypeIdentifierParts.class;
        }

        // Assemble extensions
        final JInvocation jExtensions = s_aCodeModel.ref (CollectionHelper.class).staticInvoke ("newList");
        for (final String sExtensionID : aDocIDParts.getExtensionIDs ())
          jExtensions.arg (sExtensionID);

        final String sEnumConstName = RegExHelper.getAsIdentifier (sDocID);
        final JEnumConstant jEnumConst = s_jEnumPredefinedDoc.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr._new (s_aCodeModel.ref (aDocTypeIDPartsClass))
                             .arg (aDocIDParts.getRootNS ())
                             .arg (aDocIDParts.getLocalName ())
                             .arg (aDocIDParts.getTransactionID ())
                             .arg (jExtensions)
                             .arg (aDocIDParts.getVersion ()));
        jEnumConst.arg (JExpr.lit (sName));
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));
        jEnumConst.javadoc ().add ("<code>" + sDocID + "</code>\n");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sSince);

        // Also create a shortcut for more readable names
        final String sShortcutName = CodeGenerationHelper.createShortcutDocumentTypeIDName (aDocIDParts);
        if (!aAllShortcutNames.add (sShortcutName))
          throw new IllegalStateException ("The shortcut name " +
                                           sShortcutName +
                                           " is already used for " +
                                           aDocIDParts.toString () +
                                           ". Please update the algorithm!");
        final JFieldVar aShortcut = s_jEnumPredefinedDoc.field (JMod.PUBLIC |
                                                                JMod.STATIC |
                                                                JMod.FINAL,
                                                                s_jEnumPredefinedDoc,
                                                                sShortcutName,
                                                                jEnumConst);
        aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
      }

      // fields
      final JFieldVar fParts = s_jEnumPredefinedDoc.field (JMod.PRIVATE |
                                                           JMod.FINAL,
                                                           IPeppolDocumentTypeIdentifierParts.class,
                                                           "m_aParts");
      final JFieldVar fID = s_jEnumPredefinedDoc.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
      final JFieldVar fCommonName = s_jEnumPredefinedDoc.field (JMod.PRIVATE |
                                                                JMod.FINAL,
                                                                String.class,
                                                                "m_sCommonName");
      final JFieldVar fSince = s_jEnumPredefinedDoc.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aSince");

      // Constructor
      final JMethod jCtor = s_jEnumPredefinedDoc.constructor (JMod.PRIVATE);
      final JVar jParts = jCtor.param (JMod.FINAL, IPeppolDocumentTypeIdentifierParts.class, "aParts");
      jParts.annotate (Nonnull.class);
      final JVar jCommonName = jCtor.param (JMod.FINAL, String.class, "sCommonName");
      jCommonName.annotate (Nonnull.class);
      jCommonName.annotate (Nonempty.class);
      final JVar jSince = jCtor.param (JMod.FINAL, Version.class, "aSince");
      jSince.annotate (Nonnull.class);
      jCtor.body ()
           .assign (fParts, jParts)
           .assign (fCommonName, jCommonName)
           .assign (fID, fParts.invoke ("getAsDocumentTypeIdentifierValue"))
           .assign (fSince, jSince);

      // public String getScheme ()
      JMethod m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (s_aCodeModel.ref (CPeppolIdentifier.class).staticRef ("DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME"));

      // public String getValue ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public String getRootNS ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getRootNS");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getLocalName ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getLocalName");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getSubTypeIdentifier ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getSubTypeIdentifier");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getTransactionID ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getTransactionID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public List<String> getExtensionIDs ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC,
                                       s_aCodeModel.ref (ICommonsList.class).narrow (String.class),
                                       "getExtensionIDs");
      m.annotate (Nonnull.class);
      m.annotate (ReturnsMutableCopy.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getAsUBLCustomizationID ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getAsUBLCustomizationID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getVersion ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getVersion");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getCommonName ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getCommonName");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fCommonName);

      // public String getAsDocumentTypeIdentifierValue ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getAsDocumentTypeIdentifierValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public SimpleDocumentTypeIdentifier getAsDocumentTypeIdentifier ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, SimpleDocumentTypeIdentifier.class, "getAsDocumentTypeIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (s_aCodeModel.ref (SimpleDocumentTypeIdentifier.class)).arg (JExpr._this ()));

      // public Version getSince ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.body ()._return (fSince);

      // public boolean isDefaultScheme ()
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, boolean.class, "isDefaultScheme");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr.lit (true));

      // Default methods
      if (false)
      {
        // public String getURIEncoded
        m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getURIEncoded");
        m.annotate (Nonnull.class);
        m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                       .staticInvoke ("getIdentifierURIEncoded")
                                       .arg (JExpr._this ()));

        // public String getURIPercentEncoded
        m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, String.class, "getURIPercentEncoded");
        m.annotate (Nonnull.class);
        m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                       .staticInvoke ("getIdentifierURIPercentEncoded")
                                       .arg (JExpr._this ()));
      }

      // public IPeppolDocumentTypeIdentifierParts getParts
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC, IPeppolDocumentTypeIdentifierParts.class, "getParts");
      m.annotate (Nonnull.class);
      m.body ()._return (fParts);

      // @Nullable
      // public static EPredefinedDocumentTypeIdentifier
      // getFromDocumentTypeIdentifierOrNull(@Nullable final
      // IDocumentTypeIdentifier aDocTypeID)
      m = s_jEnumPredefinedDoc.method (JMod.PUBLIC |
                                       JMod.STATIC,
                                       s_jEnumPredefinedDoc,
                                       "getFromDocumentTypeIdentifierOrNull");
      {
        m.annotate (Nullable.class);
        final JVar jValue = m.param (JMod.FINAL, IDocumentTypeIdentifier.class, "aDocTypeID");
        jValue.annotate (Nullable.class);
        final JBlock jIf = m.body ()
                            ._if (jValue.neNull ()
                                        .cand (s_aCodeModel.ref (CPeppolIdentifier.class)
                                                           .staticRef ("DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME")
                                                           .invoke ("equals")
                                                           .arg (jValue.invoke ("getScheme"))))
                            ._then ();
        final JForEach jForEach = jIf.forEach (s_jEnumPredefinedDoc, "e", s_jEnumPredefinedDoc.staticInvoke ("values"));
        jForEach.body ()
                ._if (jForEach.var ().invoke ("getValue").invoke ("equals").arg (jValue.invoke ("getValue")))
                ._then ()
                ._return (jForEach.var ());
        m.body ()._return (JExpr._null ());
      }
    }
    catch (final Exception ex)
    {
      s_aLogger.warn ("Failed to create source", ex);
    }
  }

  private static void _emitProcessIdentifier (final Sheet aProcessSheet) throws URISyntaxException
  {
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "name", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (1, "id", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "bisid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (3, "docids", UseType.REQUIRED, "string", false);
    aReadOptions.addColumn (4, "since", UseType.REQUIRED, "string", false);
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aProcessSheet,
                                                                                       aReadOptions,
                                                                                       "PeppolProcessIdentifier",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("urn:peppol.eu:names:identifier:process"),
                                                                                       new URI ("urn:peppol.eu:names:identifier:process-1.0"),
                                                                                       null);
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PeppolProcessIdentifier.gc");

    // Save as XML
    final IMicroDocument aDoc = new MicroDocument ();
    aDoc.appendComment ("This file was automatically generated. Do NOT edit!");
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sID = Genericode10Helper.getRowValue (aRow, "id");
      final String sBISID = Genericode10Helper.getRowValue (aRow, "bisid");
      final String sDocIDs = Genericode10Helper.getRowValue (aRow, "docids");
      // Split the document identifier string into a list of single strings,
      // and check if each of them is a valid predefined document identifier
      final ICommonsList <String> aDocIDs = RegExHelper.getSplitToList (sDocIDs, "\n");
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");

      final IMicroElement eAgency = eRoot.appendElement ("process");
      eAgency.setAttribute ("id", sID);
      eAgency.setAttribute ("bisid", sBISID);
      for (final String sDocID : aDocIDs)
        eAgency.appendElement ("document").setAttribute ("id", sDocID);
      eAgency.setAttribute ("since", sSince);
    }
    final String sXML = MicroWriter.getXMLString (aDoc);
    SimpleFileIO.writeFile (new File (RESULT_DIRECTORY + "PeppolProcessIdentifier.xml"),
                            sXML,
                            CCharset.CHARSET_UTF_8_OBJ);

    // Create Java source
    try
    {
      final JDefinedClass jEnum = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "identifier.process")
                                              ._enum ("EPredefinedProcessIdentifier")
                                              ._implements (IPeppolPredefinedProcessIdentifier.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add ("This file is generated. Do NOT edit!");

      // enum constants
      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sID = Genericode10Helper.getRowValue (aRow, "id");
        final String sBISID = Genericode10Helper.getRowValue (aRow, "bisid");
        final String sDocTypeIDs = Genericode10Helper.getRowValue (aRow, "docids");
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");

        final String sEnumConstName = RegExHelper.getAsIdentifier (sID);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr.lit (sID));
        jEnumConst.arg (JExpr.lit (sBISID));
        final JArray jArray = JExpr.newArray (s_jEnumPredefinedDoc);
        for (final String sDocTypeID : RegExHelper.getSplitToList (sDocTypeIDs, "\n"))
        {
          IPeppolDocumentTypeIdentifierParts aDocTypeIDParts;
          try
          {
            aDocTypeIDParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);
          }
          catch (final IllegalArgumentException ex)
          {
            aDocTypeIDParts = OpenPeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);
          }

          // Use the short name for better readability
          final String sIdentifier = true ? CodeGenerationHelper.createShortcutDocumentTypeIDName (aDocTypeIDParts)
                                          : RegExHelper.getAsIdentifier (sDocTypeID);
          jArray.add (s_jEnumPredefinedDoc.staticRef (sIdentifier));
        }
        jEnumConst.arg (jArray);
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));
        jEnumConst.javadoc ().add (sID);
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sSince);

        // Emit shortcut name for better readability
        final String sShortcutName = CodeGenerationHelper.createShortcutBISIDName (sBISID);
        if (!aAllShortcutNames.add (sShortcutName))
          throw new IllegalStateException ("The BIS ID shortcut '" +
                                           sShortcutName +
                                           "' is already used - please review the algorithm!");
        final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC |
                                                 JMod.STATIC |
                                                 JMod.FINAL,
                                                 jEnum,
                                                 sShortcutName,
                                                 jEnumConst);
        aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
      }

      // fields
      final JFieldVar fID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
      final JFieldVar fBISID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sBISID");
      final JFieldVar fDocIDs = jEnum.field (JMod.PRIVATE | JMod.FINAL, s_jEnumPredefinedDoc.array (), "m_aDocIDs");
      final JFieldVar fSince = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aSince");

      // Constructor
      final JMethod jCtor = jEnum.constructor (JMod.PRIVATE);
      final JVar jID = jCtor.param (JMod.FINAL, String.class, "sID");
      jID.annotate (Nonnull.class);
      jID.annotate (Nonempty.class);
      final JVar jBISID = jCtor.param (JMod.FINAL, String.class, "sBISID");
      jBISID.annotate (Nonnull.class);
      jBISID.annotate (Nonempty.class);
      final JVar jDocIDs = jCtor.param (JMod.FINAL, s_jEnumPredefinedDoc.array (), "aDocIDs");
      jDocIDs.annotate (Nonnull.class);
      jDocIDs.annotate (Nonempty.class);
      final JVar jSince = jCtor.param (JMod.FINAL, Version.class, "aSince");
      jSince.annotate (Nonnull.class);
      jCtor.body ().assign (fID, jID).assign (fBISID, jBISID).assign (fDocIDs, jDocIDs).assign (fSince, jSince);

      // public String getScheme ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (s_aCodeModel.ref (CPeppolIdentifier.class).staticRef ("DEFAULT_PROCESS_IDENTIFIER_SCHEME"));

      // public String getValue ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public String getBISID ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getBISID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fBISID);

      // public List<? extends IPredefinedDocumentTypeIdentifier>
      // getDocumentTypeIdentifiers ()
      m = jEnum.method (JMod.PUBLIC,
                        s_aCodeModel.ref (ICommonsList.class)
                                    .narrow (s_aCodeModel.ref (IPeppolPredefinedDocumentTypeIdentifier.class)
                                                         .wildcard ()),
                        "getDocumentTypeIdentifiers");
      m.annotate (Nonnull.class);
      m.annotate (ReturnsMutableCopy.class);
      m.body ()._return (s_aCodeModel.ref (CollectionHelper.class).staticInvoke ("newList").arg (fDocIDs));

      // public SimpleProcessIdentifier getAsProcessIdentifier ()
      m = jEnum.method (JMod.PUBLIC, SimpleProcessIdentifier.class, "getAsProcessIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (s_aCodeModel.ref (SimpleProcessIdentifier.class)).arg (JExpr._this ()));

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.body ()._return (fSince);

      // public boolean isDefaultScheme ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDefaultScheme");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr.lit (true));

      // Default methods
      if (false)
      {
        // public String getURIEncoded
        m = jEnum.method (JMod.PUBLIC, String.class, "getURIEncoded");
        m.annotate (Nonnull.class);
        m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                       .staticInvoke ("getIdentifierURIEncoded")
                                       .arg (JExpr._this ()));

        // public String getURIPercentEncoded
        m = jEnum.method (JMod.PUBLIC, String.class, "getURIPercentEncoded");
        m.annotate (Nonnull.class);
        m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                       .staticInvoke ("getIdentifierURIPercentEncoded")
                                       .arg (JExpr._this ()));
      }

      // @Nullable public static EPredefinedProcessIdentifier
      // getFromProcessIdentifierOrNull(@Nullable final IProcessIdentifier
      // aProcessID)
      m = jEnum.method (JMod.PUBLIC | JMod.STATIC, jEnum, "getFromProcessIdentifierOrNull");
      {
        m.annotate (Nullable.class);
        final JVar jValue = m.param (JMod.FINAL, IProcessIdentifier.class, "aProcessID");
        jValue.annotate (Nullable.class);
        final JBlock jIf = m.body ()
                            ._if (jValue.neNull ()
                                        .cand (s_aCodeModel.ref (CPeppolIdentifier.class)
                                                           .staticRef ("DEFAULT_PROCESS_IDENTIFIER_SCHEME")
                                                           .invoke ("equals")
                                                           .arg (jValue.invoke ("getScheme"))))
                            ._then ();
        final JForEach jForEach = jIf.forEach (jEnum, "e", jEnum.staticInvoke ("values"));
        jForEach.body ()
                ._if (jForEach.var ().invoke ("getValue").invoke ("equals").arg (jValue.invoke ("getValue")))
                ._then ()
                ._return (jForEach.var ());
        m.body ()._return (JExpr._null ());
      }
    }
    catch (final Exception ex)
    {
      s_aLogger.warn ("Failed to create source", ex);
    }
  }

  public static void main (final String [] args) throws IOException, URISyntaxException
  {
    // Where is the Excel?
    final IReadableResource aXls = new FileSystemResource (EXCEL_FILE);
    if (!aXls.exists ())
      throw new IllegalStateException ("The Excel file could not be found!");

    // Interprete as Excel
    final Workbook aWB = new HSSFWorkbook (aXls.getInputStream ());

    // Check whether all required sheets are present
    final Sheet aParticipantSheet = aWB.getSheet (SHEET_PARTICIPANT);
    if (aParticipantSheet == null)
      throw new IllegalStateException ("The " + SHEET_PARTICIPANT + " sheet could not be found!");
    final Sheet aDocumentSheet = aWB.getSheet (SHEET_DOCUMENT);
    if (aDocumentSheet == null)
      throw new IllegalStateException ("The " + SHEET_DOCUMENT + " sheet could not be found!");
    final Sheet aProcessSheet = aWB.getSheet (SHEET_PROCESS);
    if (aProcessSheet == null)
      throw new IllegalStateException ("The " + SHEET_PROCESS + " sheet could not be found!");

    // Convert participants
    _emitIdentifierIssuingAgency (aParticipantSheet);

    // Convert document identifiers
    _emitDocumentIdentifiers (aDocumentSheet);

    // Convert processes identifiers
    _emitProcessIdentifier (aProcessSheet);

    // Write all Java source files
    final FileCodeWriter aWriter = new FileCodeWriter (new File ("src/main/java"), CCharset.CHARSET_UTF_8_OBJ);
    s_aCodeModel.build (aWriter);

    s_aLogger.info ("Done creating code");
  }
}

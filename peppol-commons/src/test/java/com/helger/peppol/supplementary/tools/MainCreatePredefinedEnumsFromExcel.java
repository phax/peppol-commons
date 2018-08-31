/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.functional.IThrowingConsumer;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.version.Version;
import com.helger.genericode.Genericode10CodeListMarshaller;
import com.helger.genericode.Genericode10Helper;
import com.helger.genericode.excel.ExcelReadOptions;
import com.helger.genericode.excel.ExcelSheetToCodeList10;
import com.helger.genericode.v10.CodeListDocument;
import com.helger.genericode.v10.Row;
import com.helger.genericode.v10.UseType;
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
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.IPeppolPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.pidscheme.IParticipantIdentifierScheme;
import com.helger.peppol.identifier.peppol.process.IPeppolPredefinedProcessIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroWriter;
import com.helger.xml.namespace.MapBasedNamespaceContext;

/**
 * Utility class to create the Genericode files from the Excel code list. Also
 * creates Java source files with the predefined identifiers.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class MainCreatePredefinedEnumsFromExcel
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreatePredefinedEnumsFromExcel.class);
  private static final Version CODELIST_VERSION = new Version (3);
  private static final String RESULT_DIRECTORY = "src/main/resources/codelists/";
  private static final String RESULT_PACKAGE_PREFIX = "com.helger.peppol.identifier.peppol.";
  private static final JCodeModel s_aCodeModel = new JCodeModel ();
  private static final String DO_NOT_EDIT = "This file was automatically generated.\nDo NOT edit!";
  private static final boolean DEFAULT_DEPRECATED = false;

  @Nullable
  private static String _maskHtml (@Nullable final String s)
  {
    if (s == null)
      return null;
    String ret = s;
    ret = StringHelper.replaceAll (ret, "&", "&amp;");
    ret = StringHelper.replaceAll (ret, "<", "&lt;");
    ret = StringHelper.replaceAll (ret, ">", "&gt;");
    ret = StringHelper.replaceAll (ret, "\"", "&quot;");
    return ret;
  }

  private static void _writeGenericodeFile (@Nonnull final CodeListDocument aCodeList, @Nonnull final String sFilename)
  {
    final MapBasedNamespaceContext aNsCtx = new MapBasedNamespaceContext ();
    aNsCtx.setDefaultNamespaceURI ("");
    aNsCtx.addMapping ("gc", "http://docs.oasis-open.org/codelist/ns/genericode/1.0/");
    aNsCtx.addMapping ("ext", "urn:www.helger.com:schemas:genericode-ext");

    final Genericode10CodeListMarshaller aMarshaller = new Genericode10CodeListMarshaller ();
    aMarshaller.setNamespaceContext (aNsCtx);
    aMarshaller.setFormattedOutput (true);
    if (aMarshaller.write (aCodeList, new File (sFilename)).isFailure ())
      throw new IllegalStateException ("Failed to write file " + sFilename);
    LOGGER.info ("Wrote Genericode file " + sFilename);
  }

  private static void _emitDocumentTypes (final Sheet aDocumentSheet) throws URISyntaxException
  {
    // Create GeneriCode file
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "name", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (1, "doctypeid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "since", UseType.REQUIRED, "string", false);
    aReadOptions.addColumn (3, "deprecated", UseType.REQUIRED, "boolean", false);
    aReadOptions.addColumn (4, "deprecated-since", UseType.OPTIONAL, "string", false);
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aDocumentSheet,
                                                                                       aReadOptions,
                                                                                       "PeppolDocumentTypeIdentifier",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("urn:peppol.eu:names:identifier:documenttypes"),
                                                                                       new URI ("urn:peppol.eu:names:identifier:documenttypes-1.0"),
                                                                                       null);
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PeppolDocumentTypeIdentifier.gc");

    // Save as XML
    final IMicroDocument aDoc = new MicroDocument ();
    aDoc.appendComment (DO_NOT_EDIT);
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sName = Genericode10Helper.getRowValue (aRow, "name");
      final String sDocID = Genericode10Helper.getRowValue (aRow, "doctypeid");
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");
      final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                          DEFAULT_DEPRECATED);
      final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");
      if (bDeprecated && StringHelper.hasNoText (sDeprecatedSince))
        throw new IllegalStateException ("Code list entry is deprecated but there is no deprecated-since entry");

      final IMicroElement eAgency = eRoot.appendElement ("document-type");
      eAgency.setAttribute ("id", sDocID);
      eAgency.setAttribute ("name", sName);
      eAgency.setAttribute ("since", sSince);
      eAgency.setAttribute ("deprecated", bDeprecated);
      eAgency.setAttribute ("deprecated-since", sDeprecatedSince);
    }
    MicroWriter.writeToFile (aDoc, new File (RESULT_DIRECTORY + "PeppolDocumentTypeIdentifier.xml"));

    // Create Java source
    try
    {
      final JDefinedClass jEnum = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "doctype")
                                              ._enum ("EPredefinedDocumentTypeIdentifier")
                                              ._implements (IPeppolPredefinedDocumentTypeIdentifier.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();

      // Add all enum constants
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sName = Genericode10Helper.getRowValue (aRow, "name");
        final String sDocTypeID = Genericode10Helper.getRowValue (aRow, "doctypeid");
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");
        final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                            DEFAULT_DEPRECATED);
        final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");
        if (bDeprecated && StringHelper.hasNoText (sDeprecatedSince))
          throw new IllegalStateException ("Code list entry is deprecated but there is no deprecated-since entry");

        // Split ID in it's pieces
        final IPeppolDocumentTypeIdentifierParts aDocIDParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);

        final String sEnumConstName = RegExHelper.getAsIdentifier (sDocTypeID);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .add ("<b>This item is deprecated since version " +
                          sDeprecatedSince +
                          " and should not be used to issue new identifiers!</b><br>");
        }

        final JInvocation aNew = JExpr._new (s_aCodeModel.ref (PeppolDocumentTypeIdentifierParts.class))
                                      .arg (aDocIDParts.getRootNS ())
                                      .arg (aDocIDParts.getLocalName ())
                                      .arg (aDocIDParts.getCustomizationID ())
                                      .arg (aDocIDParts.getVersion ());
        jEnumConst.arg (aNew);

        jEnumConst.arg (JExpr.lit (sName));
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));
        jEnumConst.javadoc ().add ("<code>" + sDocTypeID + "</code><br>");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sSince);

        // Also create a shortcut for more readable names
        final String sShortcutName = CodeGenerationHelper.createShortcutDocumentTypeIDName (aDocIDParts);
        if (sShortcutName != null)
        {
          // Make unique name
          int nNext = 2;
          String sRealShortcutName = sShortcutName;
          while (!aAllShortcutNames.add (sRealShortcutName))
          {
            sRealShortcutName = sShortcutName + nNext;
            nNext++;
          }

          final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL,
                                                   jEnum,
                                                   sRealShortcutName,
                                                   jEnumConst);
          if (bDeprecated)
            aShortcut.annotate (Deprecated.class);
          aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
        }
      }

      // fields
      final JFieldVar fParts = jEnum.field (JMod.PRIVATE | JMod.FINAL,
                                            IPeppolDocumentTypeIdentifierParts.class,
                                            "m_aParts");
      final JFieldVar fID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
      final JFieldVar fCommonName = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sCommonName");
      final JFieldVar fSince = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aSince");

      // Constructor
      final JMethod jCtor = jEnum.constructor (JMod.PRIVATE);
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
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class).staticRef ("DEFAULT_DOCUMENT_TYPE_SCHEME"));

      // public String getValue ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public String getRootNS ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getRootNS");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getLocalName ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getLocalName");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getSubTypeIdentifier ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getSubTypeIdentifier");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      if (false)
      {
        // public String getTransactionID ()
        m = jEnum.method (JMod.PUBLIC, String.class, "getTransactionID");
        m.annotate (Nonnull.class);
        m.annotate (Nonempty.class);
        m.body ()._return (fParts.invoke (m.name ()));

        // public List<String> getExtensionIDs ()
        m = jEnum.method (JMod.PUBLIC, s_aCodeModel.ref (ICommonsList.class).narrow (String.class), "getExtensionIDs");
        m.annotate (Nonnull.class);
        m.annotate (ReturnsMutableCopy.class);
        m.body ()._return (fParts.invoke (m.name ()));
      }

      // public String getCustomizationID ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getCustomizationID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getVersion ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getVersion");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fParts.invoke (m.name ()));

      // public String getCommonName ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getCommonName");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fCommonName);

      // public String getAsDocumentTypeIdentifierValue ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getAsDocumentTypeIdentifierValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier ()
      m = jEnum.method (JMod.PUBLIC, PeppolDocumentTypeIdentifier.class, "getAsDocumentTypeIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (s_aCodeModel.ref (PeppolDocumentTypeIdentifier.class)).arg (JExpr._this ()));

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.body ()._return (fSince);

      // public IPeppolDocumentTypeIdentifierParts getParts
      m = jEnum.method (JMod.PUBLIC, IPeppolDocumentTypeIdentifierParts.class, "getParts");
      m.annotate (Nonnull.class);
      m.body ()._return (fParts);

      // @Nullable
      // public static EPredefinedDocumentTypeIdentifier
      // getFromDocumentTypeIdentifierOrNull(@Nullable final
      // IDocumentTypeIdentifier aDocTypeID)
      m = jEnum.method (JMod.PUBLIC | JMod.STATIC, jEnum, "getFromDocumentTypeIdentifierOrNull");
      {
        m.annotate (Nullable.class);
        final JVar jValue = m.param (JMod.FINAL, IDocumentTypeIdentifier.class, "aDocTypeID");
        jValue.annotate (Nullable.class);
        final JBlock jIf = m.body ()
                            ._if (jValue.neNull ()
                                        .cand (jValue.invoke ("hasScheme")
                                                     .arg (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                                                       .staticRef ("DEFAULT_DOCUMENT_TYPE_SCHEME"))))
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
      LOGGER.warn ("Failed to create source", ex);
    }
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

  private static void _emitParticipantIdentifierSchemes (final Sheet aParticipantSheet) throws URISyntaxException
  {
    // Read excel file
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "schemeid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (1, "iso6523", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "schemeagency", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (3, "since", UseType.REQUIRED, "string", false);
    aReadOptions.addColumn (4, "deprecated", UseType.REQUIRED, "boolean", false);
    aReadOptions.addColumn (5, "deprecated-since", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (6, "structure", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (7, "display", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (8, "examples", UseType.OPTIONAL, "string", false);
    aReadOptions.addColumn (9, "usage", UseType.OPTIONAL, "string", false);

    // Convert to GeneriCode
    final CodeListDocument aCodeList = ExcelSheetToCodeList10.convertToSimpleCodeList (aParticipantSheet,
                                                                                       aReadOptions,
                                                                                       "PeppolIdentifierIssuingAgencies",
                                                                                       CODELIST_VERSION.getAsString (),
                                                                                       new URI ("urn:peppol.eu:names:identifier:participantidentifierschemes"),
                                                                                       new URI ("urn:peppol.eu:names:identifier:participantidentifierschemes-1.0"),
                                                                                       null);
    _writeGenericodeFile (aCodeList, RESULT_DIRECTORY + "PeppolParticipantIdentifierSchemes.gc");

    _writeValidationPartyIdFile (aParticipantSheet);

    // Save data also as XML
    final IMicroDocument aDoc = new MicroDocument ();
    aDoc.appendComment (DO_NOT_EDIT);
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sSchemeID = Genericode10Helper.getRowValue (aRow, "schemeid");
      final String sISO6523 = Genericode10Helper.getRowValue (aRow, "iso6523");
      final String sAgency = Genericode10Helper.getRowValue (aRow, "schemeagency");
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");
      final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                          DEFAULT_DEPRECATED);
      final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");
      final String sStructure = Genericode10Helper.getRowValue (aRow, "structure");
      final String sDisplay = Genericode10Helper.getRowValue (aRow, "display");
      final String sExamples = Genericode10Helper.getRowValue (aRow, "examples");
      final String sUsage = Genericode10Helper.getRowValue (aRow, "usage");

      if (StringHelper.hasNoText (sSchemeID))
        throw new IllegalArgumentException ("schemeID");
      if (sSchemeID.indexOf (' ') >= 0)
        throw new IllegalArgumentException ("Scheme IDs are not supposed to contain spaces!");
      if (StringHelper.hasNoText (sISO6523))
        throw new IllegalArgumentException ("ISO6523Code");
      if (!RegExHelper.stringMatchesPattern ("[0-9]{4}", sISO6523))
        throw new IllegalArgumentException ("The ISO 6523 code '" + sISO6523 + "' does not consist of 4 numbers");
      if (bDeprecated && StringHelper.hasNoText (sDeprecatedSince))
        throw new IllegalStateException ("Code list entry is deprecated but there is no deprecated-since entry");

      final IMicroElement eAgency = eRoot.appendElement ("identifier-scheme");
      eAgency.setAttribute ("schemeid", sSchemeID);
      eAgency.setAttribute ("agencyname", sAgency);
      eAgency.setAttribute ("iso6523", sISO6523);
      eAgency.setAttribute ("since", sSince);
      eAgency.setAttribute ("deprecated", bDeprecated);
      eAgency.setAttribute ("deprecated-since", sDeprecatedSince);
      if (StringHelper.hasText (sStructure))
        eAgency.appendElement ("structure").appendText (sStructure);
      if (StringHelper.hasText (sDisplay))
        eAgency.appendElement ("display").appendText (sDisplay);
      if (StringHelper.hasText (sExamples))
        eAgency.appendElement ("examples").appendText (sExamples);
      if (StringHelper.hasText (sUsage))
        eAgency.appendElement ("usage").appendText (sUsage);
    }
    MicroWriter.writeToFile (aDoc, new File (RESULT_DIRECTORY + "PeppolParticipantIdentifierSchemes.xml"));

    // Create Java source
    try
    {
      final JDefinedClass jEnum = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "pidscheme")
                                              ._enum ("EPredefinedParticipantIdentifierScheme")
                                              ._implements (IParticipantIdentifierScheme.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // enum constants
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sSchemeID = Genericode10Helper.getRowValue (aRow, "schemeid");
        final String sISO6523 = Genericode10Helper.getRowValue (aRow, "iso6523");
        final String sAgency = Genericode10Helper.getRowValue (aRow, "schemeagency");
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");
        final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                            DEFAULT_DEPRECATED);
        final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");
        final String sStructure = Genericode10Helper.getRowValue (aRow, "structure");
        final String sDisplay = Genericode10Helper.getRowValue (aRow, "display");
        final String sExamples = Genericode10Helper.getRowValue (aRow, "examples");
        final String sUsage = Genericode10Helper.getRowValue (aRow, "usage");

        final JEnumConstant jEnumConst = jEnum.enumConstant (RegExHelper.getAsIdentifier (sSchemeID));
        jEnumConst.arg (JExpr.lit (sSchemeID));
        jEnumConst.arg (sAgency == null ? JExpr._null () : JExpr.lit (sAgency));
        jEnumConst.arg (JExpr.lit (sISO6523));
        jEnumConst.arg (bDeprecated ? JExpr.TRUE : JExpr.FALSE);
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));

        jEnumConst.javadoc ()
                  .add ("Prefix <code>" + sISO6523 + "</code>, scheme ID <code>" + sSchemeID + "</code><br>");
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .add ("\n<b>This item is deprecated since version " +
                          sDeprecatedSince +
                          " and should not be used to issue new identifiers!</b><br>");
        }
        if (StringHelper.hasText (sStructure))
          jEnumConst.javadoc ().add ("\nStructure of the code: " + _maskHtml (sStructure) + "<br>");
        if (StringHelper.hasText (sDisplay))
          jEnumConst.javadoc ().add ("\nDisplay requirements: " + _maskHtml (sDisplay) + "<br>");
        if (StringHelper.hasText (sExamples))
          jEnumConst.javadoc ().add ("\nExample value: " + _maskHtml (sExamples) + "<br>");
        if (StringHelper.hasText (sUsage))
          jEnumConst.javadoc ().add ("\nUsage information: " + _maskHtml (sUsage) + "<br>");
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
      LOGGER.warn ("Failed to create source", ex);
    }
  }

  private static void _emitProcessIdentifiers (final Sheet aProcessSheet) throws URISyntaxException
  {
    final ExcelReadOptions <UseType> aReadOptions = new ExcelReadOptions <UseType> ().setLinesToSkip (1)
                                                                                     .setLineIndexShortName (0);
    aReadOptions.addColumn (0, "name", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (1, "bisid", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (2, "id", UseType.REQUIRED, "string", true);
    aReadOptions.addColumn (3, "since", UseType.REQUIRED, "string", false);
    aReadOptions.addColumn (4, "deprecated", UseType.REQUIRED, "boolean", false);
    aReadOptions.addColumn (5, "deprecated-since", UseType.OPTIONAL, "string", false);

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
    aDoc.appendComment (DO_NOT_EDIT);
    final IMicroElement eRoot = aDoc.appendElement ("root");
    eRoot.setAttribute ("version", CODELIST_VERSION.getAsString ());
    for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
    {
      final String sName = Genericode10Helper.getRowValue (aRow, "name");
      final String sBISID = Genericode10Helper.getRowValue (aRow, "bisid");
      final String sID = Genericode10Helper.getRowValue (aRow, "id");
      final String sSince = Genericode10Helper.getRowValue (aRow, "since");
      final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                          DEFAULT_DEPRECATED);
      final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");

      if (bDeprecated && StringHelper.hasNoText (sDeprecatedSince))
        throw new IllegalStateException ("Code list entry is deprecated but there is no deprecated-since entry");

      final IMicroElement eAgency = eRoot.appendElement ("process");
      eAgency.setAttribute ("name", sName);
      eAgency.setAttribute ("bisid", sBISID);
      eAgency.setAttribute ("id", sID);
      eAgency.setAttribute ("since", sSince);
      eAgency.setAttribute ("deprecated", bDeprecated);
      eAgency.setAttribute ("deprecated-since", sDeprecatedSince);
    }
    MicroWriter.writeToFile (aDoc, new File (RESULT_DIRECTORY + "PeppolProcessIdentifier.xml"));

    // Create Java source
    try
    {
      final JDefinedClass jEnum = s_aCodeModel._package (RESULT_PACKAGE_PREFIX + "process")
                                              ._enum ("EPredefinedProcessIdentifier")
                                              ._implements (IPeppolPredefinedProcessIdentifier.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // enum constants
      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();
      for (final Row aRow : aCodeList.getSimpleCodeList ().getRow ())
      {
        final String sName = Genericode10Helper.getRowValue (aRow, "name");
        final String sBISID = Genericode10Helper.getRowValue (aRow, "bisid");
        final String sID = Genericode10Helper.getRowValue (aRow, "id");
        final String sSince = Genericode10Helper.getRowValue (aRow, "since");
        final boolean bDeprecated = StringParser.parseBool (Genericode10Helper.getRowValue (aRow, "deprecated"),
                                                            DEFAULT_DEPRECATED);
        final String sDeprecatedSince = Genericode10Helper.getRowValue (aRow, "deprecated-since");

        final String sEnumConstName = RegExHelper.getAsIdentifier (sID);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr.lit (sID));
        jEnumConst.arg (JExpr.lit (sBISID));
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .add ("<b>This item is deprecated since version " +
                          sDeprecatedSince +
                          " and should not be used to issue new identifiers!</b><br>");
        }
        jEnumConst.arg (s_aCodeModel.ref (Version.class).staticInvoke ("parse").arg (sSince));
        jEnumConst.javadoc ().add ("<code>" + sID + "</code><br>");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sSince);

        // Emit shortcut name for better readability
        // Take version number from name (ends with "V0" where 0 is the version)
        final String sShortcutName = CodeGenerationHelper.createShortcutBISIDName (sBISID +
                                                                                   "_" +
                                                                                   sName.substring (sName.lastIndexOf ('V')));
        if (sShortcutName != null)
        {
          if (!aAllShortcutNames.add (sShortcutName))
            throw new IllegalStateException ("The BIS ID shortcut '" +
                                             sShortcutName +
                                             "' is already used - please review the algorithm!");
          final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL,
                                                   jEnum,
                                                   sShortcutName,
                                                   jEnumConst);
          if (bDeprecated)
            aShortcut.annotate (Deprecated.class);
          aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
        }
      }

      // fields
      final JFieldVar fID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
      final JFieldVar fBISID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sBISID");
      final JFieldVar fSince = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aSince");

      // Constructor
      final JMethod jCtor = jEnum.constructor (JMod.PRIVATE);
      final JVar jID = jCtor.param (JMod.FINAL, String.class, "sID");
      jID.annotate (Nonnull.class);
      jID.annotate (Nonempty.class);
      final JVar jBISID = jCtor.param (JMod.FINAL, String.class, "sBISID");
      jBISID.annotate (Nonnull.class);
      jBISID.annotate (Nonempty.class);
      final JVar jSince = jCtor.param (JMod.FINAL, Version.class, "aSince");
      jSince.annotate (Nonnull.class);
      jCtor.body ().assign (fID, jID).assign (fBISID, jBISID).assign (fSince, jSince);

      // public String getScheme ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (s_aCodeModel.ref (PeppolIdentifierHelper.class).staticRef ("DEFAULT_PROCESS_SCHEME"));

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

      // public PeppolProcessIdentifier getAsProcessIdentifier ()
      m = jEnum.method (JMod.PUBLIC, PeppolProcessIdentifier.class, "getAsProcessIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (s_aCodeModel.ref (PeppolProcessIdentifier.class)).arg (JExpr._this ()));

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.body ()._return (fSince);

      // public boolean isDefaultScheme ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDefaultScheme");
      m.body ()._return (JExpr.lit (true));

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
                                        .cand (jValue.invoke ("hasScheme")
                                                     .arg (s_aCodeModel.ref (PeppolIdentifierHelper.class)
                                                                       .staticRef ("DEFAULT_PROCESS_SCHEME"))))
                            ._then ();
        final JForEach jForEach = jIf.forEach (jEnum, "e", jEnum.staticInvoke ("values"));
        jForEach.body ()
                ._if (jForEach.var ().invoke ("getValue").invoke ("equals").arg (jValue.invoke ("getValue")))
                ._then ()
                ._return (jForEach.var ());
        m.body ()._return (JExpr._null ());
      }
    }
    catch (

    final Exception ex)
    {
      LOGGER.warn ("Failed to create source", ex);
    }
  }

  private static final class CodeListFile
  {
    private final File m_aFile;
    private final IThrowingConsumer <? super Sheet, Exception> m_aHandler;

    public CodeListFile (@Nonnull final String sFilenamePart,
                         @Nonnull final IThrowingConsumer <? super Sheet, Exception> aHandler)
    {
      m_aFile = new File ("src/test/resources/codelists/PEPPOL Code Lists - " +
                          sFilenamePart +
                          " v" +
                          CODELIST_VERSION.getAsString (false) +
                          ".xlsx").getAbsoluteFile ();
      if (!m_aFile.exists ())
        throw new IllegalArgumentException ("File '" + m_aFile.getAbsolutePath () + "' does not exist!");
      m_aHandler = aHandler;
    }

  }

  public static void main (final String [] args) throws Exception
  {
    for (final CodeListFile aCLF : new CodeListFile [] { new CodeListFile ("Document types",
                                                                           MainCreatePredefinedEnumsFromExcel::_emitDocumentTypes),
                                                         new CodeListFile ("Participant identifier schemes",
                                                                           MainCreatePredefinedEnumsFromExcel::_emitParticipantIdentifierSchemes),
                                                         new CodeListFile ("Processes",
                                                                           MainCreatePredefinedEnumsFromExcel::_emitProcessIdentifiers) })
    {
      // Where is the Excel?
      final IReadableResource aXls = new FileSystemResource (aCLF.m_aFile);
      if (!aXls.exists ())
        throw new IllegalStateException ("The Excel file '" +
                                         aCLF.m_aFile.getAbsolutePath () +
                                         "' could not be found!");

      // Interprete as Excel
      try (final Workbook aWB = new XSSFWorkbook (aXls.getInputStream ()))
      {
        // Check whether all required sheets are present
        final Sheet aSheet = aWB.getSheetAt (0);
        if (aSheet == null)
          throw new IllegalStateException ("The first sheet could not be found!");

        aCLF.m_aHandler.accept (aSheet);
      }
    }

    // Write all Java source files
    final FileCodeWriter aWriter = new FileCodeWriter (new File ("src/main/java"), StandardCharsets.UTF_8);
    s_aCodeModel.build (aWriter);

    LOGGER.info ("Done creating code");
  }
}

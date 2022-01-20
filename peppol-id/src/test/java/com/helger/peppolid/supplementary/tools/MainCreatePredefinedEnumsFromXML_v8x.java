/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppolid.supplementary.tools;

import java.io.File;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.functional.IThrowingConsumer;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.commons.version.Version;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JCodeModelException;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JDocComment;
import com.helger.jcodemodel.JEnumConstant;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JForEach;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JLambdaMethodRef;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;
import com.helger.jcodemodel.writer.JCMWriter;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.IPeppolPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.pidscheme.IParticipantIdentifierScheme;
import com.helger.peppolid.peppol.process.IPeppolPredefinedProcessIdentifier;
import com.helger.peppolid.peppol.process.PeppolProcessIdentifier;
import com.helger.peppolid.peppol.transportprofile.IPredefinedTransportProfileIdentifier;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xsds.peppol.codelists2.PCLDocumentTypeType;
import com.helger.xsds.peppol.codelists2.PCLDocumentTypesType;
import com.helger.xsds.peppol.codelists2.PCLParticipantIdentifierSchemeType;
import com.helger.xsds.peppol.codelists2.PCLParticipantIdentifierSchemesType;
import com.helger.xsds.peppol.codelists2.PCLProcessIDType;
import com.helger.xsds.peppol.codelists2.PCLProcessType;
import com.helger.xsds.peppol.codelists2.PCLProcessesType;
import com.helger.xsds.peppol.codelists2.PCLStateType;
import com.helger.xsds.peppol.codelists2.PCLTransportProfileType;
import com.helger.xsds.peppol.codelists2.PCLTransportProfilesType;

/**
 * Utility class to create the Genericode files from the Excel code list. Also
 * creates Java source files with the predefined identifiers.<br>
 * Requires the code list XMLs in folder
 * <code>src/main/resources/codelists</code>
 *
 * @author Philip Helger
 */
public final class MainCreatePredefinedEnumsFromXML_v8x
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreatePredefinedEnumsFromXML_v8x.class);
  private static final Version CODELIST_VERSION = new Version (8, 0);
  private static final String RESULT_PACKAGE_PREFIX = "com.helger.peppolid.peppol.";
  private static final JCodeModel CM = new JCodeModel ();
  private static final String DO_NOT_EDIT = "This file was automatically generated.\nDo NOT edit!";

  @Nonnull
  private static EPeppolCodeListItemState _getState (@Nonnull final PCLStateType eState)
  {
    switch (eState)
    {
      case ACTIVE:
        return EPeppolCodeListItemState.ACTIVE;
      case DEPRECATED:
        return EPeppolCodeListItemState.DEPRECATED;
      case REMOVED:
        return EPeppolCodeListItemState.REMOVED;
    }
    throw new IllegalStateException ("Unsupported state " + eState);
  }

  private static void _handleDocumentTypes (final Document aDocumentSheet)
  {
    final PCLDocumentTypesType aList = new GenericJAXBMarshaller <> (PCLDocumentTypesType.class, new QName ("dummy")).read (aDocumentSheet);

    // Create Java source
    try
    {
      final JDefinedClass jEnum = CM._package (RESULT_PACKAGE_PREFIX + "doctype")
                                    ._enum ("EPredefinedDocumentTypeIdentifier")
                                    ._implements (IPeppolPredefinedDocumentTypeIdentifier.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // Add metadata
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.ref (String.class), "CODE_LIST_VERSION", JExpr.lit (aList.getVersion ()));
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.INT, "CODE_LIST_ENTRY_COUNT", JExpr.lit (aList.getEntryCount ().intValue ()));

      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();

      // Add all enum constants
      for (final PCLDocumentTypeType aRow : aList.getDocumentType ())
      {
        final String sProfileCode = aRow.getName ();
        final String sScheme = aRow.getScheme ();
        final String sValue = aRow.getValue ();
        final String sInitialRelease = aRow.getInitialRelease ();
        final EPeppolCodeListItemState eState = _getState (aRow.getState ());
        final boolean bDeprecated = !eState.isActive ();
        final String sDeprecationRelease = aRow.getDeprecationRelease ();

        // Split ID in it's pieces
        final IPeppolDocumentTypeIdentifierParts aDocIDParts = PeppolDocumentTypeIdentifierParts.extractFromString (sValue);

        final String sEnumConstName = RegExHelper.getAsIdentifier (sValue);

        if (jEnum.getEnumConstantOfName (sEnumConstName) != null)
        {
          // Avoid weird side effects
          throw new IllegalStateException ("The enum constant '" + sEnumConstName + "' is already in use");
        }

        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .addDeprecated ()
                    .add ("since " + sDeprecationRelease + " - this item should not be used to issue new identifiers!");
        }

        jEnumConst.arg (JExpr.lit (sScheme));
        {
          final JInvocation aNew = JExpr._new (CM.ref (PeppolDocumentTypeIdentifierParts.class))
                                        .arg (aDocIDParts.getRootNS ())
                                        .arg (aDocIDParts.getLocalName ())
                                        .arg (aDocIDParts.getCustomizationID ())
                                        .arg (aDocIDParts.getVersion ());
          jEnumConst.arg (aNew);
        }
        jEnumConst.arg (JExpr.lit (sProfileCode));
        jEnumConst.arg (CM.ref (Version.class).staticInvoke ("parse").arg (sInitialRelease));
        jEnumConst.arg (CM.ref (EPeppolCodeListItemState.class).enumConstantRef (eState));
        jEnumConst.arg (bDeprecated ? CM.ref (Version.class).staticInvoke ("parse").arg (sDeprecationRelease) : JExpr._null ());
        jEnumConst.arg (JExpr.lit (aRow.isIssuedByOpenpeppol ()));
        jEnumConst.arg (JExpr.lit (StringParser.parseInt (aRow.getBisVersion (), -1)));
        jEnumConst.arg (aRow.getDomainCommunity () == null ? JExpr._null () : JExpr.lit (aRow.getDomainCommunity ()));
        {
          final JInvocation aNew = JExpr._new (CM.ref (CommonsArrayList.class).narrowEmpty ());
          for (final PCLProcessIDType aProcID : aRow.getProcessId ())
            aNew.arg (CIdentifier.getURIEncoded (aProcID.getScheme (), aProcID.getValue ()));
          jEnumConst.arg (aNew);
        }
        jEnumConst.javadoc ().add ("<code>" + sValue + "</code><br>");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sInitialRelease);

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

          final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL, jEnum, sRealShortcutName, jEnumConst);
          aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
          if (bDeprecated)
          {
            aShortcut.annotate (Deprecated.class);
            aShortcut.javadoc ()
                     .addDeprecated ()
                     .add ("since " + sDeprecationRelease + " - this item should not be used to issue new identifiers!");
          }
          jEnumConst.javadoc ().add ("\nSame as {@link #" + sRealShortcutName + "}");
        }
      }

      // fields
      final JFieldVar fScheme = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sScheme");
      final JFieldVar fParts = jEnum.field (JMod.PRIVATE | JMod.FINAL, IPeppolDocumentTypeIdentifierParts.class, "m_aParts");
      final JFieldVar fID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sID");
      final JFieldVar fProfileCode = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sProfileCode");
      final JFieldVar fInitialRelease = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aInitialRelease");
      final JFieldVar fState = jEnum.field (JMod.PRIVATE | JMod.FINAL, EPeppolCodeListItemState.class, "m_eState");
      final JFieldVar fDeprecationRelease = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aDeprecationRelease");
      final JFieldVar fIssuedByOpenPeppol = jEnum.field (JMod.PRIVATE | JMod.FINAL, boolean.class, "m_bIssuedByOpenPeppol");
      final JFieldVar fBISVersion = jEnum.field (JMod.PRIVATE | JMod.FINAL, int.class, "m_nBISVersion");
      final JFieldVar fDomainCommunity = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sDomainCommunity");
      final JFieldVar fProcessIDs = jEnum.field (JMod.PRIVATE | JMod.FINAL,
                                                 CM.ref (ICommonsList.class).narrow (IProcessIdentifier.class),
                                                 "m_aProcessIDs");

      // Constructor
      final JMethod jCtor = jEnum.constructor (0);
      final JVar jScheme = jCtor.param (JMod.FINAL, String.class, "sScheme");
      jScheme.annotate (Nonnull.class);
      jScheme.annotate (Nonempty.class);
      final JVar jParts = jCtor.param (JMod.FINAL, IPeppolDocumentTypeIdentifierParts.class, "aParts");
      jParts.annotate (Nonnull.class);
      final JVar jProfileCode = jCtor.param (JMod.FINAL, String.class, "sProfileCode");
      jProfileCode.annotate (Nonnull.class);
      jProfileCode.annotate (Nonempty.class);
      final JVar jInitialRelease = jCtor.param (JMod.FINAL, Version.class, "aInitialRelease");
      jInitialRelease.annotate (Nonnull.class);
      final JVar jState = jCtor.param (JMod.FINAL, EPeppolCodeListItemState.class, "eState");
      jState.annotate (Nonnull.class);
      final JVar jDeprecationRelease = jCtor.param (JMod.FINAL, Version.class, "aDeprecationRelease");
      jDeprecationRelease.annotate (Nullable.class);
      final JVar jIssuedByOpenPeppol = jCtor.param (JMod.FINAL, boolean.class, "bIssuedByOpenPeppol");
      final JVar jBISVersion = jCtor.param (JMod.FINAL, int.class, "nBISVersion");
      final JVar jDomainCommunity = jCtor.param (JMod.FINAL, String.class, "sDomainCommunity");
      jDomainCommunity.annotate (Nullable.class);
      final JVar jProcessIDs = jCtor.param (JMod.FINAL, CM.ref (ICommonsList.class).narrow (String.class), "aProcessIDs");
      jCtor.body ()
           .assign (fScheme, jScheme)
           .assign (fParts, jParts)
           .assign (fProfileCode, jProfileCode)
           .assign (fID, fParts.invoke ("getAsDocumentTypeIdentifierValue"))
           .assign (fInitialRelease, jInitialRelease)
           .assign (fState, jState)
           .assign (fDeprecationRelease, jDeprecationRelease)
           .assign (fIssuedByOpenPeppol, jIssuedByOpenPeppol)
           .assign (fBISVersion, jBISVersion)
           .assign (fDomainCommunity, jDomainCommunity)
           .assign (fProcessIDs,
                    CM.ref (CommonsArrayList.class)
                      .narrowEmpty ()
                      ._new ()
                      .arg (jProcessIDs)
                      .arg (new JLambdaMethodRef (CM.ref (PeppolIdentifierFactory.class).staticRef ("INSTANCE"),
                                                  "parseProcessIdentifier")));

      // public String getScheme ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fScheme);

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
        m = jEnum.method (JMod.PUBLIC, CM.ref (ICommonsList.class).narrow (String.class), "getExtensionIDs");
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
      m.body ()._return (fProfileCode);

      // public String getAsDocumentTypeIdentifierValue ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getAsDocumentTypeIdentifierValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fID);

      // public PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier ()
      m = jEnum.method (JMod.PUBLIC, PeppolDocumentTypeIdentifier.class, "getAsDocumentTypeIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (CM.ref (PeppolDocumentTypeIdentifier.class)).arg (JExpr._this ()));

      // public IPeppolDocumentTypeIdentifierParts getParts
      m = jEnum.method (JMod.PUBLIC, IPeppolDocumentTypeIdentifierParts.class, "getParts");
      m.annotate (Nonnull.class);
      m.body ()._return (fParts);

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.annotate (Deprecated.class);
      m.body ()._return (fInitialRelease);

      // public Version getInitialRelease ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getInitialRelease");
      m.annotate (Nonnull.class);
      m.body ()._return (fInitialRelease);

      // public boolean isDeprecated ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDeprecated");
      m.body ()._return (fState.invoke ("isDeprecated"));

      // public EPeppolCodeListItemState getState ()
      m = jEnum.method (JMod.PUBLIC, EPeppolCodeListItemState.class, "getState");
      m.annotate (Nonnull.class);
      m.body ()._return (fState);

      // public Version getDeprecatedSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getDeprecatedSince");
      m.annotate (Nullable.class);
      m.annotate (Deprecated.class);
      m.body ()._return (fDeprecationRelease);

      // public Version getDeprecationRelease ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getDeprecationRelease");
      m.annotate (Nullable.class);
      m.body ()._return (fDeprecationRelease);

      // public boolean isIssuedByOpenPEPPOL ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isIssuedByOpenPEPPOL");
      m.annotate (Deprecated.class);
      m.body ()._return (fIssuedByOpenPeppol);

      // public boolean isIssuedByOpenPeppol ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isIssuedByOpenPeppol");
      m.body ()._return (fIssuedByOpenPeppol);

      // public int getBISVersion ()
      m = jEnum.method (JMod.PUBLIC, int.class, "getBISVersion");
      m.annotate (CheckForSigned.class);
      m.body ()._return (fBISVersion);

      // since 7.3 this method is nullable
      // public String getDomainCommunity ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getDomainCommunity");
      m.annotate (Nullable.class);
      m.body ()._return (fDomainCommunity);

      // public ICommonsList<IProcessIdentifier> getAllProcessIDs()
      m = jEnum.method (JMod.PUBLIC, CM.ref (ICommonsList.class).narrow (IProcessIdentifier.class), "getAllProcessIDs");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.annotate (ReturnsMutableCopy.class);
      m.body ()._return (fProcessIDs.invoke ("getClone"));

      // @Nullable
      // public static EPredefinedDocumentTypeIdentifier
      // getFromDocumentTypeIdentifierOrNull(@Nullable final
      // IDocumentTypeIdentifier aDocTypeID)
      m = jEnum.method (JMod.PUBLIC | JMod.STATIC, jEnum, "getFromDocumentTypeIdentifierOrNull");
      {
        m.annotate (Nullable.class);
        final JVar jValue = m.param (JMod.FINAL, IDocumentTypeIdentifier.class, "aDocTypeID");
        jValue.annotate (Nullable.class);
        final JBlock jIf = m.body ()._if (jValue.neNull ())._then ();
        final JForEach jForEach = jIf.forEach (jEnum, "e", jEnum.staticInvoke ("values"));
        jForEach.body ()
                ._if (jForEach.var ()
                              .invoke ("hasScheme")
                              .arg (jValue.invoke ("getScheme"))
                              .cand (jForEach.var ().invoke ("hasValue").arg (jValue.invoke ("getValue"))))
                ._then ()
                ._return (jForEach.var ());
        m.body ()._return (JExpr._null ());
      }
    }
    catch (final JCodeModelException ex)
    {
      LOGGER.error ("Failed to create source", ex);
    }
  }

  private static void _handleParticipantIdentifierSchemes (final Document aParticipantSheet)
  {
    final PCLParticipantIdentifierSchemesType aList = new GenericJAXBMarshaller <> (PCLParticipantIdentifierSchemesType.class,
                                                                                    new QName ("dummy")).read (aParticipantSheet);

    // Create Java source
    try
    {
      final JDefinedClass jEnum = CM._package (RESULT_PACKAGE_PREFIX + "pidscheme")
                                    ._enum ("EPredefinedParticipantIdentifierScheme")
                                    ._implements (IParticipantIdentifierScheme.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // Add metadata
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.ref (String.class), "CODE_LIST_VERSION", JExpr.lit (aList.getVersion ()));
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.INT, "CODE_LIST_ENTRY_COUNT", JExpr.lit (aList.getEntryCount ().intValue ()));

      // enum constants
      for (final PCLParticipantIdentifierSchemeType aRow : aList.getParticipantIdentifierScheme ())
      {
        final String sSchemeID = aRow.getSchemeid ();
        final String sISO6523 = aRow.getIso6523 ();
        final String sCountryCode = aRow.getCountry ();
        final String sSchemeName = aRow.getSchemeName ();
        final String sIssuingAgency = aRow.getIssuingAgency ();
        final String sInitialRelease = aRow.getInitialRelease ();
        final EPeppolCodeListItemState eState = _getState (aRow.getState ());
        final boolean bDeprecated = !eState.isActive ();
        final String sDeprecationRelease = aRow.getDeprecationRelease ();
        final String sStructure = aRow.getStructure ();
        final String sDisplay = aRow.getDisplay ();
        final String sExamples = aRow.getExamples ();
        final String sUsage = aRow.getUsage ();

        final String sEnumConstName = RegExHelper.getAsIdentifier (sSchemeID);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr.lit (sSchemeID));
        jEnumConst.arg (JExpr.lit (sISO6523));
        jEnumConst.arg (JExpr.lit (sCountryCode));
        jEnumConst.arg (JExpr.lit (sSchemeName));
        jEnumConst.arg (sIssuingAgency == null ? JExpr._null () : JExpr.lit (sIssuingAgency));
        jEnumConst.arg (CM.ref (Version.class).staticInvoke ("parse").arg (sInitialRelease));
        jEnumConst.arg (CM.ref (EPeppolCodeListItemState.class).enumConstantRef (eState));

        jEnumConst.javadoc ().add ("Prefix <code>" + sISO6523 + "</code>, scheme ID <code>" + sSchemeID + "</code><br>");
        if (StringHelper.hasText (sStructure))
          jEnumConst.javadoc ().add ("\nStructure of the code: " + CodeGenerationHelper.maskHtml (sStructure) + "<br>");
        if (StringHelper.hasText (sDisplay))
          jEnumConst.javadoc ().add ("\nDisplay requirements: " + CodeGenerationHelper.maskHtml (sDisplay) + "<br>");
        if (StringHelper.hasText (sExamples))
          jEnumConst.javadoc ().add ("\nExample value: " + CodeGenerationHelper.maskHtml (sExamples) + "<br>");
        if (StringHelper.hasText (sUsage))
          jEnumConst.javadoc ().add ("\nUsage information: " + CodeGenerationHelper.maskHtml (sUsage) + "<br>");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sInitialRelease);
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .addDeprecated ()
                    .add ("since " + sDeprecationRelease + " - this item should not be used to issue new identifiers!");
        }
      }

      // fields
      final JFieldVar fSchemeID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sSchemeID");
      final JFieldVar fISO6523 = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sISO6523");
      final JFieldVar fCountryCode = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sCountryCode");
      final JFieldVar fSchemeName = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sSchemeName");
      final JFieldVar fIssuingAgency = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sIssuingAgency");
      final JFieldVar fInitialRelease = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aInitialRelease");
      final JFieldVar fState = jEnum.field (JMod.PRIVATE | JMod.FINAL, EPeppolCodeListItemState.class, "m_eState");

      // Constructor
      final JMethod jCtor = jEnum.constructor (0);
      final JVar jSchemeID = jCtor.param (JMod.FINAL, String.class, "sSchemeID");
      jSchemeID.annotate (Nonnull.class);
      jSchemeID.annotate (Nonempty.class);
      final JVar jISO6523 = jCtor.param (JMod.FINAL, String.class, "sISO6523");
      jISO6523.annotate (Nonnull.class);
      jISO6523.annotate (Nonempty.class);
      final JVar jCountryCode = jCtor.param (JMod.FINAL, String.class, "sCountryCode");
      jCountryCode.annotate (Nonnull.class);
      jCountryCode.annotate (Nonempty.class);
      final JVar jSchemeName = jCtor.param (JMod.FINAL, String.class, "sSchemeName");
      jSchemeName.annotate (Nonnull.class);
      jSchemeName.annotate (Nonempty.class);
      final JVar jIssuingAgency = jCtor.param (JMod.FINAL, String.class, "sIssuingAgency");
      jIssuingAgency.annotate (Nullable.class);
      final JVar jInitialRelease = jCtor.param (JMod.FINAL, Version.class, "aInitialRelease");
      jInitialRelease.annotate (Nonnull.class);
      final JVar jState = jCtor.param (JMod.FINAL, EPeppolCodeListItemState.class, "eState");
      jState.annotate (Nonnull.class);
      jCtor.body ()
           .assign (fSchemeID, jSchemeID)
           .assign (fISO6523, jISO6523)
           .assign (fCountryCode, jCountryCode)
           .assign (fSchemeName, jSchemeName)
           .assign (fIssuingAgency, jIssuingAgency)
           .assign (fInitialRelease, jInitialRelease)
           .assign (fState, jState);

      // public String getSchemeID ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getSchemeID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fSchemeID);

      // public String getISO6523Code ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getISO6523Code");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fISO6523);

      // public String getCountryCode ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getCountryCode");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fCountryCode);

      // public String getSchemeName ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getSchemeName");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fSchemeName);

      // public String getSchemeAgency ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getSchemeAgency");
      m.annotate (Nullable.class);
      m.body ()._return (fIssuingAgency);

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.annotate (Deprecated.class);
      m.body ()._return (fInitialRelease);

      // public Version getInitialRelease ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getInitialRelease");
      m.annotate (Nonnull.class);
      m.body ()._return (fInitialRelease);

      // public boolean isDeprecated ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDeprecated");
      m.body ()._return (fState.invoke ("isDeprecated"));

      // public EPeppolCodeListItemState getState ()
      m = jEnum.method (JMod.PUBLIC, EPeppolCodeListItemState.class, "getState");
      m.annotate (Nonnull.class);
      m.body ()._return (fState);
    }
    catch (final Exception ex)
    {
      LOGGER.warn ("Failed to create source", ex);
    }
  }

  private static void _handleProcessIdentifiers (final Document aProcessSheet)
  {
    final PCLProcessesType aList = new GenericJAXBMarshaller <> (PCLProcessesType.class, new QName ("dummy")).read (aProcessSheet);

    final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();

    // Create Java source
    try
    {
      final JDefinedClass jEnum = CM._package (RESULT_PACKAGE_PREFIX + "process")
                                    ._enum ("EPredefinedProcessIdentifier")
                                    ._implements (IPeppolPredefinedProcessIdentifier.class);
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // Add metadata
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.ref (String.class), "CODE_LIST_VERSION", JExpr.lit (aList.getVersion ()));
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.INT, "CODE_LIST_ENTRY_COUNT", JExpr.lit (aList.getEntryCount ().intValue ()));

      // enum constants
      for (final PCLProcessType aRow : aList.getProcess ())
      {
        final String sScheme = aRow.getScheme ();
        final String sValue = aRow.getValue ();
        final EPeppolCodeListItemState eState = _getState (aRow.getState ());
        final boolean bDeprecated = !eState.isActive ();

        // Prepend the scheme, if it is non-default
        final String sIDPrefix = PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME.equals (sScheme) ? "" : sScheme + "-";
        final String sEnumConstName = RegExHelper.getAsIdentifier (sIDPrefix + sValue);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr.lit (sScheme));
        jEnumConst.arg (JExpr.lit (sValue));
        jEnumConst.arg (CM.ref (EPeppolCodeListItemState.class).enumConstantRef (eState));
        jEnumConst.javadoc ().add ("ID: <code>" + sScheme + "::" + sValue + "</code><br>");
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ().addDeprecated ().add ("This item should not be used to issue new identifiers!");
        }

        // Also create a shortcut for more readable names
        final String sShortcutName = CodeGenerationHelper.createShortcutProcess (sScheme, sValue);
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

          final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL, jEnum, sRealShortcutName, jEnumConst);
          aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
          if (bDeprecated)
          {
            aShortcut.annotate (Deprecated.class);
            aShortcut.javadoc ().addDeprecated ().add ("This item should not be used to issue new identifiers!");
          }
          jEnumConst.javadoc ().add ("\nSame as {@link #" + sRealShortcutName + "}");
        }
      }

      {
        // Deprecated names
        final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL,
                                                 jEnum,
                                                 "BIS5A_V3",
                                                 jEnum.fields ().get ("BIS3_BILLING"));
        aShortcut.annotate (Deprecated.class);
        aShortcut.javadoc ().addDeprecated ().add ("Use BIS3_BILLING instead!");
      }

      // fields
      final JFieldVar fScheme = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sScheme");
      final JFieldVar fValue = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sValue");
      final JFieldVar fState = jEnum.field (JMod.PRIVATE | JMod.FINAL, EPeppolCodeListItemState.class, "m_eState");

      // Constructor
      final JMethod jCtor = jEnum.constructor (0);
      final JVar jScheme = jCtor.param (JMod.FINAL, String.class, "sScheme");
      jScheme.annotate (Nonnull.class);
      jScheme.annotate (Nonempty.class);
      final JVar jValue = jCtor.param (JMod.FINAL, String.class, "sValue");
      jValue.annotate (Nonnull.class);
      jValue.annotate (Nonempty.class);
      final JVar jState = jCtor.param (JMod.FINAL, EPeppolCodeListItemState.class, "eState");
      jState.annotate (Nonnull.class);
      jCtor.body ().assign (fScheme, jScheme).assign (fValue, jValue).assign (fState, jState);

      // public String getScheme ()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getScheme");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fScheme);

      // public String getValue ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getValue");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fValue);

      // public boolean isDeprecated ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDeprecated");
      m.body ()._return (fState.invoke ("isDeprecated"));

      // public EPeppolCodeListItemState getState ()
      m = jEnum.method (JMod.PUBLIC, EPeppolCodeListItemState.class, "getState");
      m.annotate (Nonnull.class);
      m.body ()._return (fState);

      // public PeppolProcessIdentifier getAsProcessIdentifier ()
      m = jEnum.method (JMod.PUBLIC, PeppolProcessIdentifier.class, "getAsProcessIdentifier");
      m.annotate (Nonnull.class);
      m.body ()._return (JExpr._new (CM.ref (PeppolProcessIdentifier.class)).arg (JExpr._this ()));

      // @Nullable public static EPredefinedProcessIdentifier
      // getFromProcessIdentifierOrNull(@Nullable final IProcessIdentifier
      // aProcessID)
      m = jEnum.method (JMod.PUBLIC | JMod.STATIC, jEnum, "getFromProcessIdentifierOrNull");
      {
        m.annotate (Nullable.class);
        final JVar jParam = m.param (JMod.FINAL, IProcessIdentifier.class, "aProcessID");
        jParam.annotate (Nullable.class);
        final JBlock jIf = m.body ()._if (jParam.neNull ())._then ();
        final JForEach jForEach = jIf.forEach (jEnum, "e", jEnum.staticInvoke ("values"));
        jForEach.body ()
                ._if (jForEach.var ()
                              .invoke ("hasScheme")
                              .arg (jParam.invoke ("getScheme"))
                              .cand (jForEach.var ().invoke ("hasValue").arg (jParam.invoke ("getValue"))))
                ._then ()
                ._return (jForEach.var ());
        m.body ()._return (JExpr._null ());
      }
    }
    catch (final JCodeModelException ex)
    {
      LOGGER.warn ("Failed to create source", ex);
    }
  }

  private static void _handleTransportProfileIdentifiers (final Document aTPSheet)
  {
    final PCLTransportProfilesType aList = new GenericJAXBMarshaller <> (PCLTransportProfilesType.class,
                                                                         new QName ("dummy")).read (aTPSheet);

    // Create Java source
    try
    {
      final JDefinedClass jEnum = CM._package (RESULT_PACKAGE_PREFIX + "transportprofile")._enum ("EPredefinedTransportProfileIdentifier");
      jEnum._implements (CM.ref (IPredefinedTransportProfileIdentifier.class));
      jEnum.annotate (CodingStyleguideUnaware.class);
      jEnum.javadoc ().add (DO_NOT_EDIT);

      // Add metadata
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.ref (String.class), "CODE_LIST_VERSION", JExpr.lit (aList.getVersion ()));
      jEnum.field (JMod.PUBLIC_STATIC_FINAL, CM.INT, "CODE_LIST_ENTRY_COUNT", JExpr.lit (aList.getEntryCount ().intValue ()));

      // enum constants
      final ICommonsSet <String> aAllShortcutNames = new CommonsHashSet <> ();
      for (final PCLTransportProfileType aRow : aList.getTransportProfile ())
      {
        final String sProtocol = aRow.getProtocol ();
        final String sProfileVersion = aRow.getProfileVersion ();
        final String sProfileID = aRow.getProfileId ();
        final String sInitialRelease = aRow.getInitialRelease ();
        final EPeppolCodeListItemState eState = _getState (aRow.getState ());
        final boolean bDeprecated = !eState.isActive ();
        final String sDeprecationRelease = aRow.getDeprecationRelease ();

        // Prepend the scheme, if it is non-default
        final String sEnumConstName = RegExHelper.getAsIdentifier (sProfileID);
        final JEnumConstant jEnumConst = jEnum.enumConstant (sEnumConstName);
        jEnumConst.arg (JExpr.lit (sProtocol));
        jEnumConst.arg (JExpr.lit (sProfileVersion));
        jEnumConst.arg (JExpr.lit (sProfileID));
        jEnumConst.arg (CM.ref (Version.class).staticInvoke ("parse").arg (sInitialRelease));
        jEnumConst.arg (CM.ref (EPeppolCodeListItemState.class).enumConstantRef (eState));
        jEnumConst.javadoc ().add ("ID: <code>" + sProfileID + "</code><br>");
        jEnumConst.javadoc ().addTag (JDocComment.TAG_SINCE).add ("code list " + sInitialRelease);
        if (bDeprecated)
        {
          jEnumConst.annotate (Deprecated.class);
          jEnumConst.javadoc ()
                    .addDeprecated ()
                    .add ("since " + sDeprecationRelease + " - this item should not be used to issue new identifiers!");
        }

        // Emit shortcut name for better readability
        final String sShortcutName = CodeGenerationHelper.createShortcutTransportProtocolName (sProtocol + "_" + sProfileVersion);
        if (sShortcutName != null)
        {
          final String sRealShortcutName = sShortcutName;
          if (!aAllShortcutNames.add (sRealShortcutName))
            throw new IllegalStateException ("The Transport Profile shortcut '" +
                                             sRealShortcutName +
                                             "' is already used - please review the algorithm!");
          final JFieldVar aShortcut = jEnum.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL, jEnum, sRealShortcutName, jEnumConst);
          aShortcut.javadoc ().add ("Same as {@link #" + sEnumConstName + "}");
          jEnumConst.javadoc ().add ("\nSame as {@link #" + sRealShortcutName + "}");
          if (bDeprecated)
          {
            aShortcut.annotate (Deprecated.class);
            aShortcut.javadoc ()
                     .addDeprecated ()
                     .add ("since " + sDeprecationRelease + " - this item should not be used to issue new identifiers!");
          }
        }
      }

      // fields
      final JFieldVar fProtocol = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sProtocol");
      final JFieldVar fProfileVersion = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sProfileVersion");
      final JFieldVar fProfileID = jEnum.field (JMod.PRIVATE | JMod.FINAL, String.class, "m_sProfileID");
      final JFieldVar fInitialRelease = jEnum.field (JMod.PRIVATE | JMod.FINAL, Version.class, "m_aInitialRelease");
      final JFieldVar fState = jEnum.field (JMod.PRIVATE | JMod.FINAL, EPeppolCodeListItemState.class, "m_eState");

      // Constructor
      final JMethod jCtor = jEnum.constructor (0);
      final JVar jProtocol = jCtor.param (JMod.FINAL, String.class, "sProtocol");
      jProtocol.annotate (Nonnull.class);
      jProtocol.annotate (Nonempty.class);
      final JVar jProfileVersion = jCtor.param (JMod.FINAL, String.class, "sProfileVersion");
      jProfileVersion.annotate (Nonnull.class);
      jProfileVersion.annotate (Nonempty.class);
      final JVar jProfileID = jCtor.param (JMod.FINAL, String.class, "sProfileID");
      jProfileID.annotate (Nonnull.class);
      jProfileID.annotate (Nonempty.class);
      final JVar jInitialRelease = jCtor.param (JMod.FINAL, Version.class, "aInitialRelease");
      jInitialRelease.annotate (Nonnull.class);
      final JVar jState = jCtor.param (JMod.FINAL, EPeppolCodeListItemState.class, "eState");
      jState.annotate (Nonnull.class);
      jCtor.body ()
           .assign (fProtocol, jProtocol)
           .assign (fProfileVersion, jProfileVersion)
           .assign (fProfileID, jProfileID)
           .assign (fInitialRelease, jInitialRelease)
           .assign (fState, jState);

      // public String getProtocol()
      JMethod m = jEnum.method (JMod.PUBLIC, String.class, "getProtocol");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fProtocol);

      // public String getProfileVersion ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getProfileVersion");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fProfileVersion);

      // public String getProfileID ()
      m = jEnum.method (JMod.PUBLIC, String.class, "getProfileID");
      m.annotate (Nonnull.class);
      m.annotate (Nonempty.class);
      m.body ()._return (fProfileID);

      // public Version getSince ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getSince");
      m.annotate (Nonnull.class);
      m.annotate (Deprecated.class);
      m.body ()._return (fInitialRelease);

      // public Version getInitialRelease ()
      m = jEnum.method (JMod.PUBLIC, Version.class, "getInitialRelease");
      m.annotate (Nonnull.class);
      m.body ()._return (fInitialRelease);

      // public boolean isDeprecated ()
      m = jEnum.method (JMod.PUBLIC, boolean.class, "isDeprecated");
      m.body ()._return (fState.invoke ("isDeprecated"));

      // public EPeppolCodeListItemState getState ()
      m = jEnum.method (JMod.PUBLIC, EPeppolCodeListItemState.class, "getState");
      m.annotate (Nonnull.class);
      m.body ()._return (fState);
    }
    catch (final JCodeModelException ex)
    {
      LOGGER.warn ("Failed to create source", ex);
    }
  }

  private static final class CodeListFile
  {
    private final File m_aFile;
    private final IThrowingConsumer <? super Document, Exception> m_aHandler;

    public CodeListFile (@Nonnull final String sFilenamePart, @Nonnull final IThrowingConsumer <? super Document, Exception> aHandler)
    {
      m_aFile = new File ("src/main/resources/codelists/Peppol Code Lists - " +
                          sFilenamePart +
                          " v" +
                          CODELIST_VERSION.getAsString (false, true) +
                          ".xml").getAbsoluteFile ();
      if (!m_aFile.exists ())
        throw new IllegalArgumentException ("File '" + m_aFile.getAbsolutePath () + "' does not exist!");
      m_aHandler = aHandler;
    }
  }

  public static void main (final String [] args) throws Exception
  {
    for (final CodeListFile aCLF : new CodeListFile [] { new CodeListFile ("Document types",
                                                                           MainCreatePredefinedEnumsFromXML_v8x::_handleDocumentTypes),
                                                         new CodeListFile ("Participant identifier schemes",
                                                                           MainCreatePredefinedEnumsFromXML_v8x::_handleParticipantIdentifierSchemes),
                                                         new CodeListFile ("Processes",
                                                                           MainCreatePredefinedEnumsFromXML_v8x::_handleProcessIdentifiers),
                                                         new CodeListFile ("Transport profiles",
                                                                           MainCreatePredefinedEnumsFromXML_v8x::_handleTransportProfileIdentifiers) })
    {
      final Document aDoc = DOMReader.readXMLDOM (aCLF.m_aFile);
      if (aDoc == null)
        throw new IllegalStateException ("Failed to read " + aCLF.m_aFile.getAbsolutePath () + " as XML file");
      // Interpret as Excel
      aCLF.m_aHandler.accept (aDoc);
    }

    // Write all Java source files
    new JCMWriter (CM).build (new File ("src/main/java"), LOGGER::info);

    LOGGER.info ("Done creating code");
  }
}

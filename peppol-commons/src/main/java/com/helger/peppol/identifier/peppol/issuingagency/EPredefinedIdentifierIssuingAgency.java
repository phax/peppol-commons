/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
package com.helger.peppol.identifier.peppol.issuingagency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;


/**
 * This file is generated. Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedIdentifierIssuingAgency
    implements IIdentifierIssuingAgency
{

    /**
     * Prefix <code>0002</code>, scheme ID <code>FR:SIRENE</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    FR_SIRENE("FR:SIRENE", "Institut National de la Statistique et des Etudes Economiques, (I.N.S.E.E.)", "0002", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0007</code>, scheme ID <code>SE:ORGNR</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    SE_ORGNR("SE:ORGNR", "The National Tax Board", "0007", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0009</code>, scheme ID <code>FR:SIRET</code><br>
     * 
     * 
     * @since code list 1.1.1
     */
    FR_SIRET("FR:SIRET", "DU PONT DE NEMOURS", "0009", false, Version.parse("1.1.1")),

    /**
     * Prefix <code>0037</code>, scheme ID <code>FI:OVT</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    FI_OVT("FI:OVT", "National Board of Taxes, (Verohallitus)", "0037", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0060</code>, scheme ID <code>DUNS</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DUNS("DUNS", "Dun and Bradstreet Ltd", "0060", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0088</code>, scheme ID <code>GLN</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    GLN("GLN", "EAN International", "0088", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0096</code>, scheme ID <code>DK:P</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DK_P("DK:P", "Danish Chamber of Commerce", "0096", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0097</code>, scheme ID <code>IT:FTI</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IT_FTI("IT:FTI", "FTI - Ediforum Italia", "0097", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0106</code>, scheme ID <code>NL:KVK</code><br>
     * 
     * 
     * @since code list 1.1.2
     */
    NL_KVK("NL:KVK", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland, Scheme", "0106", false, Version.parse("1.1.2")),

    /**
     * Prefix <code>0135</code>, scheme ID <code>IT:SIA</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IT_SIA("IT:SIA", "SIA-Societ\u00e0 Interbancaria per l'Automazione S.p.A.", "0135", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0142</code>, scheme ID <code>IT:SECETI</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IT_SECETI("IT:SECETI", "Servizi Centralizzati SECETI S.p.A.", "0142", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>0184</code>, scheme ID <code>DIGST</code><br>
     * 
     * 
     * @since code list 1.2.1
     */
    DIGST("DIGST", "DIGSTORG", "0184", false, Version.parse("1.2.1")),

    /**
     * Prefix <code>9901</code>, scheme ID <code>DK:CPR</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DK_CPR("DK:CPR", "Danish Ministry of the Interior and Health", "9901", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9902</code>, scheme ID <code>DK:CVR</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DK_CVR("DK:CVR", "The Danish Commerce and Companies Agency", "9902", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9904</code>, scheme ID <code>DK:SE</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DK_SE("DK:SE", "Danish Ministry of Taxation, Central Customs and Tax Administration", "9904", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9905</code>, scheme ID <code>DK:VANS</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    DK_VANS("DK:VANS", "Danish VANS providers", "9905", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9906</code>, scheme ID <code>IT:VAT</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IT_VAT("IT:VAT", "Ufficio responsabile gestione partite IVA", "9906", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9907</code>, scheme ID <code>IT:CF</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IT_CF("IT:CF", "TAX Authority", "9907", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9908</code>, scheme ID <code>NO:ORGNR</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    NO_ORGNR("NO:ORGNR", "Enhetsregisteret ved Bronnoysundregisterne", "9908", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9909</code>, scheme ID <code>NO:VAT</code><br>
     * <b>This item is deprecated and should not be used to issue new identifiers!</b><br>
     * 
     * 
     * @since code list 1.0.0
     */
    NO_VAT("NO:VAT", "Enhetsregisteret ved Bronnoysundregisterne", "9909", true, Version.parse("1.0.0")),

    /**
     * Prefix <code>9910</code>, scheme ID <code>HU:VAT</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    HU_VAT("HU:VAT", null, "9910", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9912</code>, scheme ID <code>EU:VAT</code><br>
     * <b>This item is deprecated and should not be used to issue new identifiers!</b><br>
     * 
     * 
     * @since code list 1.0.0
     */
    EU_VAT("EU:VAT", "National ministries of Economy", "9912", true, Version.parse("1.0.0")),

    /**
     * Prefix <code>9913</code>, scheme ID <code>EU:REID</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    EU_REID("EU:REID", "Business Registers Network", "9913", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9914</code>, scheme ID <code>AT:VAT</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    AT_VAT("AT:VAT", "\u00d6sterreichische Umsatzsteuer-Identifikationsnummer", "9914", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9915</code>, scheme ID <code>AT:GOV</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    AT_GOV("AT:GOV", "\u00d6sterreichisches Verwaltungs bzw. Organisationskennzeichen", "9915", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9916</code>, scheme ID <code>AT:CID</code><br>
     * <b>This item is deprecated and should not be used to issue new identifiers!</b><br>
     * 
     * 
     * @since code list 1.0.0
     */
    AT_CID("AT:CID", "Firmenidentifikationsnummer der Statistik Austria", "9916", true, Version.parse("1.0.0")),

    /**
     * Prefix <code>9917</code>, scheme ID <code>IS:KT</code><br>
     * 
     * 
     * @since code list 1.0.0
     */
    IS_KT("IS:KT", "Icelandic National Registry", "9917", false, Version.parse("1.0.0")),

    /**
     * Prefix <code>9918</code>, scheme ID <code>IBAN</code><br>
     * 
     * 
     * @since code list 1.0.1
     */
    IBAN("IBAN", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", "9918", false, Version.parse("1.0.1")),

    /**
     * Prefix <code>9919</code>, scheme ID <code>AT:KUR</code><br>
     * 
     * 
     * @since code list 1.0.2
     */
    AT_KUR("AT:KUR", "Kennziffer des Unternehmensregisters", "9919", false, Version.parse("1.0.2")),

    /**
     * Prefix <code>9920</code>, scheme ID <code>ES:VAT</code><br>
     * 
     * 
     * @since code list 1.0.2
     */
    ES_VAT("ES:VAT", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", "9920", false, Version.parse("1.0.2")),

    /**
     * Prefix <code>9921</code>, scheme ID <code>IT:IPA</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    IT_IPA("IT:IPA", "Indice delle Pubbliche Amministrazioni", "9921", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9922</code>, scheme ID <code>AD:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    AD_VAT("AD:VAT", "Andorra VAT number", "9922", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9923</code>, scheme ID <code>AL:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    AL_VAT("AL:VAT", "Albania VAT number", "9923", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9924</code>, scheme ID <code>BA:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    BA_VAT("BA:VAT", "Bosnia and Herzegovina VAT number", "9924", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9925</code>, scheme ID <code>BE:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    BE_VAT("BE:VAT", "Belgium VAT number", "9925", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9926</code>, scheme ID <code>BG:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    BG_VAT("BG:VAT", "Bulgaria VAT number", "9926", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9927</code>, scheme ID <code>CH:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    CH_VAT("CH:VAT", "Switzerland VAT number", "9927", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9928</code>, scheme ID <code>CY:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    CY_VAT("CY:VAT", "Cyprus VAT number", "9928", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9929</code>, scheme ID <code>CZ:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    CZ_VAT("CZ:VAT", "Czech Republic VAT number", "9929", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9930</code>, scheme ID <code>DE:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    DE_VAT("DE:VAT", "Germany VAT number", "9930", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9931</code>, scheme ID <code>EE:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    EE_VAT("EE:VAT", "Estonia VAT number", "9931", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9932</code>, scheme ID <code>GB:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    GB_VAT("GB:VAT", "United Kingdom VAT number", "9932", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9933</code>, scheme ID <code>GR:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    GR_VAT("GR:VAT", "Greece VAT number", "9933", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9934</code>, scheme ID <code>HR:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    HR_VAT("HR:VAT", "Croatia VAT number", "9934", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9935</code>, scheme ID <code>IE:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    IE_VAT("IE:VAT", "Ireland VAT number", "9935", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9936</code>, scheme ID <code>LI:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    LI_VAT("LI:VAT", "Liechtenstein VAT number", "9936", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9937</code>, scheme ID <code>LT:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    LT_VAT("LT:VAT", "Lithuania VAT number", "9937", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9938</code>, scheme ID <code>LU:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    LU_VAT("LU:VAT", "Luxemburg VAT number", "9938", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9939</code>, scheme ID <code>LV:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    LV_VAT("LV:VAT", "Latvia VAT number", "9939", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9940</code>, scheme ID <code>MC:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    MC_VAT("MC:VAT", "Monaco VAT number", "9940", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9941</code>, scheme ID <code>ME:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    ME_VAT("ME:VAT", "Montenegro VAT number", "9941", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9942</code>, scheme ID <code>MK:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    MK_VAT("MK:VAT", "Macedonia, the former Yugoslav Republic of VAT number", "9942", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9943</code>, scheme ID <code>MT:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    MT_VAT("MT:VAT", "Malta VAT number", "9943", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9944</code>, scheme ID <code>NL:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    NL_VAT("NL:VAT", "Netherlands VAT number", "9944", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9945</code>, scheme ID <code>PL:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    PL_VAT("PL:VAT", "Poland VAT number", "9945", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9946</code>, scheme ID <code>PT:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    PT_VAT("PT:VAT", "Portugal VAT number", "9946", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9947</code>, scheme ID <code>RO:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    RO_VAT("RO:VAT", "Romania VAT number", "9947", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9948</code>, scheme ID <code>RS:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    RS_VAT("RS:VAT", "Serbia VAT number", "9948", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9949</code>, scheme ID <code>SI:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    SI_VAT("SI:VAT", "Slovenia VAT number", "9949", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9950</code>, scheme ID <code>SK:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    SK_VAT("SK:VAT", "Slovakia VAT number", "9950", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9951</code>, scheme ID <code>SM:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    SM_VAT("SM:VAT", "San Marino VAT number", "9951", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9952</code>, scheme ID <code>TR:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    TR_VAT("TR:VAT", "Turkey VAT number", "9952", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9953</code>, scheme ID <code>VA:VAT</code><br>
     * 
     * 
     * @since code list 1.1.0
     */
    VA_VAT("VA:VAT", "Holy See (Vatican City State) VAT number", "9953", false, Version.parse("1.1.0")),

    /**
     * Prefix <code>9954</code>, scheme ID <code>NL:OIN</code><br>
     * 
     * 
     * @since code list 1.1.3
     */
    NL_OIN("NL:OIN", "Dutch Originator's Identification Number", "9954", false, Version.parse("1.1.3")),

    /**
     * Prefix <code>9955</code>, scheme ID <code>SE:VAT</code><br>
     * 
     * 
     * @since code list 1.2.0
     */
    SE_VAT("SE:VAT", "Swedish VAT number", "9955", false, Version.parse("1.2.0")),

    /**
     * Prefix <code>9956</code>, scheme ID <code>BE:CBE</code><br>
     * 
     * 
     * @since code list 1.2.1
     */
    BE_CBE("BE:CBE", "Belgian Crossroad Bank of Enterprises ", "9956", false, Version.parse("1.2.1")),

    /**
     * Prefix <code>9957</code>, scheme ID <code>FR:VAT</code><br>
     * 
     * 
     * @since code list 1.2.1
     */
    FR_VAT("FR:VAT", "French VAT number", "9957", false, Version.parse("1.2.1"));
    private final String m_sSchemeID;
    private final String m_sSchemeAgency;
    private final String m_sISO6523;
    private final boolean m_bDeprecated;
    private final Version m_aSince;

    private EPredefinedIdentifierIssuingAgency(
        @Nonnull
        @Nonempty
        final String sSchemeID,
        @Nullable
        final String sSchemeAgency,
        @Nonnull
        @Nonempty
        final String sISO6523, final boolean bDeprecated,
        @Nonnull
        final Version aSince) {
        m_sSchemeID = sSchemeID;
        m_sSchemeAgency = sSchemeAgency;
        m_sISO6523 = sISO6523;
        m_bDeprecated = bDeprecated;
        m_aSince = aSince;
    }

    @Nonnull
    @Nonempty
    public String getSchemeID() {
        return m_sSchemeID;
    }

    @Nullable
    public String getSchemeAgency() {
        return m_sSchemeAgency;
    }

    @Nonnull
    @Nonempty
    public String getISO6523Code() {
        return m_sISO6523;
    }

    @Nonnull
    @Nonempty
    public String createIdentifierValue(
        @Nonnull
        @Nonempty
        final String sIdentifier) {
        return ((m_sISO6523 +":")+ sIdentifier);
    }

    @Nonnull
    public PeppolParticipantIdentifier createParticipantIdentifier(
        @Nonnull
        @Nonempty
        final String sIdentifier) {
        return ((PeppolParticipantIdentifier) PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme(createIdentifierValue(sIdentifier)));
    }

    public boolean isDeprecated() {
        return m_bDeprecated;
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }
}

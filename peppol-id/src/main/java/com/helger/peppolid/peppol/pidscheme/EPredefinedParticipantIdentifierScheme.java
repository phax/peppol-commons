/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.pidscheme;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedParticipantIdentifierScheme
    implements IParticipantIdentifierScheme
{

    /**
     * Prefix <code>0002</code>, scheme ID <code>FR:SIRENE</code><br>
     * Structure of the code: 1) Number of characters: 9 characters (&quot;SIREN&quot;) 14 &quot; 9+5 (&quot;SIRET&quot;), The 9 character number designates an organization, The 14 character number designates a specific establishment of the organization designated by the first 9 characters. 2) Check digits: 9th &amp; 14th character respectively<br>
     * Display requirements: The 9 figure code number (SIREN) is written in groups of 3 characters. Example: 784 301 772, The 14 figure code number is written in 3 groups of 3 characters and a
     * single group of 5. Example: 784 301 772 00025<br>
     * Usage information: 1.1.1 - Changed from FR:SIRET to FR:SIRENE (see ISU Jira ISU-231)<br>
     * 
     * @since code list 1.0.0
     */
    FR_SIRENE("FR:SIRENE", "Institut National de la Statistique et des Etudes Economiques, (I.N.S.E.E.)", "0002", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0007</code>, scheme ID <code>SE:ORGNR</code><br>
     * Structure of the code: 1) 10 digits. 1st digit = Group number, 2nd - 9th digit = Ordinalnumber1st digit, = Group number, 10th digit = Check digit, 2) Last digit.<br>
     * Display requirements: Single group of 10 digits.<br>
     * 
     * @since code list 1.0.0
     */
    SE_ORGNR("SE:ORGNR", "The National Tax Board", "0007", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0009</code>, scheme ID <code>FR:SIRET</code><br>
     * Structure of the code: 1) 14 digits, 2) None<br>
     * Display requirements: In four groups, Groups 1 - 3 = three digits each, Group 4 = five digits<br>
     * 
     * @since code list 1.1.1
     */
    FR_SIRET("FR:SIRET", "DU PONT DE NEMOURS", "0009", Version.parse("1.1.1"), false),

    /**
     * Prefix <code>0037</code>, scheme ID <code>FI:OVT</code><br>
     * Structure of the code: 1) ICD 4 Digits, Organization code upto 11 characters, Organization name upto 250 characters, 2) None
     * - Example: 00371234567800001
     * - 0037 Country code for Finland (ISO 6523  International Code Designator (ICD) value)
     * - 12345678 Business ID without hyphen 
     * - 00001 Optional specifier for organisation unit (assigned by the organisation itself)<br>
     * Display requirements: None<br>
     * Example value: OVT identifier conforming to standard ISO6523.   
     * - Constant 0037 (Finnish tax administration organisation code)
     * - Finnish local tax ID, 8 characters with initial zero and no hyphen
     * - Free-format 5 characters, for example profit center.  
     * Example: 003710948874<br>
     * 
     * @since code list 1.0.0
     */
    FI_OVT("FI:OVT", "National Board of Taxes, (Verohallitus)", "0037", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0060</code>, scheme ID <code>DUNS</code><br>
     * Structure of the code: 1) 8 digits, 1st-7th digit = number, 8th digit = check number, 2) digit<br>
     * Display requirements: Single group of 8 digits<br>
     * 
     * @since code list 1.0.0
     */
    DUNS("DUNS", "Dun and Bradstreet Ltd", "0060", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0088</code>, scheme ID <code>GLN</code><br>
     * Structure of the code: 1) Eight identification digits and a check digit. A two digit prefix will be added in the future but it will not be used to calculate the check digit. 2) The Organization name is not part of the D-U-N-S number.<br>
     * Display requirements: IIIIIIIIC where all characters are the digits 0, to 9, I = an identification digit and C = the check digit. When the prefix (P) is added the display requirement will be eleven digits, PPIIIIIIIIC.<br>
     * 
     * @since code list 1.0.0
     */
    GLN("GLN", "GS1 GLN", "0088", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0096</code>, scheme ID <code>DK:P</code><br>
     * Structure of the code: 1) 13 digits including check digits, 2) None<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    DK_P("DK:P", "Danish Chamber of Commerce", "0096", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0097</code>, scheme ID <code>IT:FTI</code><br>
     * Structure of the code: Character repertoire, The EDI identifier consists of digits only. The identifier has a fixed length. No separators are required. Structure: [123] [123456] [123456] [12], 17, &lt; &gt;, A B C D, A: numerical value allocated by the RA to the regional sub-authority, (3 digits), B: numerical value allocated by the sub-authority to the registered organization (mandatory part of the identifier; 6 digits), C: numerical value used by the registered organization (free part; 6 digits), D: numerical check digit calculated by the registered organization; (2 digits), Check digit computation, The check digit is modular 97 computed on ABC as one number.<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_FTI("IT:FTI", "FTI - Ediforum Italia", "0097", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0106</code>, scheme ID <code>NL:KVK</code><br>
     * 
     * @since code list 1.1.2
     */
    NL_KVK("NL:KVK", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland, Scheme", "0106", Version.parse("1.1.2"), false),

    /**
     * Prefix <code>0130</code>, scheme ID <code>EU:NAL</code><br>
     * Structure of the code: 1) ICD 4 digits, 2) None<br>
     * Display requirements: None<br>
     * 
     * @since code list 4
     */
    EU_NAL("EU:NAL", "European Commission, Information Directorate, Data Transmission Service, Rue de\nla Loi, 200, B-1049 Brussels, Belgium", "0130", Version.parse("4"), false),

    /**
     * Prefix <code>0135</code>, scheme ID <code>IT:SIA</code><br>
     * Structure of the code: Structure of EDI identifier, Character repertoire, The EDI identifier consists of digits only. The identifier has a fixed length. No separators are required. Structure:
     * [1234567] [123] [1] [12345], min 11- max 16, &lt; &gt;, A B C D, A: numerical value (7 digits) assigned by Uffico Provinciale IVA (local branch of Ministry of Finance); B: numerical value a (3 digits) identifying the County; C: numerical check digit (1 digit); D: optional numerical value (up to 5 digits0 used by the registered organization (free part). Check digit computation, The check digit algorithm is the one published in the Gazzetta Ufficiale no 345 of December 29 1976.<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_SIA("IT:SIA", "SIA-Societ\u00e0 Interbancaria per l'Automazione S.p.A.", "0135", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0142</code>, scheme ID <code>IT:SECETI</code><br>
     * Structure of the code: First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_SECETI("IT:SECETI", "Servizi Centralizzati SECETI S.p.A.", "0142", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0184</code>, scheme ID <code>DK:DIGST</code><br>
     * Structure of the code: Defined by Danish Agency for Digitisation<br>
     * 
     * @since code list 1.2.1
     */
    DK_DIGST("DK:DIGST", "DIGSTORG", "0184", Version.parse("1.2.1"), false),

    /**
     * Prefix <code>0190</code>, scheme ID <code>NL:OINO</code><br>
     * 
     * @since code list 2
     */
    NL_OINO("NL:OINO", "Dutch Originator's Identification Number", "0190", Version.parse("2"), false),

    /**
     * Prefix <code>0191</code>, scheme ID <code>EE:CC</code><br>
     * Structure of the code: Always 8-digit number<br>
     * Display requirements: None<br>
     * 
     * @since code list 2
     */
    EE_CC("EE:CC", "Centre of Registers and Information Systems of the Ministry of Justice", "0191", Version.parse("2"), false),

    /**
     * Prefix <code>0192</code>, scheme ID <code>NO:ORG</code><br>
     * Structure of the code: 9 digits
     * The organization number consists of 9 digits where the last digit is a control digit calculated with standard weights, modulus 11. After this, weights 3, 2, 7, 6, 5, 4, 3 and 2 are calculated from the first digit.<br>
     * Display requirements: None<br>
     * 
     * @since code list 2
     */
    NO_ORG("NO:ORG", "The Br\u00f8nn\u00f8ysund Register Centre", "0192", Version.parse("2"), false),

    /**
     * Prefix <code>0193</code>, scheme ID <code>UBLBE</code><br>
     * Structure of the code: Maximum 50 characters
     *  4 Characters fixed length identifying the type 
     * Maximum 46 characters for the identifier itself<br>
     * Display requirements: None<br>
     * 
     * @since code list 3
     */
    UBLBE("UBLBE", "UBL.BE", "0193", Version.parse("3"), false),

    /**
     * Prefix <code>0195</code>, scheme ID <code>SG:UEN</code><br>
     * 
     * @since code list 4
     */
    SG_UEN("SG:UEN", "Singaport Nationwide E-Invoice Framework", "0195", Version.parse("4"), false),

    /**
     * Prefix <code>0196</code>, scheme ID <code>IS:KTNR</code><br>
     * 
     * @since code list 4
     */
    IS_KTNR("IS:KTNR", "Icelandic National Registry", "0196", Version.parse("4"), false),

    /**
     * Prefix <code>9901</code>, scheme ID <code>DK:CPR</code><br>
     * Structure of the code: 1) First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    DK_CPR("DK:CPR", "Danish Ministry of the Interior and Health", "9901", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9902</code>, scheme ID <code>DK:CVR</code><br>
     * Example value: 7603770123<br>
     * 
     * @since code list 1.0.0
     */
    DK_CVR("DK:CVR", "The Danish Commerce and Companies Agency", "9902", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9904</code>, scheme ID <code>DK:SE</code><br>
     * Example value: DK26769388<br>
     * 
     * @since code list 1.0.0
     */
    DK_SE("DK:SE", "Danish Ministry of Taxation, Central Customs and Tax Administration", "9904", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9905</code>, scheme ID <code>DK:VANS</code><br>
     * Example value: DK26769388<br>
     * 
     * @since code list 1.0.0
     */
    DK_VANS("DK:VANS", "Danish VANS providers", "9905", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9906</code>, scheme ID <code>IT:VAT</code><br>
     * Example value: IT06363391001<br>
     * 
     * @since code list 1.0.0
     */
    IT_VAT("IT:VAT", "Ufficio responsabile gestione partite IVA", "9906", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9907</code>, scheme ID <code>IT:CF</code><br>
     * Example value: RSSBBR69C48F839A
     * NOTE: The &quot;CF&quot; is a Fiscal Code that can be &quot;personal&quot; or for a &quot;legal entity&quot;.
     * The CF for legal entities is like the Italian VAT code (IT:VAT)<br>
     * 
     * @since code list 1.0.0
     */
    IT_CF("IT:CF", "TAX Authority", "9907", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9908</code>, scheme ID <code>NO:ORGNR</code><br>
     * 
     * @since code list 1.0.0
     */
    NO_ORGNR("NO:ORGNR", "Enhetsregisteret ved Bronnoysundregisterne", "9908", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9909</code>, scheme ID <code>NO:VAT</code><br>
     * <b>This item is deprecated since version 1.1.0 and should not be used to issue new identifiers!</b><br>
     * Example value: 990399123<br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    NO_VAT("NO:VAT", "Enhetsregisteret ved Bronnoysundregisterne", "9909", Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9910</code>, scheme ID <code>HU:VAT</code><br>
     * Example value: 990399123MVA<br>
     * 
     * @since code list 1.0.0
     */
    HU_VAT("HU:VAT", null, "9910", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9912</code>, scheme ID <code>EU:VAT</code><br>
     * <b>This item is deprecated since version 1.1.0 and should not be used to issue new identifiers!</b><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    EU_VAT("EU:VAT", "National ministries of Economy", "9912", Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9913</code>, scheme ID <code>EU:REID</code><br>
     * 
     * @since code list 1.0.0
     */
    EU_REID("EU:REID", "Business Registers Network", "9913", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9914</code>, scheme ID <code>AT:VAT</code><br>
     * Example value: ATU12345678<br>
     * 
     * @since code list 1.0.0
     */
    AT_VAT("AT:VAT", "\u00d6sterreichische Umsatzsteuer-Identifikationsnummer", "9914", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9915</code>, scheme ID <code>AT:GOV</code><br>
     * 
     * @since code list 1.0.0
     */
    AT_GOV("AT:GOV", "\u00d6sterreichisches Verwaltungs bzw. Organisationskennzeichen", "9915", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9916</code>, scheme ID <code>AT:CID</code><br>
     * <b>This item is deprecated since version 1.0.2 and should not be used to issue new identifiers!</b><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    AT_CID("AT:CID", "Firmenidentifikationsnummer der Statistik Austria", "9916", Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9917</code>, scheme ID <code>IS:KT</code><br>
     * <b>This item is deprecated since version 4 and should not be used to issue new identifiers!</b><br>
     * Usage information: In favour of 0196<br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    IS_KT("IS:KT", "Icelandic National Registry", "9917", Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9918</code>, scheme ID <code>IBAN</code><br>
     * 
     * @since code list 1.0.1
     */
    IBAN("IBAN", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", "9918", Version.parse("1.0.1"), false),

    /**
     * Prefix <code>9919</code>, scheme ID <code>AT:KUR</code><br>
     * Structure of the code: 9 characters in total; letter, number x3, letter, number x3, letter<br>
     * 
     * @since code list 1.0.2
     */
    AT_KUR("AT:KUR", "Kennziffer des Unternehmensregisters", "9919", Version.parse("1.0.2"), false),

    /**
     * Prefix <code>9920</code>, scheme ID <code>ES:VAT</code><br>
     * 
     * @since code list 1.0.2
     */
    ES_VAT("ES:VAT", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", "9920", Version.parse("1.0.2"), false),

    /**
     * Prefix <code>9921</code>, scheme ID <code>IT:IPA</code><br>
     * 
     * @since code list 1.1.0
     */
    IT_IPA("IT:IPA", "Indice delle Pubbliche Amministrazioni", "9921", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9922</code>, scheme ID <code>AD:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AD_VAT("AD:VAT", "Andorra VAT number", "9922", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9923</code>, scheme ID <code>AL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AL_VAT("AL:VAT", "Albania VAT number", "9923", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9924</code>, scheme ID <code>BA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BA_VAT("BA:VAT", "Bosnia and Herzegovina VAT number", "9924", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9925</code>, scheme ID <code>BE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BE_VAT("BE:VAT", "Belgium VAT number", "9925", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9926</code>, scheme ID <code>BG:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BG_VAT("BG:VAT", "Bulgaria VAT number", "9926", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9927</code>, scheme ID <code>CH:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CH_VAT("CH:VAT", "Switzerland VAT number", "9927", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9928</code>, scheme ID <code>CY:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CY_VAT("CY:VAT", "Cyprus VAT number", "9928", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9929</code>, scheme ID <code>CZ:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CZ_VAT("CZ:VAT", "Czech Republic VAT number", "9929", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9930</code>, scheme ID <code>DE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    DE_VAT("DE:VAT", "Germany VAT number", "9930", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9931</code>, scheme ID <code>EE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    EE_VAT("EE:VAT", "Estonia VAT number", "9931", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9932</code>, scheme ID <code>GB:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GB_VAT("GB:VAT", "United Kingdom VAT number", "9932", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9933</code>, scheme ID <code>GR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GR_VAT("GR:VAT", "Greece VAT number", "9933", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9934</code>, scheme ID <code>HR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    HR_VAT("HR:VAT", "Croatia VAT number", "9934", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9935</code>, scheme ID <code>IE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    IE_VAT("IE:VAT", "Ireland VAT number", "9935", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9936</code>, scheme ID <code>LI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LI_VAT("LI:VAT", "Liechtenstein VAT number", "9936", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9937</code>, scheme ID <code>LT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LT_VAT("LT:VAT", "Lithuania VAT number", "9937", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9938</code>, scheme ID <code>LU:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LU_VAT("LU:VAT", "Luxemburg VAT number", "9938", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9939</code>, scheme ID <code>LV:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LV_VAT("LV:VAT", "Latvia VAT number", "9939", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9940</code>, scheme ID <code>MC:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MC_VAT("MC:VAT", "Monaco VAT number", "9940", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9941</code>, scheme ID <code>ME:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    ME_VAT("ME:VAT", "Montenegro VAT number", "9941", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9942</code>, scheme ID <code>MK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MK_VAT("MK:VAT", "Macedonia, the former Yugoslav Republic of VAT number", "9942", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9943</code>, scheme ID <code>MT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MT_VAT("MT:VAT", "Malta VAT number", "9943", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9944</code>, scheme ID <code>NL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    NL_VAT("NL:VAT", "Netherlands VAT number", "9944", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9945</code>, scheme ID <code>PL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PL_VAT("PL:VAT", "Poland VAT number", "9945", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9946</code>, scheme ID <code>PT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PT_VAT("PT:VAT", "Portugal VAT number", "9946", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9947</code>, scheme ID <code>RO:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RO_VAT("RO:VAT", "Romania VAT number", "9947", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9948</code>, scheme ID <code>RS:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RS_VAT("RS:VAT", "Serbia VAT number", "9948", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9949</code>, scheme ID <code>SI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SI_VAT("SI:VAT", "Slovenia VAT number", "9949", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9950</code>, scheme ID <code>SK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SK_VAT("SK:VAT", "Slovakia VAT number", "9950", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9951</code>, scheme ID <code>SM:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SM_VAT("SM:VAT", "San Marino VAT number", "9951", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9952</code>, scheme ID <code>TR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    TR_VAT("TR:VAT", "Turkey VAT number", "9952", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9953</code>, scheme ID <code>VA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    VA_VAT("VA:VAT", "Holy See (Vatican City State) VAT number", "9953", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9954</code>, scheme ID <code>NL:OIN</code><br>
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br>
     * Usage information: Deprecated by 0190<br>
     * 
     * @since code list 1.1.3
     */
    @Deprecated
    NL_OIN("NL:OIN", "Dutch Originator's Identification Number", "9954", Version.parse("1.1.3"), true),

    /**
     * Prefix <code>9955</code>, scheme ID <code>SE:VAT</code><br>
     * 
     * @since code list 1.2.0
     */
    SE_VAT("SE:VAT", "Swedish VAT number", "9955", Version.parse("1.2.0"), false),

    /**
     * Prefix <code>9956</code>, scheme ID <code>BE:CBE</code><br>
     * Structure of the code: Format: 9.999.999.999 - Check: 99 = 97 - (9.999.999.9 modulo 97)<br>
     * 
     * @since code list 1.2.1
     */
    BE_CBE("BE:CBE", "Belgian Crossroad Bank of Enterprises", "9956", Version.parse("1.2.1"), false),

    /**
     * Prefix <code>9957</code>, scheme ID <code>FR:VAT</code><br>
     * 
     * @since code list 1.2.1
     */
    FR_VAT("FR:VAT", "French VAT number", "9957", Version.parse("1.2.1"), false),

    /**
     * Prefix <code>9958</code>, scheme ID <code>DE:LID</code><br>
     * Usage information: Temporary until ISO 6523 identifier approved<br>
     * 
     * @since code list 3
     */
    DE_LID("DE:LID", "German Leitweg ID", "9958", Version.parse("3"), false);
    private final String m_sSchemeID;
    private final String m_sSchemeAgency;
    private final String m_sISO6523;
    private final Version m_aSince;
    private final boolean m_bDeprecated;

    private EPredefinedParticipantIdentifierScheme(@Nonnull @Nonempty final String sSchemeID,
        @Nullable final String sSchemeAgency,
        @Nonnull @Nonempty final String sISO6523,
        @Nonnull final Version aSince,
        final boolean bDeprecated) {
        m_sSchemeID = sSchemeID;
        m_sSchemeAgency = sSchemeAgency;
        m_sISO6523 = sISO6523;
        m_aSince = aSince;
        m_bDeprecated = bDeprecated;
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
    public Version getSince() {
        return m_aSince;
    }

    public boolean isDeprecated() {
        return m_bDeprecated;
    }
}

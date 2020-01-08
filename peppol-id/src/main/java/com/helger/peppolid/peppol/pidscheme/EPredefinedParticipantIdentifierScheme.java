/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.pidscheme;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


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
    FR_SIRENE("FR:SIRENE", "0002", "FR", "System Information et Repertoire des Entreprise et des Etablissements: SIRENE", "Institut National de la Statistique et des Etudes Economiques, (I.N.S.E.E.)", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0007</code>, scheme ID <code>SE:ORGNR</code><br>
     * Structure of the code: 1) 10 digits. 1st digit = Group number, 2nd - 9th digit = Ordinalnumber1st digit, = Group number, 10th digit = Check digit, 2) Last digit.<br>
     * Display requirements: Single group of 10 digits.<br>
     * 
     * @since code list 1.0.0
     */
    SE_ORGNR("SE:ORGNR", "0007", "SE", "Organisationsnummer", "The National Tax Board", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0009</code>, scheme ID <code>FR:SIRET</code><br>
     * Structure of the code: 1) 14 digits, 2) None<br>
     * Display requirements: In four groups, Groups 1 - 3 = three digits each, Group 4 = five digits<br>
     * 
     * @since code list 1.1.1
     */
    FR_SIRET("FR:SIRET", "0009", "FR", "SIRET-CODE", "DU PONT DE NEMOURS", Version.parse("1.1.1"), false),

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
    FI_OVT("FI:OVT", "0037", "FI", "LY-tunnus", "National Board of Taxes, (Verohallitus)", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0060</code>, scheme ID <code>DUNS</code><br>
     * Structure of the code: 1) 8 digits, 1st-7th digit = number, 8th digit = check number, 2) digit<br>
     * Display requirements: Single group of 8 digits<br>
     * 
     * @since code list 1.0.0
     */
    DUNS("DUNS", "0060", "international", "Data Universal Numbering System (D-U-N-S Number)", "Dun and Bradstreet Ltd", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0088</code>, scheme ID <code>GLN</code><br>
     * Structure of the code: 1) Eight identification digits and a check digit. A two digit prefix will be added in the future but it will not be used to calculate the check digit. 2) The Organization name is not part of the D-U-N-S number.<br>
     * Display requirements: IIIIIIIIC where all characters are the digits 0, to 9, I = an identification digit and C = the check digit. When the prefix (P) is added the display requirement will be eleven digits, PPIIIIIIIIC.<br>
     * 
     * @since code list 1.0.0
     */
    GLN("GLN", "0088", "international", "Global Location Number", "GS1 GLN", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0096</code>, scheme ID <code>DK:P</code><br>
     * Structure of the code: 1) 13 digits including check digits, 2) None<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    DK_P("DK:P", "0096", "DK", "DANISH CHAMBER OF COMMERCE Scheme", "Danish Chamber of Commerce", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0097</code>, scheme ID <code>IT:FTI</code><br>
     * Structure of the code: Character repertoire, The EDI identifier consists of digits only. The identifier has a fixed length. No separators are required. Structure: [123] [123456] [123456] [12], 17, &lt; &gt;, A B C D, A: numerical value allocated by the RA to the regional sub-authority, (3 digits), B: numerical value allocated by the sub-authority to the registered organization (mandatory part of the identifier; 6 digits), C: numerical value used by the registered organization (free part; 6 digits), D: numerical check digit calculated by the registered organization; (2 digits), Check digit computation, The check digit is modular 97 computed on ABC as one number.<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_FTI("IT:FTI", "0097", "IT", "FTI - Ediforum Italia", "FTI - Ediforum Italia", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0106</code>, scheme ID <code>NL:KVK</code><br>
     * 
     * @since code list 1.1.2
     */
    NL_KVK("NL:KVK", "0106", "NL", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland (Association of\nChambers of Commerce and Industry in the Netherlands), Scheme", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland", Version.parse("1.1.2"), false),

    /**
     * Prefix <code>0130</code>, scheme ID <code>EU:NAL</code><br>
     * Structure of the code: 1) ICD 4 digits, 2) None<br>
     * Display requirements: None<br>
     * 
     * @since code list 4
     */
    EU_NAL("EU:NAL", "0130", "international", "Directorates of the European Commission", "European Commission, Information Directorate, Data Transmission Service", Version.parse("4"), false),

    /**
     * Prefix <code>0135</code>, scheme ID <code>IT:SIA</code><br>
     * Structure of the code: Structure of EDI identifier, Character repertoire, The EDI identifier consists of digits only. The identifier has a fixed length. No separators are required. Structure:
     * [1234567] [123] [1] [12345], min 11- max 16, &lt; &gt;, A B C D, A: numerical value (7 digits) assigned by Uffico Provinciale IVA (local branch of Ministry of Finance); B: numerical value a (3 digits) identifying the County; C: numerical check digit (1 digit); D: optional numerical value (up to 5 digits0 used by the registered organization (free part). Check digit computation, The check digit algorithm is the one published in the Gazzetta Ufficiale no 345 of December 29 1976.<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_SIA("IT:SIA", "0135", "IT", "SIA Object Identifiers", "SIA-Societ\u00e0 Interbancaria per l'Automazione S.p.A.", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0142</code>, scheme ID <code>IT:SECETI</code><br>
     * Structure of the code: First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    IT_SECETI("IT:SECETI", "0142", "IT", "SECETI Object Identifiers", "Servizi Centralizzati SECETI S.p.A.", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>0151</code>, scheme ID <code>AU:ABN</code><br>
     * Structure of the code: The ABN is an 11 digit numeric identifier. The first two digits are check digits calculated using a modulus 89 of the weighted digits. The first digit of the number is guaranteed to be non-zero by adding 10 to the modulus (so the first two digits range from 10 through to 99). For companies the last 9 digits are identical to their existing Australian Company Number (CAN) which includes a single trailing check digit. For other entities the digits are randomly allocated but are guaranteed to fail the CAN check. This allows the Australian Securities and Investment Commission (ASIC) to continue allocating CAN during a transition period. The valid characters are numeric digits 0-9.<br>
     * Display requirements: It is displayed as follows: -, XX XXX XXX XXX<br>
     * 
     * @since code list 5
     */
    AU_ABN("AU:ABN", "0151", "AU", "Australian Business Number (ABN) Scheme", "Australian Taxation Office", Version.parse("5"), false),

    /**
     * Prefix <code>0183</code>, scheme ID <code>CH:UIDB</code><br>
     * Structure of the code: CHEXXXXXXXXP
     * UID number, is composed by 9 digits and is random generated and has no internal means.
     *  1)
     *  12 characters
     * CHE: Swiss Country Code following ISO 3166-1.
     * XXXXXXXX: 8 digits for the number itself
     * P: check digit
     *  2)
     * CHEXXXXXXXXP, the last digit<br>
     * Display requirements: There is no requirements. As suggested in the standards eCH-0097 (http://www.ech.ch) the transmission of the UID is without any separator.
     * For display reason is suggested to use this format CHE-XXX.XXX.XXP minus-character ('-') after 'CHE' and separator dot-character ('.') after 6th and 9th character<br>
     * Usage information: Also called &quot;Numéro d'identification suisse des enterprises (IDE)&quot;<br>
     * 
     * @since code list 5
     */
    CH_UIDB("CH:UIDB", "0183", "CH", "Swiss Unique Business Identification Number (UIDB)", "Swiss Federal Statistical Office (FSO)", Version.parse("5"), false),

    /**
     * Prefix <code>0184</code>, scheme ID <code>DK:DIGST</code><br>
     * Structure of the code: Defined by Danish Agency for Digitisation<br>
     * 
     * @since code list 1.2.1
     */
    DK_DIGST("DK:DIGST", "0184", "DK", "DIGSTORG", "DIGSTORG", Version.parse("1.2.1"), false),

    /**
     * Prefix <code>0190</code>, scheme ID <code>NL:OINO</code><br>
     * 
     * @since code list 2
     */
    NL_OINO("NL:OINO", "0190", "NL", "Organisatie-identificatienummer (OIN)", "Logius", Version.parse("2"), false),

    /**
     * Prefix <code>0191</code>, scheme ID <code>EE:CC</code><br>
     * Structure of the code: Always 8-digit number<br>
     * Display requirements: None<br>
     * 
     * @since code list 2
     */
    EE_CC("EE:CC", "0191", "EE", "Company code", "Centre of Registers and Information Systems of the Ministry of Justice", Version.parse("2"), false),

    /**
     * Prefix <code>0192</code>, scheme ID <code>NO:ORG</code><br>
     * Structure of the code: 9 digits
     * The organization number consists of 9 digits where the last digit is a control digit calculated with standard weights, modulus 11. After this, weights 3, 2, 7, 6, 5, 4, 3 and 2 are calculated from the first digit.<br>
     * Display requirements: None<br>
     * 
     * @since code list 2
     */
    NO_ORG("NO:ORG", "0192", "NO", "Organisasjonsnummer", "The Br\u00f8nn\u00f8ysund Register Centre", Version.parse("2"), false),

    /**
     * Prefix <code>0193</code>, scheme ID <code>UBLBE</code><br>
     * Structure of the code: Maximum 50 characters
     *  4 Characters fixed length identifying the type 
     * Maximum 46 characters for the identifier itself<br>
     * Display requirements: None<br>
     * 
     * @since code list 3
     */
    UBLBE("UBLBE", "0193", "BE", "UBL.BE Party Identifier", "UBL.BE", Version.parse("3"), false),

    /**
     * Prefix <code>0195</code>, scheme ID <code>SG:UEN</code><br>
     * 
     * @since code list 4
     */
    SG_UEN("SG:UEN", "0195", "SG", "Singapore Nationwide E-Invoice Framework", "lnfocomm Media Development Authority", Version.parse("4"), false),

    /**
     * Prefix <code>0196</code>, scheme ID <code>IS:KTNR</code><br>
     * 
     * @since code list 4
     */
    IS_KTNR("IS:KTNR", "0196", "IS", "Icelandic identifier", "Icelandic National Registry", Version.parse("4"), false),

    /**
     * Prefix <code>0198</code>, scheme ID <code>DK:ERST</code><br>
     * Structure of the code: DKXXXXXXXX
     * Two characters (DK) followed by 8 digits eg. DK12345678<br>
     * Display requirements: 10 characters, no space or other separator<br>
     * 
     * @since code list 5
     */
    DK_ERST("DK:ERST", "0198", "DK", "ERSTORG", "The Danish Business Authority", Version.parse("5"), false),

    /**
     * Prefix <code>0199</code>, scheme ID <code>LEI</code><br>
     * Structure of the code: The ISO 17442 standard specifies the minimum reference data, which must the format of the organization identifiers, be Supplied for each LEI:
     * * The official name of the legal entity as recorded in the official registers.
     * * The registered address of that legal entity.
     * * The country of formation.
     * * The codes for the representation of names of countries and their subdivisions.
     * * The date of the first LEI assignment; the date of last update of the
     * * LEI information; and the date of expiry, if applicable.
     * Additional information may be registered as agreed between the legal entity and its LEI issuing organization.
     * etc. - see ICD sheet for further information<br>
     * Display requirements: The entire 20 character code (including the check digits)<br>
     * 
     * @since code list 5
     */
    LEI("LEI", "0199", "international", "Legal Entity Identifier (LEI)", "As of December 2018, there are 33 LEI issuing organizations in the world.", Version.parse("5"), false),

    /**
     * Prefix <code>0200</code>, scheme ID <code>LT:LEC</code><br>
     * Structure of the code: 9 (nine) digits
     * Each legal entity's code number is multiplied by 1, 2, 3, 4, 5, 6, 7, 8
     * (weighting) starting from the left-hand side of the code. Calculates the sum of the product, divided by 11. The remainder of the division, which must be less than 10, is the 9th, control, number of the legal entity's code.<br>
     * 
     * @since code list 5
     */
    LT_LEC("LT:LEC", "0200", "LT", "Legal entity code", "State Enterprise Centre of Registers", Version.parse("5"), false),

    /**
     * Prefix <code>9901</code>, scheme ID <code>DK:CPR</code><br>
     * Structure of the code: 1) First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * 
     * @since code list 1.0.0
     */
    DK_CPR("DK:CPR", "9901", "DK", "Danish Ministry of the Interior and Health", "Danish Ministry of the Interior and Health", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9902</code>, scheme ID <code>DK:CVR</code><br>
     * Example value: 7603770123<br>
     * 
     * @since code list 1.0.0
     */
    DK_CVR("DK:CVR", "9902", "DK", "The Danish Commerce and Companies Agency", "The Danish Commerce and Companies Agency", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9904</code>, scheme ID <code>DK:SE</code><br>
     * Example value: DK26769388<br>
     * 
     * @since code list 1.0.0
     */
    DK_SE("DK:SE", "9904", "DK", "Danish Ministry of Taxation, Central Customs and Tax Administration", "Danish Ministry of Taxation, Central Customs and Tax Administration", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9905</code>, scheme ID <code>DK:VANS</code><br>
     * Example value: DK26769388<br>
     * 
     * @since code list 1.0.0
     */
    DK_VANS("DK:VANS", "9905", "DK", "Danish VANS providers", "Danish VANS providers", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9906</code>, scheme ID <code>IT:VAT</code><br>
     * Example value: IT06363391001<br>
     * 
     * @since code list 1.0.0
     */
    IT_VAT("IT:VAT", "9906", "IT", "Ufficio responsabile gestione partite IVA", null, Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9907</code>, scheme ID <code>IT:CF</code><br>
     * Example value: RSSBBR69C48F839A
     * NOTE: The &quot;CF&quot; is a Fiscal Code that can be &quot;personal&quot; or for a &quot;legal entity&quot;.
     * The CF for legal entities is like the Italian VAT code (IT:VAT)<br>
     * 
     * @since code list 1.0.0
     */
    IT_CF("IT:CF", "9907", "IT", "TAX Authority", "TAX Authority", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9908</code>, scheme ID <code>NO:ORGNR</code><br>
     * 
     * @since code list 1.0.0
     */
    NO_ORGNR("NO:ORGNR", "9908", "NO", "Enhetsregisteret ved Bronnoysundregisterne", "The Br\u00f8nn\u00f8ysund Register Centre", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9909</code>, scheme ID <code>NO:VAT</code><br>
     * <b>This item is deprecated since version 1.1.0 and should not be used to issue new identifiers!</b><br>
     * Example value: 990399123<br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    NO_VAT("NO:VAT", "9909", "NO", "Norwegian VAT number", "Enhetsregisteret ved Bronnoysundregisterne", Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9910</code>, scheme ID <code>HU:VAT</code><br>
     * Example value: 990399123MVA<br>
     * 
     * @since code list 1.0.0
     */
    HU_VAT("HU:VAT", "9910", "HU", "Hungary VAT number", null, Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9912</code>, scheme ID <code>EU:VAT</code><br>
     * <b>This item is deprecated since version 1.1.0 and should not be used to issue new identifiers!</b><br>
     * Structure of the code: Must start with the country code<br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    EU_VAT("EU:VAT", "9912", "international", "National ministries of Economy", null, Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9913</code>, scheme ID <code>EU:REID</code><br>
     * 
     * @since code list 1.0.0
     */
    EU_REID("EU:REID", "9913", "international", "Business Registers Network", "Business Registers Network", Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9914</code>, scheme ID <code>AT:VAT</code><br>
     * Example value: ATU12345678<br>
     * 
     * @since code list 1.0.0
     */
    AT_VAT("AT:VAT", "9914", "AT", "\u00d6sterreichische Umsatzsteuer-Identifikationsnummer", null, Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9915</code>, scheme ID <code>AT:GOV</code><br>
     * 
     * @since code list 1.0.0
     */
    AT_GOV("AT:GOV", "9915", "AT", "\u00d6sterreichisches Verwaltungs bzw. Organisationskennzeichen", null, Version.parse("1.0.0"), false),

    /**
     * Prefix <code>9916</code>, scheme ID <code>AT:CID</code><br>
     * <b>This item is deprecated since version 1.0.2 and should not be used to issue new identifiers!</b><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    AT_CID("AT:CID", "9916", "AT", "Firmenidentifikationsnummer der Statistik Austria", null, Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9917</code>, scheme ID <code>IS:KT</code><br>
     * <b>This item is deprecated since version 4 and should not be used to issue new identifiers!</b><br>
     * Usage information: In favour of 0196<br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    IS_KT("IS:KT", "9917", "IS", "Icelandic National Registry", null, Version.parse("1.0.0"), true),

    /**
     * Prefix <code>9918</code>, scheme ID <code>IBAN</code><br>
     * 
     * @since code list 1.0.1
     */
    IBAN("IBAN", "9918", "international", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", Version.parse("1.0.1"), false),

    /**
     * Prefix <code>9919</code>, scheme ID <code>AT:KUR</code><br>
     * Structure of the code: 9 characters in total; letter, number x3, letter, number x3, letter<br>
     * 
     * @since code list 1.0.2
     */
    AT_KUR("AT:KUR", "9919", "AT", "Kennziffer des Unternehmensregisters", null, Version.parse("1.0.2"), false),

    /**
     * Prefix <code>9920</code>, scheme ID <code>ES:VAT</code><br>
     * 
     * @since code list 1.0.2
     */
    ES_VAT("ES:VAT", "9920", "ES", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", Version.parse("1.0.2"), false),

    /**
     * Prefix <code>9921</code>, scheme ID <code>IT:IPA</code><br>
     * 
     * @since code list 1.1.0
     */
    IT_IPA("IT:IPA", "9921", "IT", "Indice delle Pubbliche Amministrazioni", "Indice delle Pubbliche Amministrazioni", Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9922</code>, scheme ID <code>AD:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AD_VAT("AD:VAT", "9922", "AD", "Andorra VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9923</code>, scheme ID <code>AL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AL_VAT("AL:VAT", "9923", "AL", "Albania VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9924</code>, scheme ID <code>BA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BA_VAT("BA:VAT", "9924", "BA", "Bosnia and Herzegovina VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9925</code>, scheme ID <code>BE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BE_VAT("BE:VAT", "9925", "BE", "Belgium VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9926</code>, scheme ID <code>BG:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BG_VAT("BG:VAT", "9926", "BG", "Bulgaria VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9927</code>, scheme ID <code>CH:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CH_VAT("CH:VAT", "9927", "CH", "Switzerland VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9928</code>, scheme ID <code>CY:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CY_VAT("CY:VAT", "9928", "CY", "Cyprus VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9929</code>, scheme ID <code>CZ:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CZ_VAT("CZ:VAT", "9929", "CZ", "Czech Republic VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9930</code>, scheme ID <code>DE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    DE_VAT("DE:VAT", "9930", "DE", "Germany VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9931</code>, scheme ID <code>EE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    EE_VAT("EE:VAT", "9931", "EE", "Estonia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9932</code>, scheme ID <code>GB:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GB_VAT("GB:VAT", "9932", "GB", "United Kingdom VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9933</code>, scheme ID <code>GR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GR_VAT("GR:VAT", "9933", "GR", "Greece VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9934</code>, scheme ID <code>HR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    HR_VAT("HR:VAT", "9934", "HR", "Croatia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9935</code>, scheme ID <code>IE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    IE_VAT("IE:VAT", "9935", "IE", "Ireland VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9936</code>, scheme ID <code>LI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LI_VAT("LI:VAT", "9936", "LI", "Liechtenstein VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9937</code>, scheme ID <code>LT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LT_VAT("LT:VAT", "9937", "LT", "Lithuania VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9938</code>, scheme ID <code>LU:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LU_VAT("LU:VAT", "9938", "LU", "Luxemburg VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9939</code>, scheme ID <code>LV:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LV_VAT("LV:VAT", "9939", "LV", "Latvia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9940</code>, scheme ID <code>MC:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MC_VAT("MC:VAT", "9940", "MC", "Monaco VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9941</code>, scheme ID <code>ME:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    ME_VAT("ME:VAT", "9941", "ME", "Montenegro VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9942</code>, scheme ID <code>MK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MK_VAT("MK:VAT", "9942", "MK", "Macedonia, the former Yugoslav Republic of VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9943</code>, scheme ID <code>MT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MT_VAT("MT:VAT", "9943", "MT", "Malta VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9944</code>, scheme ID <code>NL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    NL_VAT("NL:VAT", "9944", "NL", "Netherlands VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9945</code>, scheme ID <code>PL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PL_VAT("PL:VAT", "9945", "PL", "Poland VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9946</code>, scheme ID <code>PT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PT_VAT("PT:VAT", "9946", "PT", "Portugal VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9947</code>, scheme ID <code>RO:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RO_VAT("RO:VAT", "9947", "RO", "Romania VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9948</code>, scheme ID <code>RS:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RS_VAT("RS:VAT", "9948", "RS", "Serbia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9949</code>, scheme ID <code>SI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SI_VAT("SI:VAT", "9949", "SI", "Slovenia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9950</code>, scheme ID <code>SK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SK_VAT("SK:VAT", "9950", "SK", "Slovakia VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9951</code>, scheme ID <code>SM:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SM_VAT("SM:VAT", "9951", "SM", "San Marino VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9952</code>, scheme ID <code>TR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    TR_VAT("TR:VAT", "9952", "TR", "Turkey VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9953</code>, scheme ID <code>VA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    VA_VAT("VA:VAT", "9953", "VA", "Holy See (Vatican City State) VAT number", null, Version.parse("1.1.0"), false),

    /**
     * Prefix <code>9954</code>, scheme ID <code>NL:OIN</code><br>
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br>
     * Usage information: Deprecated by 0190<br>
     * 
     * @since code list 1.1.3
     */
    @Deprecated
    NL_OIN("NL:OIN", "9954", "NL", "Dutch Originator's Identification Number", null, Version.parse("1.1.3"), true),

    /**
     * Prefix <code>9955</code>, scheme ID <code>SE:VAT</code><br>
     * 
     * @since code list 1.2.0
     */
    SE_VAT("SE:VAT", "9955", "SE", "Swedish VAT number", null, Version.parse("1.2.0"), false),

    /**
     * Prefix <code>9956</code>, scheme ID <code>BE:CBE</code><br>
     * Structure of the code: Format: 9.999.999.999 - Check: 99 = 97 - (9.999.999.9 modulo 97)<br>
     * 
     * @since code list 1.2.1
     */
    BE_CBE("BE:CBE", "9956", "BE", "Belgian Crossroad Bank of Enterprise number", "Belgian Crossroad Bank of Enterprises", Version.parse("1.2.1"), false),

    /**
     * Prefix <code>9957</code>, scheme ID <code>FR:VAT</code><br>
     * 
     * @since code list 1.2.1
     */
    FR_VAT("FR:VAT", "9957", "FR", "French VAT number", null, Version.parse("1.2.1"), false),

    /**
     * Prefix <code>9958</code>, scheme ID <code>DE:LID</code><br>
     * Usage information: Temporary until ISO 6523 identifier approved<br>
     * 
     * @since code list 3
     */
    DE_LID("DE:LID", "9958", "DE", "German Leitweg ID", null, Version.parse("3"), false);
    private final String m_sSchemeID;
    private final String m_sISO6523;
    private final String m_sCountryCode;
    private final String m_sSchemeName;
    private final String m_sIssuingAgency;
    private final Version m_aSince;
    private final boolean m_bDeprecated;

    private EPredefinedParticipantIdentifierScheme(@Nonnull @Nonempty final String sSchemeID,
        @Nonnull @Nonempty final String sISO6523,
        @Nonnull @Nonempty final String sCountryCode,
        @Nonnull @Nonempty final String sSchemeName,
        @Nullable final String sIssuingAgency,
        @Nonnull final Version aSince,
        final boolean bDeprecated) {
        m_sSchemeID = sSchemeID;
        m_sISO6523 = sISO6523;
        m_sCountryCode = sCountryCode;
        m_sSchemeName = sSchemeName;
        m_sIssuingAgency = sIssuingAgency;
        m_aSince = aSince;
        m_bDeprecated = bDeprecated;
    }

    @Nonnull
    @Nonempty
    public String getSchemeID() {
        return m_sSchemeID;
    }

    @Nonnull
    @Nonempty
    public String getISO6523Code() {
        return m_sISO6523;
    }

    @Nonnull
    @Nonempty
    public String getCountryCode() {
        return m_sCountryCode;
    }

    @Nonnull
    @Nonempty
    public String getSchemeName() {
        return m_sSchemeName;
    }

    @Nullable
    public String getSchemeAgency() {
        return m_sIssuingAgency;
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    public boolean isDeprecated() {
        return m_bDeprecated;
    }
}

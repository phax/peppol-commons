package com.helger.peppolid.peppol.pidscheme;

import java.time.LocalDate;
import java.time.Month;
import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedParticipantIdentifierScheme
    implements IPeppolParticipantIdentifierScheme
{

    /**
     * Prefix <code>0002</code>, scheme ID <code>FR:SIRENE</code><br>
     * Structure of the code: 1) Number of characters: 9 characters (&quot;SIREN&quot;) 14 &quot; 9+5 (&quot;SIRET&quot;), The 9 character number designates an organization, The 14 character number designates a specific establishment of the organization designated by the first 9 characters.
     *  2) Check digits: 9th &amp; 14th character respectively<br>
     * Display requirements: The 9 figure code number (SIREN) is written in groups of 3 characters. Example: 784 301 772
     * The 14 figure code number is written in 3 groups of 3 characters and a single group of 5. Example: 784 301 772 00025<br>
     * Example value: 784301772
     *  78430177200025<br>
     * Usage information: 1.1.1 - Changed from FR:SIRET to FR:SIRENE (see ISU Jira ISU-231)<br>
     * 
     * @since code list 1.0.0
     */
    FR_SIRENE("FR:SIRENE", "0002", "FR", "System Information et Repertoire des Entreprise et des Etablissements: SIRENE", "Institut National de la Statistique et des Etudes Economiques, (I.N.S.E.E.)", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0007</code>, scheme ID <code>SE:ORGNR</code><br>
     * Structure of the code: 1) 10 digits. 1st digit = Group number, 2nd - 9th digit = Ordinalnumber; 1st digit, = Group number, 10th digit = Check digit
     *  2) Last digit.<br>
     * Display requirements: Single group of 10 digits.<br>
     * Example value: 2120000787<br>
     * 
     * @since code list 1.0.0
     */
    SE_ORGNR("SE:ORGNR", "0007", "SE", "Organisationsnummer", "The National Tax Board", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0009</code>, scheme ID <code>FR:SIRET</code><br>
     * Structure of the code: 1) 14 digits
     *  2) None<br>
     * Display requirements: In four groups, Groups 1 - 3 = three digits each, Group 4 = five digits<br>
     * Example value: 78430177200025<br>
     * 
     * @since code list 1.1.1
     */
    FR_SIRET("FR:SIRET", "0009", "FR", "SIRET-CODE", "DU PONT DE NEMOURS", Version.parse("1.1.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0037</code>, scheme ID <code>FI:OVT</code><br>
     * Structure of the code: 1) ICD 4 Digits, Organization code (8 digits, 1st-7th digit = number, 8th digit = check number), optional specifier for organisational unit (5 digits)
     * - Example: 00371234567800001
     * - 0037 Country code for Finland (ISO 6523  International Code Designator (ICD) value)
     * - 12345678 Business ID without hyphen 
     * - 00001 Optional specifier for organisation unit (assigned by the organisation itself)<br>
     * Display requirements: None<br>
     * Example value: 0037:00371234567800001
     *  0037:1234567800001<br>
     * Usage information: OVT identifier conforming to standard ISO6523.   
     * - Constant 0037 (Finnish tax administration organisation code)
     * - Finnish local tax ID, 8 characters with initial zero and no hyphen
     * +K4:K5<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-12-31
     */
    @Deprecated(forRemoval = false)
    FI_OVT("FI:OVT", "0037", "FI", "LY-tunnus", "National Board of Taxes, (Verohallitus)", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(12), 31)),

    /**
     * Prefix <code>0060</code>, scheme ID <code>DUNS</code><br>
     * Structure of the code: 1) Eight identification digits and a check digit. A two digit prefix will be added in the future but it will not be used to calculate the check digit.
     *  2) The Organization name is not part of the D-U-N-S number.<br>
     * Display requirements: IIIIIIIIC where all characters are the digits 0, to 9, I = an identification digit and C = the check digit. When the prefix (P) is added the display requirement will be eleven digits, PPIIIIIIIIC.<br>
     * Example value: 812810734<br>
     * Usage information: Check digits were removed to get a bigger number area
     * UPIK search to validate DUNS numbers
     * &quot;D&amp;B Direct 2.0&quot; API available<br>
     * 
     * @since code list 1.0.0
     */
    DUNS("DUNS", "0060", "international", "Data Universal Numbering System (D-U-N-S Number)", "Dun and Bradstreet Ltd", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0088</code>, scheme ID <code>GLN</code><br>
     * Structure of the code: 1) 13 digits including check digits, 2) None<br>
     * Display requirements: None<br>
     * Example value: 1548079098355<br>
     * Usage information: GLN-13 are the only supports supported atm<br>
     * 
     * @since code list 1.0.0
     */
    GLN("GLN", "0088", "international", "Global Location Number", "GS1 GLN", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0096</code>, scheme ID <code>DK:P</code><br>
     * Structure of the code: XXXXXXXXXX
     *  10 digits eg. 1234567890<br>
     * Display requirements: 10 characters, no space or other separator<br>
     * Usage information: A P-number that represents a production unit.<br>
     * 
     * @since code list 1.0.0
     */
    DK_P("DK:P", "0096", "DK", "The Danish Business Authority - P-number (DK:P)", "The Danish Business Authority", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0097</code>, scheme ID <code>IT:FTI</code><br>
     * Structure of the code: Character repertoire: The EDI identifier consists of digits only.
     * The identifier has a fixed length.
     * No separators are required.
     * Structure: [1234567] [123] [1] [12345], min 11- max 16, &lt; &gt;, A B C D
     * A: numerical value (7 digits) assigned by Uffico Provinciale IVA (local branch of Ministry of Finance)
     * B: numerical value a (3 digits) identifying the County
     * C: numerical check digit (1 digit);
     * D: optional numerical value (up to 5 digits used by the registered organization (free part).
     * Check digit computation: The check digit algorithm is the one published in the Gazzetta Ufficiale no 345 of December 29 1976.<br>
     * Display requirements: None<br>
     * Usage information: We couldn't find the checksum algorithm in the scanned PDF of 1976
     * Proposed to deprecate<br>
     * 
     * @since code list 1.0.0
     */
    IT_FTI("IT:FTI", "0097", "IT", "FTI - Ediforum Italia", "FTI - Ediforum Italia", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0106</code>, scheme ID <code>NL:KVK</code><br>
     * Structure of the code: Character repertoire: The EDI identifier consists of digits only.
     * The identifier has a fixed length.
     * No separators are required.
     * Structure: [123] [123456] [123456] [12], 17, &lt; &gt;, A B C D
     * A: numerical value allocated by the RA to the regional subauthority, (3 digits)
     * B: numerical value allocated by the sub-authority to the registered, organization (mandatory part of the identifier; 6 digits)
     * C: numerical value used by the registered organization (free part; 6 digits)
     * D: numerical check digit calculated by the registered organization; (2 digits)
     * Check digit computation, The check digit is modular 97 computed on ABC as one number.<br>
     * Display requirements: None<br>
     * Usage information: Real numbers seem to be 8 characters<br>
     * 
     * @since code list 1.1.2
     */
    NL_KVK("NL:KVK", "0106", "NL", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland (Association of\nChambers of Commerce and Industry in the Netherlands), Scheme", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland", Version.parse("1.1.2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0130</code>, scheme ID <code>EU:NAL</code><br>
     * Structure of the code: 1) ICD 4 digits, 2) None<br>
     * Display requirements: None<br>
     * Usage information: Requested by TICC-10 and TICC-11;
     * in SML since 2019-08-23<br>
     * 
     * @since code list 4
     */
    EU_NAL("EU:NAL", "0130", "international", "Directorates of the European Commission", "European Commission, Information Directorate, Data Transmission Service", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0135</code>, scheme ID <code>IT:SIA</code><br>
     * Structure of the code: First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * Usage information: Propose to deprecate<br>
     * 
     * @since code list 1.0.0
     */
    IT_SIA("IT:SIA", "0135", "IT", "SIA Object Identifiers", "SIA-Societ\u00e0 Interbancaria per l'Automazione S.p.A.", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0142</code>, scheme ID <code>IT:SECETI</code><br>
     * Structure of the code: 1) First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * Usage information: Propose to deprecate<br>
     * 
     * @since code list 1.0.0
     */
    IT_SECETI("IT:SECETI", "0142", "IT", "SECETI Object Identifiers", "Servizi Centralizzati SECETI S.p.A.", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0151</code>, scheme ID <code>AU:ABN</code><br>
     * Structure of the code: The ABN is an 11 digit numeric identifier.
     * The first two digits are check digits calculated using a modulus 89 of the weighted digits.
     * The first digit of the number is guaranteed to be non-zero by adding 10 to the modulus (so the first two digits range from 10 through to 99).
     * For companies the last 9 digits are identical to their existing Australian Company Number (CAN) which includes a single trailing check digit.
     * For other entities the digits are randomly allocated but are guaranteed to fail the CAN check. This allows the Australian Securities and Investment Commission (ASIC) to continue allocating CAN during a transition period. The valid characters are numeric digits 0-9.<br>
     * Display requirements: It is displayed as follows: -, XX XXX XXX XXX<br>
     * Example value: 51 824 753 556<br>
     * Usage information: https://abr.business.gov.au/Help/AbnFormat<br>
     * 
     * @since code list 5
     */
    AU_ABN("AU:ABN", "0151", "AU", "Australian Business Number (ABN) Scheme", "Australian Taxation Office", Version.parse("5"), EPeppolCodeListItemState.ACTIVE, null, null),

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
    CH_UIDB("CH:UIDB", "0183", "CH", "Swiss Unique Business Identification Number (UIDB)", "Swiss Federal Statistical Office (FSO)", Version.parse("5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0184</code>, scheme ID <code>DK:DIGST</code><br>
     * Structure of the code: XXXXXXXX
     *  8 digits eg. 12345678<br>
     * Display requirements: 8 characters, no space or other separator<br>
     * Usage information: A CVR number uniquely identifies a legal entity. CVR stands for “Central Business Register” and contains information about all registered companies in Denmark.<br>
     * 
     * @since code list 1.2.1
     */
    DK_DIGST("DK:DIGST", "0184", "DK", "The Danish Business Authority - CVR-number (DK:CVR)", "The Danish Business Authority", Version.parse("1.2.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0188</code>, scheme ID <code>JP:SST</code><br>
     * Structure of the code: 12-digit fundamental numbers, and a one-digit check numeral put ahead of them
     *  1) Figure of 13 digits
     *  2) Figures from 1 to 9 (Formula to calculate the test number) Formula 9-((n = 1 (Sigma)12( Pn * Qn )) remainder obtained by dividing the 9)
     * Pn : the numeral of the n-th digit of a fundamental number, when counted from the bottom digit.
     * Qn : one when the &quot;n&quot; is an odd number, two when the &quot;n&quot; is an even one<br>
     * Display requirements: None<br>
     * 
     * @since code list 8.1
     */
    JP_SST("JP:SST", "0188", "JP", "Corporate Number of The Social Security and Tax Number System", "National Tax Agency Japan", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0190</code>, scheme ID <code>NL:OINO</code><br>
     * Structure of the code: 20 digits
     *  1) Structure: prefix(8)- identification number(8 or 9)- suffix(3 or 4)
     * - The first 8 digits prefix designates the origin of the identification number
     * - The following 8 or 9 digits designates a unique identification number
     * - The last 3 or 4 (depending on the length of the identification number are filled with “0” digit [note: Up until 2017 numbers are issued where this position contains a serial number.]
     *  2) No check digit<br>
     * Display requirements: In one group of 20 digits<br>
     * Example value: 00000001820029336000<br>
     * Usage information: Get all OINs from http://portaal.digikoppeling.nl/registers/<br>
     * 
     * @since code list 2
     */
    NL_OINO("NL:OINO", "0190", "NL", "Organisatie-identificatienummer (OIN)", "Logius", Version.parse("2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0191</code>, scheme ID <code>EE:CC</code><br>
     * Structure of the code: Always 8-digit number
     *  1) First number in code:
     *  1 – Commercial organisations
     *  7 -- State agencies and local government institutions
     *  8 -- Non-profit associations
     *  9 -- Foundations
     *  2)<br>
     * Display requirements: None<br>
     * Example value: 10137025<br>
     * Usage information: See https://ariregister.rik.ee/index?lang=eng<br>
     * 
     * @since code list 2
     */
    EE_CC("EE:CC", "0191", "EE", "Company code", "Centre of Registers and Information Systems of the Ministry of Justice", Version.parse("2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0192</code>, scheme ID <code>NO:ORG</code><br>
     * Structure of the code: 9 digits
     * The organization number consists of 9 digits where the last digit is a control digit calculated with standard weights, modulus 11. After this, weights 3, 2, 7, 6, 5, 4, 3 and 2 are calculated from the first digit.<br>
     * Display requirements: None<br>
     * Example value: 745707327<br>
     * 
     * @since code list 2
     */
    NO_ORG("NO:ORG", "0192", "NO", "Organisasjonsnummer", "The Br\u00f8nn\u00f8ysund Register Centre", Version.parse("2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0193</code>, scheme ID <code>UBLBE</code><br>
     * Structure of the code: Maximum 50 characters
     *  4 Characters fixed length identifying the type 
     * Maximum 46 characters for the identifier itself<br>
     * Display requirements: None<br>
     * 
     * @since code list 3
     */
    UBLBE("UBLBE", "0193", "BE", "UBL.BE Party Identifier", "UBL.BE", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0195</code>, scheme ID <code>SG:UEN</code><br>
     * Structure of the code: The code comprises of 3 fields
     *  1st field = ICD
     *  2nd field = Special identifier (e.g. country identifier, test identifier, etc)
     *  3rd field = Organisation identifrer No check value<br>
     * Display requirements: None, except all fields are left justified<br>
     * Usage information: https://www.uen.gov.sg/ueninternet/faces/pages/admin/aboutUEN.jspx?_afrLoop=1018044967911865&amp;_afrWindowMode=0&amp;_adf.ctrl-state=fdr4mq9l0_26<br>
     * 
     * @since code list 4
     */
    SG_UEN("SG:UEN", "0195", "SG", "Singapore Nationwide E-Invoice Framework", "lnfocomm Media Development Authority", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0196</code>, scheme ID <code>IS:KTNR</code><br>
     * Structure of the code: 10 digit string using numerical characters from 0 to 9
     * Based on individuals birthdate or legal entities registration date D1D2M1M2Y1Y2R1R2CM D = day, M = month, Y = year, R = random. C = checksum, M = century.
     * Ninth character &quot;C&quot;:
     * C = 11 - ((3xD1 + 2xD2 + 7xM1 + 6xM2 + 5xY1 + 4xY2 + 3xR1 + 2xR2) mod 11)<br>
     * Display requirements: Whole string: nnnnnnnnnn
     * Commonly displayed with hyphen between Y2 and R1, e.g. nnnnnn-nnnn<br>
     * 
     * @since code list 4
     */
    IS_KTNR("IS:KTNR", "0196", "IS", "Icelandic identifier", "Icelandic National Registry", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0198</code>, scheme ID <code>DK:ERST</code><br>
     * Structure of the code: DKXXXXXXXX
     * Two characters (DK) followed by 8 digits eg. DK12345678<br>
     * Display requirements: 10 characters, no space or other separator<br>
     * 
     * @since code list 5
     */
    DK_ERST("DK:ERST", "0198", "DK", "The Danish Business Authority - SE-number (DK:SE)", "The Danish Business Authority", Version.parse("5"), EPeppolCodeListItemState.ACTIVE, null, null),

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
     * The number allocation scheme was further specified in Annex 2 of the Financial Stability Board's third progress note on the Global LEI Initiative on 24 October 2012:
     * • Characters 1 -4: A four character prefix allocated uniquely to each LEI issuer.
     * • Characters 5-6: Two reserved characters set to zero.
     * • Characters 7-18: Entity-specific part of the code generated and assigned by LOUs according to transparent, sound and robust allocation policies.
     * • Characters 19-20: Two check digits as described in the ISO 17442 standards. The check digit scheme follows ISO/IEC 7064 (MOD 97-10) and contributes to avoiding typing errors.
     * See http://www.fsb.org/wp-contenVuploads/r_121024.pdf?page_moved=1
     *  1:)
     *  1. a) Character representations:
     * • n: digits (numeric characters 0 to 9 only);
     * • a: uppercase letters (alpha character, A-Z only, without &quot;special&quot; characters such as blanks, separators or punctuation).
     *  2. b) Length indications:
     * • nn!: fixed length;
     * • nn: maximum length.
     * The format of the LEI shall be
     * -18!an2!n.
     * The LEI consists of 20 characters decomposed as follows: characters (18!an) without separators or &quot;special&quot; characters; the 19th and 20th characters (2!n) shall be the check digit or digits, as calculated from the scheme defined in this International Standard.
     *  2:)
     * The check digits shall be calculated based on the scheme defined in ISO/IEC 7064 (MOD 97-10). See Annex A. The check digits are used to verify the LEI.
     *  1. Convert letters to digits in accordance with the following:
     * A=10B=11 C=12D=13E=14
     * F=15G=16H=17I=18J=19
     * K=20 L = 21 M =22 N =23 0 =24
     * P =25Q = 26 R = 27 S = 28 T = 29
     * U = 30 V = 31 W = 32 X = 33 Y = 34 Z = 35
     *  2. Apply the check character system, MOD 97-10, in accordance with ISO/IEC 7064.
     *  3. If the remainder is 1 (one), the number is valid.
     * 
     * Generating the check digits
     * 
     *  4. Add &quot;00&quot; to the right-hand end of the LEI.
     *  5. Convert alpha characters into numeric characters in accordance with letters in #1.
     *  6. Apply the check character system, MOD 97-10, in accordance with ISO/IEC 7064.<br>
     * Display requirements: The entire 20 character code (including the check digits)<br>
     * 
     * @since code list 5
     */
    LEI("LEI", "0199", "international", "Legal Entity Identifier (LEI)", "As of December 2018, there are 33 LEI issuing organizations in the world.", Version.parse("5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0200</code>, scheme ID <code>LT:LEC</code><br>
     * Structure of the code: 9 (nine) digits
     * Each legal entity's code number is multiplied by 1, 2, 3, 4, 5, 6, 7, 8
     * (weighting) starting from the left-hand side of the code. Calculates the sum of the product, divided by 11. The remainder of the division, which must be less than 10, is the 9th, control, number of the legal entity's code.<br>
     * Display requirements: None<br>
     * Example value: 111963319<br>
     * 
     * @since code list 5
     */
    LT_LEC("LT:LEC", "0200", "LT", "Legal entity code", "State Enterprise Centre of Registers", Version.parse("5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0201</code>, scheme ID <code>IT:CUUO</code><br>
     * Structure of the code: String of 6 alphanumeric characters<br>
     * Display requirements: None<br>
     * Usage information: &quot;Alphanumeric characters&quot;<br>
     * 
     * @since code list 6
     */
    IT_CUUO("IT:CUUO", "0201", "IT", "Codice Univoco Unit\u00e0 Organizzativa iPA", "Agenzia per l\u2019Italia digitale", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0204</code>, scheme ID <code>DE:LWID</code><br>
     * Structure of the code: Up to 46 alphanumeric characters
     * Format: &lt;Up to 12 numeric characters&gt;&quot;-&quot;&lt;up to 30 alphanumeric characters&gt;&quot;–&quot;&lt;2 numeric characters&gt;<br>
     * Display requirements: Whole string<br>
     * Usage information: The fine grained part is optional<br>
     * 
     * @since code list 6
     */
    DE_LWID("DE:LWID", "0204", "DE", "Peppol-Leitweg-ID", "Koordinierungsstelle f\u00fcr IT-Standards (KoSIT)", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0205</code>, scheme ID <code>IT:COD</code><br>
     * Structure of the code: The code is 7 alphanumeric characters<br>
     * Usage information: Participants with this ID CAN NOT be registered in the SMP/SML!<br>
     * 
     * @since code list 8.4
     */
    IT_COD("IT:COD", "0205", "IT", "CODDEST", "Agenzia delle Entrate", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0208</code>, scheme ID <code>BE:EN</code><br>
     * Structure of the code: 10 numeric characters.
     *  1. Enterprise identification number: the first digit has to be &quot;0&quot; or &quot;1&quot;<br>
     * Display requirements: Group of 10 digits<br>
     * 
     * @since code list 7
     */
    BE_EN("BE:EN", "0208", "BE", "Numero d'entreprise / ondernemingsnummer / Unternehmensnummer", "Banque-Carrefour des Entreprises (BCE) / Kruispuntbank van Ondernemingen (KBO) / Zentrale Datenbank der Unternehmen (ZOU)\nService public f\u00e9d\u00e9ral Economie, P.M.E. Classes moyennes et Energie", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0209</code>, scheme ID <code>GS1</code><br>
     * Structure of the code: The code will include an application identifier indicating the type of GS1 identification key used for an object, followed by the value of the key. The string can be completed by other application identifiers indicating attributes related to the GS1 ID key and the value of these attributes related to the identified object.<br>
     * Display requirements: None<br>
     * Example value: 414541000099999325412345678901234567890<br>
     * 
     * @since code list 7.5
     */
    GS1("GS1", "0209", "international", "GS1 identification keys", "GS1", Version.parse("7.5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0210</code>, scheme ID <code>IT:CFI</code><br>
     * Structure of the code: The code is 16 alphanumeric digits for natural persons or 11 numeric digits for legal persons.
     * The code is 16 alphanumeric digits for natural persons, with this structure:
     *  3 digits - Surname according to specific rules
     *  3 digits - Name according to specific rules
     *  2 digits - Last two digits of year of birth
     *  1 digit - Month of birth according to a specific conversion table
     *  2 digits - Day of birth and gender
     *  4 digits - Place of birth in a “Belfiore” code
     *  1 digit - CIN – Control Internal Number
     * The code is 11 numeric digits for legal persons, with this structure:
     *  7 digits - Progressive number
     *  3 digits - Revenue Office ID
     *  1 digit - check
     * Last digit for natural persons - CIN - Control Internal Number
     * Last digit for legal persons - Check<br>
     * 
     * @since code list 7.5
     */
    IT_CFI("IT:CFI", "0210", "IT", "CODICE FISCALE", "Agenzia delle Entrate", Version.parse("7.5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0211</code>, scheme ID <code>IT:IVA</code><br>
     * Structure of the code: The code is &quot;IT&quot; followed by 11 numeric digits
     * First 7 digits is a progressive number
     * The following 3 digits means the province of residence
     * The last digit is a check number, calculated using Luhn's Algorithm<br>
     * 
     * @since code list 7.5
     */
    IT_IVA("IT:IVA", "0211", "IT", "PARTITA IVA", "Agenzia delle Entrate", Version.parse("7.5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0212</code>, scheme ID <code>FI:ORG</code><br>
     * Structure of the code: 9999999-9
     * nine characters
     * last digit after the hyphen is check character<br>
     * Display requirements: Shall be presented as string so that leading zeros have to be present.
     * Hyphen shall be present between last two digits.<br>
     * 
     * @since code list 7.5
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-12-31
     */
    @Deprecated(forRemoval = false)
    FI_ORG("FI:ORG", "0212", "FI", "Finnish Organization Identifier", "State Treasury of Finland / Valtiokonttori", Version.parse("7.5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(12), 31)),

    /**
     * Prefix <code>0213</code>, scheme ID <code>FI:VAT</code><br>
     * Structure of the code: FI99999999
     * ten characters<br>
     * Display requirements: Shall be presented as string so that leading zeros have to be present. Two first characters have always fixed value FI.<br>
     * 
     * @since code list 7.5
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-12-31
     */
    @Deprecated(forRemoval = false)
    FI_VAT("FI:VAT", "0213", "FI", "Finnish Organization Value Add Tax Identifier", "State Treasury of Finland / Valtiokonttori", Version.parse("7.5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(12), 31)),

    /**
     * Prefix <code>0215</code>, scheme ID <code>FI:NSI</code><br>
     * Structure of the code: xxnnnnnnnnnnnnxxxxx
     * xx = operator prefix (example TE)
     * nnnnnnnnnnnnxxxxx = the organisation identification number of the
     * undertaking, which may be a VAT code, or a national organisation
     * number supplemented by the organisation number with an ICD code in front of the organisation number and may be followed by a
     * company-specific specification.
     * 
     * max thirty-five (35) characters<br>
     * Display requirements: None<br>
     * Usage information: An example of Net Service ID: TE003701011385TEST<br>
     * 
     * @since code list 8.1
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-12-31
     */
    @Deprecated(forRemoval = false)
    FI_NSI("FI:NSI", "0215", "FI", "Net service ID", "Tieto Finland Oy", Version.parse("8.1"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(12), 31)),

    /**
     * Prefix <code>0216</code>, scheme ID <code>FI:OVT2</code><br>
     * Structure of the code: 0037NNNNNNNNXXXXX
     * The code consists of three parts: Prefix 0037, the Business ID and an optional suffix.
     * The 0037 in the beginning is a fixed value. The next eight characters are the organization's Finnish Business ID (Business Identity Code) without a hyphen. An optional suffix up to five characters in the end of OVT code can be used, for example, to refer to a department, profit center or location of an organization unit. The organization can choose the values of the suffix itself and the organization must take care of the uniqueness of each ID it uses.
     * 
     * The length of the code is thus 12…17 characters.
     *  0037 = fixed prefix
     * NNNNNNNN = Finnish Business ID (numbers only with possible leading zeros)
     * XXXXX = optional suffix for organizational part ID [A…Z 0…9]<br>
     * Display requirements: None<br>
     * Usage information: An example of OVT code: 003704944842TST01<br>
     * 
     * @since code list 8.1
     */
    FI_OVT2("FI:OVT2", "0216", "FI", "OVTcode", "TIEKE- Tietoyhteiskunnan kehittamiskeskus ry", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0218</code>, scheme ID <code>LV:URN</code><br>
     * Structure of the code: The unified registration number is an 11-digit code where first
     * character can be 4 or 5 or 6 (with the exception of mass media and
     * public-private partnerships whose unified registration number is
     * nine digits long)<br>
     * Display requirements: None<br>
     * 
     * @since code list 8.8
     */
    LV_URN("LV:URN", "0218", "LV", "Unified registration number", "The Register of Enterprises of the Republic of Latvia", Version.parse("8.8"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0221</code>, scheme ID <code>JP:IIN</code><br>
     * Structure of the code: T + 13-digit integer
     * 
     * Figure of 14 digits
     * 
     * Figures from 1 to 9
     * (Formula to calculate the test number)
     * Formula 9 - ((n = 1 (Sigma)12( Pn * Qn )) remainder obtained by dividing the 9)
     * Pn: the numeral of the n-th digit of a fundamental number, when counted from the bottom digit.
     * Qn: one when the &quot;n&quot; is an odd number, two when the &quot;n&quot; is an even one<br>
     * Display requirements: None<br>
     * Usage information: Business entities need to submit application to be registered to issue qualified invoices.<br>
     * 
     * @since code list 8.5
     */
    JP_IIN("JP:IIN", "0221", "JP", "The registered number of the qualified invoice issuer", "Name: National Tax Agency Japan", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0225</code>, scheme ID <code>FR:CTC</code><br>
     * Structure of the code: The identifier is alphanumeric with 130 characters maximum<br>
     * Display requirements: The identification number is a concatenated string of the characters, without spaces and left justified<br>
     * Usage information: The ICD registration will be updated, so that only the characters A-Z, a-z, 0-9 and special characters &quot;-&quot; and &quot;_&quot; will be allowed.<br>
     * 
     * @since code list 9.1
     */
    FR_CTC("FR:CTC", "0225", "FR", "FRCTC Electronic Address", "AIFE (Agence pour l\u2019Informatique Financi\u00e8re de l\u2019Etat)", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0230</code>, scheme ID <code>MY:EIF</code><br>
     * Structure of the code: 1st field = ICD
     *  2nd field = Special Identifier (e.g. country identifier, test identifier, etc)
     *  3rd field = Organisation identifier
     * 
     * No check value<br>
     * Display requirements: None<br>
     * Usage information: For use in electronic messages in accordance to the National e-Invoicing framework on identification of organization<br>
     * 
     * @since code list 8.5
     */
    MY_EIF("MY:EIF", "0230", "MY", "National e-Invoicing Framework", "Malaysia Digital Economy Corporation Sdn Bhd (MDEC)", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0235</code>, scheme ID <code>AE:TIN</code><br>
     * Structure of the code: A TIN comprises of 10 numerical digits:
     * - [1]: UAE country identifier in accordance with GCC agreement. This number must always be &quot;1&quot;
     * - [23456789]: business identifier, generated automatically by the FTA's core tax administration system at the time a person presents themselves for registration
     * - [X]: check digit (mathematically derived by adopting Luhn's
     * algorithm). Luhn's algorithm is as follows:
     *   a. Multiply each ODD-placed digit by 2
     *   b. Multiplying each EVEN-placed digit by 1
     *   b.1. If the product is less than 10, the number is retained as it is
     *   b.2 If the product is 10 or more, the units place and tens place digits is added to get a single value
     *   c. Take the sum of the digits from step b above
     *   d. Mod 10 of the unit's place of the sum of the values in
     * step c above<br>
     * Display requirements: [1][23456789][X]: The first digit from the left must always be &quot;1&quot;<br>
     * 
     * @since code list 9.1
     */
    AE_TIN("AE:TIN", "0235", "AE", "UAE Tax Identification Number (TIN)", "UAE Federal Tax Authority", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0240</code>, scheme ID <code>LU:MAT</code><br>
     * Structure of the code: - 11 characters in total (Arabic numerals only)
     * - 4 first digits = year (may be 0000)
     * - Digits 5-6 = legal form of the legal person
     * Checksum: digit 11<br>
     * Display requirements: None<br>
     * 
     * @since code list 9.1
     */
    LU_MAT("LU:MAT", "0240", "LU", "Register of legal persons", "Centre des technologies de l'information de l'Etat (CTIE)", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>0242</code>, scheme ID <code>SPIS</code><br>
     * Structure of the code: The identifier must follow the following regular expression in a
     * case-insensitive way:
     * [0-9]{6}(-[0-9A-Z_]{3,12}(\.[0-9A-Z\-\._~]{3,24})?)?
     * The identifier consists of one to three parts: number of characters
     * and their significance, if any
     * - The mandatory Main ID - 6 characters
     * - The optional Peppol Use Case ID - 3 to 12 characters
     * - The optional Service Provider Suffix - 3 to 24 characters<br>
     * Display requirements: Must follow the structure of the identifier<br>
     * Usage information: The identifier will have several applications within the Peppol
     * Network.<br>
     * 
     * @since code list 9.3
     */
    SPIS("SPIS", "0242", "international", "OpenPeppol Service Provider Identification Scheme", "OpenPeppol AISBL", Version.parse("9.3"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9901</code>, scheme ID <code>DK:CPR</code><br>
     * Structure of the code: 1) First field: ICD: 4 digits, Second field: sequence of digits<br>
     * Display requirements: None<br>
     * Usage information: Personal identifier<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2023-11-30
     */
    @Deprecated(forRemoval = false)
    DK_CPR("DK:CPR", "9901", "DK", "Danish Ministry of the Interior and Health", "Danish Ministry of the Interior and Health", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2023, Month.of(11), 30)),

    /**
     * Prefix <code>9902</code>, scheme ID <code>DK:CVR</code><br>
     * Example value: 13585628<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2023-11-30
     */
    @Deprecated(forRemoval = false)
    DK_CVR("DK:CVR", "9902", "DK", "The Danish Commerce and Companies Agency", "The Danish Commerce and Companies Agency", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2023, Month.of(11), 30)),

    /**
     * Prefix <code>9904</code>, scheme ID <code>DK:SE</code><br>
     * Example value: DK26769388<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2023-11-30
     */
    @Deprecated(forRemoval = false)
    DK_SE("DK:SE", "9904", "DK", "Danish Ministry of Taxation, Central Customs and Tax Administration", "Danish Ministry of Taxation, Central Customs and Tax Administration", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2023, Month.of(11), 30)),

    /**
     * Prefix <code>9905</code>, scheme ID <code>DK:VANS</code><br>
     * Example value: DK26769388<br>
     * Usage information: Propose to deprecate (only 7 identifiers, only IBM - all look like test)<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2023-11-30
     */
    @Deprecated(forRemoval = false)
    DK_VANS("DK:VANS", "9905", "DK", "Danish VANS providers", "Danish VANS providers", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2023, Month.of(11), 30)),

    /**
     * Prefix <code>9906</code>, scheme ID <code>IT:VAT</code><br>
     * Structure of the code: The code is &quot;IT&quot; followed by 11 numeric digits
     * First 7 digits is a progressive number
     * The following 3 digits means the province of residence
     * The last digit is a check number, calculated using Luhn's Algorithm<br>
     * Example value: IT06363391001<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    IT_VAT("IT:VAT", "9906", "IT", "Ufficio responsabile gestione partite IVA", null, Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24)),

    /**
     * Prefix <code>9907</code>, scheme ID <code>IT:CF</code><br>
     * Example value: RSSBBR69C48F839A<br>
     * Usage information: NOTE: The &quot;CF&quot; is a Fiscal Code that can be &quot;personal&quot; or for a &quot;legal entity&quot;.
     * The CF for legal entities is like the Italian VAT code (IT:VAT)<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    IT_CF("IT:CF", "9907", "IT", "TAX Authority", "TAX Authority", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24)),

    /**
     * Prefix <code>9908</code>, scheme ID <code>NO:ORGNR</code><br>
     * Structure of the code: 9 digits
     * The organization number consists of 9 digits where the last digit is a control digit calculated with standard weights, modulus 11. After this, weights 3, 2, 7, 6, 5, 4, 3 and 2 are calculated from the first digit.<br>
     * Usage information: Use 0192 instead<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    NO_ORGNR("NO:ORGNR", "9908", "NO", "Enhetsregisteret ved Bronnoysundregisterne", "The Br\u00f8nn\u00f8ysund Register Centre", Version.parse("1.0.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.3"), PDTFactory.createLocalDate(2023, Month.of(5), 24)),

    /**
     * Prefix <code>9909</code>, scheme ID <code>NO:VAT</code><br>
     * Example value: 990399123MVA<br>
     * Usage information: Numerical part is the OrgNumber<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v1.1.0 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    NO_VAT("NO:VAT", "9909", "NO", "Norwegian VAT number", "Enhetsregisteret ved Bronnoysundregisterne", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("1.1.0"), null),

    /**
     * Prefix <code>9910</code>, scheme ID <code>HU:VAT</code><br>
     * 
     * @since code list 1.0.0
     */
    HU_VAT("HU:VAT", "9910", "HU", "Hungary VAT number", null, Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9912</code>, scheme ID <code>EU:VAT</code><br>
     * Structure of the code: Must start with the country code<br>
     * Usage information: Proposed to undeprecate; longest known is 18 chars (incl. country code)
     * Deprecated in 1.1.0<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v1.1.0 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    EU_VAT("EU:VAT", "9912", "international", "National ministries of Economy", null, Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("1.1.0"), null),

    /**
     * Prefix <code>9913</code>, scheme ID <code>EU:REID</code><br>
     * Usage information: Proposed to deprecate<br>
     * 
     * @since code list 1.0.0
     */
    EU_REID("EU:REID", "9913", "international", "Business Registers Network", "Business Registers Network", Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9914</code>, scheme ID <code>AT:VAT</code><br>
     * Example value: ATU12345678<br>
     * 
     * @since code list 1.0.0
     */
    AT_VAT("AT:VAT", "9914", "AT", "\u00d6sterreichische Umsatzsteuer-Identifikationsnummer", null, Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9915</code>, scheme ID <code>AT:GOV</code><br>
     * Example value: b<br>
     * Usage information: No entity behind id<br>
     * 
     * @since code list 1.0.0
     */
    AT_GOV("AT:GOV", "9915", "AT", "\u00d6sterreichisches Verwaltungs bzw. Organisationskennzeichen", null, Version.parse("1.0.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9916</code>, scheme ID <code>AT:CID</code><br>
     * 
     * @since code list 1.0.0
     * @deprecated since v1.0.2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    AT_CID("AT:CID", "9916", "AT", "Firmenidentifikationsnummer der Statistik Austria", null, Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("1.0.2"), null),

    /**
     * Prefix <code>9917</code>, scheme ID <code>IS:KT</code><br>
     * Structure of the code: 10 digit string using numerical characters from 0 to 9
     * Based on individuals birthdate or legal entities registration date D1D2M1M2Y1Y2R1R2CM D = day, M = month, Y = year, R = random. C = checksum, M = century.
     * Ninth character &quot;C&quot;:
     * C = 11 - ((3xD1 + 2xD2 + 7xM1 + 6xM2 + 5xY1 + 4xY2 + 3xR1 + 2xR2) mod 11)<br>
     * Display requirements: Whole string: nnnnnnnnnn
     * Commonly displayed with hyphen between Y2 and R1, e.g. nnnnnn-nnnn<br>
     * Usage information: In favour of 0196<br>
     * 
     * @since code list 1.0.0
     * @deprecated since v4 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    IS_KT("IS:KT", "9917", "IS", "Icelandic National Registry", null, Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("4"), null),

    /**
     * Prefix <code>9918</code>, scheme ID <code>IBAN</code><br>
     * Structure of the code: RegEx: [A-Z]{2}[0-9]{2}[A-Z-0-9]{11,30}
     * Checking: https://github.com/arthurdejong/python-stdnum/blob/master/stdnum/iban.py
     * https://github.com/arthurdejong/python-stdnum/blob/master/stdnum/iban.dat<br>
     * 
     * @since code list 1.0.1
     */
    IBAN("IBAN", "9918", "international", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T", Version.parse("1.0.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9919</code>, scheme ID <code>AT:KUR</code><br>
     * Structure of the code: 9 characters in total; letter, number x3, letter, number x3, letter<br>
     * Usage information: Propose to deprecated<br>
     * 
     * @since code list 1.0.2
     */
    AT_KUR("AT:KUR", "9919", "AT", "Kennziffer des Unternehmensregisters", null, Version.parse("1.0.2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9920</code>, scheme ID <code>ES:VAT</code><br>
     * 
     * @since code list 1.0.2
     */
    ES_VAT("ES:VAT", "9920", "ES", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", "Agencia Espa\u00f1ola de Administraci\u00f3n Tributaria", Version.parse("1.0.2"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9921</code>, scheme ID <code>IT:IPA</code><br>
     * Usage information: Propose to deprecate; not used in BIS 3<br>
     * 
     * @since code list 1.1.0
     * @deprecated since v6 - this item should not be used to issue new identifiers!<br>Removed per 2023-11-30
     */
    @Deprecated(forRemoval = false)
    IT_IPA("IT:IPA", "9921", "IT", "Indice delle Pubbliche Amministrazioni", "Indice delle Pubbliche Amministrazioni", Version.parse("1.1.0"), EPeppolCodeListItemState.REMOVED, Version.parse("6"), PDTFactory.createLocalDate(2023, Month.of(11), 30)),

    /**
     * Prefix <code>9922</code>, scheme ID <code>AD:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AD_VAT("AD:VAT", "9922", "AD", "Andorra VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9923</code>, scheme ID <code>AL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    AL_VAT("AL:VAT", "9923", "AL", "Albania VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9924</code>, scheme ID <code>BA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BA_VAT("BA:VAT", "9924", "BA", "Bosnia and Herzegovina VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9925</code>, scheme ID <code>BE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BE_VAT("BE:VAT", "9925", "BE", "Belgium VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9926</code>, scheme ID <code>BG:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    BG_VAT("BG:VAT", "9926", "BG", "Bulgaria VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9927</code>, scheme ID <code>CH:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CH_VAT("CH:VAT", "9927", "CH", "Switzerland VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9928</code>, scheme ID <code>CY:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CY_VAT("CY:VAT", "9928", "CY", "Cyprus VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9929</code>, scheme ID <code>CZ:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    CZ_VAT("CZ:VAT", "9929", "CZ", "Czech Republic VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9930</code>, scheme ID <code>DE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    DE_VAT("DE:VAT", "9930", "DE", "Germany VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9931</code>, scheme ID <code>EE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    EE_VAT("EE:VAT", "9931", "EE", "Estonia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9932</code>, scheme ID <code>GB:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GB_VAT("GB:VAT", "9932", "GB", "United Kingdom VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9933</code>, scheme ID <code>GR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    GR_VAT("GR:VAT", "9933", "GR", "Greece VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9934</code>, scheme ID <code>HR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    HR_VAT("HR:VAT", "9934", "HR", "Croatia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9935</code>, scheme ID <code>IE:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    IE_VAT("IE:VAT", "9935", "IE", "Ireland VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9936</code>, scheme ID <code>LI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LI_VAT("LI:VAT", "9936", "LI", "Liechtenstein VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9937</code>, scheme ID <code>LT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LT_VAT("LT:VAT", "9937", "LT", "Lithuania VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9938</code>, scheme ID <code>LU:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LU_VAT("LU:VAT", "9938", "LU", "Luxemburg VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9939</code>, scheme ID <code>LV:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    LV_VAT("LV:VAT", "9939", "LV", "Latvia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9940</code>, scheme ID <code>MC:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MC_VAT("MC:VAT", "9940", "MC", "Monaco VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9941</code>, scheme ID <code>ME:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    ME_VAT("ME:VAT", "9941", "ME", "Montenegro VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9942</code>, scheme ID <code>MK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MK_VAT("MK:VAT", "9942", "MK", "Macedonia, the former Yugoslav Republic of VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9943</code>, scheme ID <code>MT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    MT_VAT("MT:VAT", "9943", "MT", "Malta VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9944</code>, scheme ID <code>NL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    NL_VAT("NL:VAT", "9944", "NL", "Netherlands VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9945</code>, scheme ID <code>PL:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PL_VAT("PL:VAT", "9945", "PL", "Poland VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9946</code>, scheme ID <code>PT:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    PT_VAT("PT:VAT", "9946", "PT", "Portugal VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9947</code>, scheme ID <code>RO:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RO_VAT("RO:VAT", "9947", "RO", "Romania VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9948</code>, scheme ID <code>RS:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    RS_VAT("RS:VAT", "9948", "RS", "Serbia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9949</code>, scheme ID <code>SI:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SI_VAT("SI:VAT", "9949", "SI", "Slovenia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9950</code>, scheme ID <code>SK:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SK_VAT("SK:VAT", "9950", "SK", "Slovakia VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9951</code>, scheme ID <code>SM:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    SM_VAT("SM:VAT", "9951", "SM", "San Marino VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9952</code>, scheme ID <code>TR:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    TR_VAT("TR:VAT", "9952", "TR", "Turkey VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9953</code>, scheme ID <code>VA:VAT</code><br>
     * 
     * @since code list 1.1.0
     */
    VA_VAT("VA:VAT", "9953", "VA", "Holy See (Vatican City State) VAT number", null, Version.parse("1.1.0"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9954</code>, scheme ID <code>NL:OIN</code><br>
     * Usage information: Deprecated by 0190<br>
     * 
     * @since code list 1.1.3
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    NL_OIN("NL:OIN", "9954", "NL", "Dutch Originator's Identification Number", null, Version.parse("1.1.3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null),

    /**
     * Prefix <code>9955</code>, scheme ID <code>SE:VAT</code><br>
     * 
     * @since code list 1.2.0
     * @deprecated since v8.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-07-31
     */
    @Deprecated(forRemoval = false)
    SE_VAT("SE:VAT", "9955", "SE", "Swedish VAT number", null, Version.parse("1.2.0"), EPeppolCodeListItemState.REMOVED, Version.parse("8.4"), PDTFactory.createLocalDate(2023, Month.of(7), 31)),

    /**
     * Prefix <code>9956</code>, scheme ID <code>BE:CBE</code><br>
     * Structure of the code: Format: 9.999.999.999 - Check: 99 = 97 - (9.999.999.9 modulo 97)<br>
     * Example value: 0899965307<br>
     * 
     * @since code list 1.2.1
     * @deprecated since v7.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-12-31
     */
    @Deprecated(forRemoval = false)
    BE_CBE("BE:CBE", "9956", "BE", "Belgian Crossroad Bank of Enterprise number", "Belgian Crossroad Bank of Enterprises", Version.parse("1.2.1"), EPeppolCodeListItemState.REMOVED, Version.parse("7.4"), PDTFactory.createLocalDate(2023, Month.of(12), 31)),

    /**
     * Prefix <code>9957</code>, scheme ID <code>FR:VAT</code><br>
     * 
     * @since code list 1.2.1
     */
    FR_VAT("FR:VAT", "9957", "FR", "French VAT number", null, Version.parse("1.2.1"), EPeppolCodeListItemState.ACTIVE, null, null),

    /**
     * Prefix <code>9958</code>, scheme ID <code>DE:LID</code><br>
     * Usage information: Replaced by 0204<br>
     * 
     * @since code list 3
     * @deprecated since v6 - this item should not be used to issue new identifiers!<br>Removed per 2023-07-31
     */
    @Deprecated(forRemoval = false)
    DE_LID("DE:LID", "9958", "DE", "Peppol-Leitweg-ID", null, Version.parse("3"), EPeppolCodeListItemState.REMOVED, Version.parse("6"), PDTFactory.createLocalDate(2023, Month.of(7), 31)),

    /**
     * Prefix <code>9959</code>, scheme ID <code>US:EIN</code><br>
     * 
     * @since code list 8.3
     */
    US_EIN("US:EIN", "9959", "US", "US Employer ID Number", null, Version.parse("8.3"), EPeppolCodeListItemState.ACTIVE, null, null);
    public static final String CODE_LIST_VERSION = "9.3";
    public static final int CODE_LIST_ENTRY_COUNT = 100;
    private final String m_sSchemeID;
    private final String m_sISO6523;
    private final String m_sCountryCode;
    private final String m_sSchemeName;
    private final String m_sIssuingAgency;
    private final Version m_aInitialRelease;
    private final EPeppolCodeListItemState m_eState;
    private final Version m_aDeprecationRelease;
    private final LocalDate m_aRemovalDate;

    EPredefinedParticipantIdentifierScheme(@Nonnull @Nonempty final String sSchemeID,
        @Nonnull @Nonempty final String sISO6523,
        @Nonnull @Nonempty final String sCountryCode,
        @Nonnull @Nonempty final String sSchemeName,
        @Nullable final String sIssuingAgency,
        @Nonnull final Version aInitialRelease,
        @Nonnull final EPeppolCodeListItemState eState,
        @Nullable final Version aDeprecationRelease,
        @Nullable final LocalDate aRemovalDate) {
        m_sSchemeID = sSchemeID;
        m_sISO6523 = sISO6523;
        m_sCountryCode = sCountryCode;
        m_sSchemeName = sSchemeName;
        m_sIssuingAgency = sIssuingAgency;
        m_aInitialRelease = aInitialRelease;
        m_eState = eState;
        m_aDeprecationRelease = aDeprecationRelease;
        m_aRemovalDate = aRemovalDate;
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
    public Version getInitialRelease() {
        return m_aInitialRelease;
    }

    @Nonnull
    public EPeppolCodeListItemState getState() {
        return m_eState;
    }

    @Nullable
    public Version getDeprecationRelease() {
        return m_aDeprecationRelease;
    }

    @Nullable
    public LocalDate getRemovalDate() {
        return m_aRemovalDate;
    }
}

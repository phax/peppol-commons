<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        queryBinding='xslt2'>
  <title>OpenPeppol eDEC MLS</title>

  <p id="about">
    This is the Schematron for the Peppol Message Level Status (MLS).

    Author:
      Philip Helger

    History
      v1.0.0 RC1
        2025-03-11, Philip Helger - initial version
  </p>

  <ns prefix="cac" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"/>
  <ns prefix="cbc" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"/>
  <ns prefix="ubl" uri="urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2"/>

  <pattern id="default">
    <let name="cl_rc" value="' AP AB RE '"/>
    <let name="cl_src" value="' BV BW FD SV '"/>

    <rule context="/ubl:ApplicationResponse">
      <assert id="SCH-MLS-01" flag="fatal" test="normalize-space(cbc:CustomizationID) = 'urn:peppol:edec:mls:1.0'"
      >[SCH-MLS-01] The customization ID MUST use the value 'urn:peppol:edec:mls:1.0'</assert>

      <assert id="SCH-MLS-02" flag="fatal" test="normalize-space(cbc:ProfileID) = 'urn:peppol:edec:mls'"
      >[SCH-MLS-02] The profile ID MUST use the value 'urn:peppol:edec:mls'</assert>

      <!-- cbc:ID is mandatory according to the XSD -->
      <assert id="SCH-MLS-03" flag="fatal" test="normalize-space(cbc:ID) != ''"
      >[SCH-MLS-03] The ID MUST NOT be empty</assert>

      <assert id="SCH-MLS-04" flag="fatal" test="exists(cbc:IssueDate)"
      >[SCH-MLS-04] The Issue Date MUST be present</assert>
      <assert id="SCH-MLS-05" flag="fatal" test="string-length(normalize-space(cbc:IssueDate)) = 10"
      >[SCH-MLS-05] The Issue Date MUST NOT contain timezone information</assert>

      <assert id="SCH-MLS-06" flag="fatal" test="exists(cbc:IssueTime)"
      >[SCH-MLS-06] The Issue Time MUST be present</assert>
      <assert id="SCH-MLS-07" flag="fatal" test="matches(normalize-space(cbc:IssueTime), '([+-]\d{2}:\d{2}|Z)$')"
      >[SCH-MLS-07] The Issue Time MUST contain timezone information</assert>
      
      <!-- cac:SenderParty and cac:ReceiverParty are mandatory according to the XSD -->
      <assert id="SCH-MLS-08" flag="fatal" test="exists(cac:SenderParty/cbc:EndpointID)"
      >[SCH-MLS-08] The Sender Party Endpoint ID MUST be present</assert>
      <assert id="SCH-MLS-09" flag="fatal" test="normalize-space(cac:SenderParty/cbc:EndpointID) != ''"
      >[SCH-MLS-09] The Sender Party Endpoint ID MUST NOT be empty</assert>
      <assert id="SCH-MLS-10" flag="fatal" test="exists(cac:SenderParty/cbc:EndpointID/@schemeID)"
      >[SCH-MLS-10] The Sender Party Endpoint ID scheme ID MUST be present</assert>
      <!-- TODO change this to the real SPIS Scheme ID  when available -->
      <assert id="SCH-MLS-11" flag="fatal" test="matches(cac:SenderParty/cbc:EndpointID/@schemeID, '^[0-9]{4}$')"
      >[SCH-MLS-11] The Sender Party Endpoint ID scheme ID MUST be a valid Participant Identifier Scheme</assert>

      <assert id="SCH-MLS-12" flag="fatal" test="exists(cac:ReceiverParty/cbc:EndpointID)"
      >[SCH-MLS-12] The Receiver Party Endpoint ID MUST be present</assert>
      <assert id="SCH-MLS-13" flag="fatal" test="normalize-space(cac:ReceiverParty/cbc:EndpointID) != ''"
      >[SCH-MLS-13] The Receiver Party Endpoint ID MUST NOT be empty</assert>
      <assert id="SCH-MLS-14" flag="fatal" test="exists(cac:ReceiverParty/cbc:EndpointID/@schemeID)"
      >[SCH-MLS-14] The Receiver Party Endpoint ID scheme ID MUST be present</assert>
      <!-- TODO change this to the real SPIS Scheme ID  when available -->
      <assert id="SCH-MLS-15" flag="fatal" test="matches(cac:ReceiverParty/cbc:EndpointID/@schemeID, '^[0-9]{4}$')"
      >[SCH-MLS-15] The Receiver Party Endpoint ID scheme ID MUST be a valid Participant Identifier Scheme</assert>
      
      <assert id="SCH-MLS-16" flag="fatal" test="count(cac:DocumentResponse) = 1"
      >[SCH-MLS-16] Exactly one Document Response MUST be present</assert>
    </rule>

    <rule context="/ubl:ApplicationResponse/cac:DocumentResponse">
      <let name="rc" value="normalize-space(cac:Response/cbc:ResponseCode)" />
      <let name="count_bv" value="count(cac:LineResponse[normalize-space(cac:Response/cac:Status/cbc:StatusReasonCode) = 'BV'])" />
      <let name="count_fd" value="count(cac:LineResponse[normalize-space(cac:Response/cac:Status/cbc:StatusReasonCode) = 'FD'])" />
      <let name="count_sv" value="count(cac:LineResponse[normalize-space(cac:Response/cac:Status/cbc:StatusReasonCode) = 'SV'])" />

      <!-- cac:Response is mandatory according to XSD -->
      <assert id="SCH-MLS-17" flag="fatal" test="exists(cac:Response/cbc:ResponseCode)"
      >[SCH-MLS-17] The Overall Response Code MUST be present</assert>
      <assert id="SCH-MLS-18" flag="fatal" test="contains($cl_rc, concat(' ', $rc, ' '))"
      >[SCH-MLS-18] The Response Code (<value-of select="$rc"/>) MUST be coded according to the code list</assert>

      <assert id="SCH-MLS-19" flag="fatal" test="$rc != 'RE' or
                                                 ( exists(cac:Response/cbc:Description) and
                                                   normalize-space(cac:Response/cbc:Description) != '' )"
      >[SCH-MLS-19] A negative MLS MUST contain a Description</assert>
      
      <!-- cac:DocumentReference is mandatory according to XSD -->
      <assert id="SCH-MLS-20" flag="fatal" test="count(cac:DocumentReference) = 1"
      >[SCH-MLS-20] Exactly one Document Reference MUST be present</assert>

      <!-- cac:DocumentReference/cbc:ID is mandatory according to XSD -->
      <assert id="SCH-MLS-21" flag="fatal" test="normalize-space(cac:DocumentReference/cbc:ID) != ''"
      >[SCH-MLS-21] The Referenced Document ID MUST NOT be empty</assert>
      
      <assert id="SCH-MLS-22" flag="fatal" test="$rc != 'RE' or 
                                                 count(cac:LineResponse) &gt; 0"
      >[SCH-MLS-22] A negative MLS MUST contain at least one Issue</assert>
      <assert id="SCH-MLS-23" flag="fatal" test="every $src in (cac:LineResponse/cac:Response/cac:Status/cbc:StatusReasonCode) satisfies
                                                   contains($cl_src, concat(' ', normalize-space($src), ' '))"
      >[SCH-MLS-23] Each Status Reason Code MUST be coded according to the code list</assert>
      
      <assert id="SCH-MLS-24" flag="fatal" test="$rc != 'RE' or 
                                                 ( $count_bv &gt; 0 or
                                                   $count_fd &gt; 0 or
                                                   $count_sv &gt; 0 )"
      >[SCH-MLS-24] A negative MLS MUST contain at least one Issue with a failure</assert>

      <!-- Note: positive MLS may contain BW only -->
      <assert id="SCH-MLS-25" flag="fatal" test="$rc = 'RE' or 
                                                 ( $count_bv = 0 and
                                                   $count_fd = 0 and
                                                   $count_sv = 0 )"
      >[SCH-MLS-25] A positive MLS MUST NOT contain Issues with failures</assert>
    </rule>
  </pattern>
</schema>

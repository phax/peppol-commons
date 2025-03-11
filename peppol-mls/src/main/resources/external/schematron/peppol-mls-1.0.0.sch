<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        queryBinding='xslt2'>
  <title>OpenPeppol MLS</title>

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
    </rule>

    <rule context="/ubl:ApplicationResponse/cac:DocumentResponse">
    </rule>
  </pattern>
</schema>

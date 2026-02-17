# Introduction

<!-- ph-badge-start -->
[![Maven Central](https://img.shields.io/maven-central/v/com.helger.peppol/peppol-commons-parent-pom)](https://img.shields.io/maven-central/v/com.helger.peppol/peppol-commons-parent-pom)
[![javadoc](https://javadoc.io/badge2/com.helger.peppol/peppol-commons/javadoc.svg)](https://javadoc.io/doc/com.helger.peppol/peppol-commons)
<!-- ph-badge-end -->

This project contains different libraries that are commonly used in the Peppol/eDelivery area:
* [`peppol-id-datatypes`](#peppol-id-datatypes) - the generated JAXB classes for ID handling (since v8.4.0)
* [`peppol-id`](#peppol-id) - the ID data structures (since v7.0.0)
* [`peppol-commons`](#peppol-commons) - the most basic data structures for use with Peppol and BDXR
* [`peppol-testfiles`](#peppol-testfiles) - a set of UBL and SBDH test files
* [`peppol-sbdh`](#peppol-sbdh) - Peppol specific SBDH handling
* [`peppol-sml-client`](#peppol-sml-client) - the Peppol SML client
* [`peppol-smp-datatypes`](#peppol-smp-datatypes) - the Peppol SMP generated JAXB classes (since v8.4.0)
* [`peppol-smp-client`](#peppol-smp-client) - the Peppol SMP and BDXR SMP client
* [`peppol-directory-businesscard`](#peppol-directory-businesscard) - the Peppol Directory Business Card data model (since v9.1.0)
* [`peppol-mlr`](#peppol-mlr) - specific support for the Peppol Message Level Response (MLR) (since v9.1.2)
* [`peppol-mls`](#peppol-mls) - specific support for the Peppol Message Level Status (MLS) (since v10.1.0)
* [`dbnalliance-commons`](#dbnalliance-commons) - contains commons stuff for DBNAlliance support (since v10.2.0)
* [`dbnalliance-xhe`](#dbnalliance-xhe) - specific support for DBNAlliance XHE header (since v9.5.0)
* [`hredelivery-commons`](#hredelivery-commons) - contains commons stuff for HR eDelivery support (since v12.0.2)
  
This project is part of my Peppol solution stack. See https://github.com/phax/peppol for other components and libraries in that area.

These project are used implicitly by e.g. the following projects:
* [phase4](https://github.com/phax/phase4/) - an AS4 implementation that supports the Peppol Network
* [phoss-smp](https://github.com/phax/phoss-smp/) - the phoss SMP server with a management GUI
* [phoss-directory](https://github.com/phax/phoss-directory/) - the phoss Directory for Peppol and TOOP

This project is licensed under the Apache 2.0 license.

## peppol-id-datatypes

Java library with the JAXB generated elements for Peppol ID and Peppol Code List handling.
First created in v8.4.0.

Make sure to run `mvn process-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

## peppol-id

Java library with shared IDs and predefined IDs.
First created in v7.0.0.

## peppol-commons

Java library with shared Peppol components. It contains the basic algorithms. Since v7 this depends on the `peppol-id` submodule.

This project also contains the predefined Peppol Truststores. See https://github.com/phax/peppol-commons/tree/master/peppol-commons/src/main/resources/truststore for all details.

## peppol-sbdh

Simple SBDH handler for the use with Peppol.
It offers the possibility to extract all meta data from an SBDH document as well as 
set all meta data to an SBDH document.

Make sure to run `mvn generate-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

This projects implements the "Envelope specification" as listed on
http://www.peppol.eu/ressource-library/technical-specifications/transport-infrastructure/infrastructure-resources.
The detail document this project refers to can be found at
https://docs.peppol.eu/edelivery/envelope/PEPPOL-EDN-Business-Message-Envelope-1.2.1-2020-03-11.pdf

## peppol-testfiles

A Java library with a lot of UBL and SBDH test files suitable for different scenarios.  

SimpleInvoicing test files are used from https://github.com/SimplerInvoicing/testset

## peppol-sml-client

This project contains the SML client library used by the SMP's to interact with the SML.
This library is usually only used within SMP servers, to communicate the changes to the central SML.

Make sure to run `mvn generate-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

This project contains 2 main classes for talking to the Peppol SML:
  * `ManageServiceMetadataServiceCaller` which is used to change SMP assignments in the SML. This must be called for a new SMP to register it once at the SML.
  * `ManageParticipantIdentifierServiceCaller` which is used to manage the assignment of participants to SMPs. This must be invoked from the SMP server every time a new participant is registered (or an existing one is modified or deleted).
  
Both classes offer the possibility to set an optional custom `SSLSocketFactory` as well as a custom optional `HostnameVerifier`. The implementation of this is in the base class `AbstractSMLClientCaller`.

This project is used by [peppol-smp-server](https://github.com/phax/peppol-smp-server/) the SMP server with a management GUI and flexible backends.

## peppol-smp-datatypes

Java library with the JAXB generated elements for Peppol SMP handling.
First created in v8.4.0.

Make sure to run `mvn process-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

## peppol-smp-client

This project holds the SMP client library used by the Access Points to retrieve service metadata. 
This project supports the Peppol SMP specification, the OASIS BDXR SMP v1 and OASIS BDXR SMP v2 specification. 
This project uses Apache HTTP client to perform the REST lookups on foreign SMPs.

The Peppol SMP specification 1.2.0, mandatory per 1.5.2022, is supported since v8.7.3.
The Peppol SMP specification 1.3.0 to be used per 1.12.2023 does not require any changes and is supported out of the box.

I also provide an OSS [phoss SMP server](https://github.com/phax/phoss-smp) with a nice management GUI.

If you are looking for a standalone query tool, https://github.com/Helger-IT/smp-query-webapp is what you may have a look at.

## peppol-directory-businesscard

This was introduced in v9.1.0

This project holds the different versions of the Peppol Directory Business Card data model, as well as one generic model.

## peppol-mlr

This was introduced in v9.1.2

This project holds utility classes to read and write a Peppol Message Level Response (MLR) as defined in https://docs.peppol.eu/poacc/upgrade-3/profiles/36-mlr/

* Class `PeppolMLRBuilder` can be used to build a Peppol MLR document - with or without line details. For each `LineResponse` the specialized builder class `PeppolMLRLineResponseBuilder` is available.
* Class `PeppolMLRMarshaller` can be used to serialize MLR messages from and to XML. It is based on the [ph-ubl](https://github.com/phax/ph-ubl) marshaller.

See https://github.com/phax/peppol-commons/blob/master/peppol-mlr/src/test/java/com/helger/peppol/mlr/PeppolMLRBuilderTest.java for an example how to use the classes.

## peppol-mls

This was introduced in v10.0.3

This project holds utility classes to read and write a [Peppol Message Level Status](https://docs.peppol.eu/edelivery/specs/mls/v1.0.0/) (MLS) as defined in the member review of the MLS specification.
This is not necessarily the final version of the Peppol MLS and subject to change when the final version is released.

* Class `PeppolMLSBuilder` can be used to build a Peppol MLS document - with or without line details. For each `LineResponse` the specialized builder class `PeppolMLSLineResponseBuilder` is available.
* Class `PeppolMLSMarshaller` can be used to serialize MLS messages from and to XML. It is based on the [ph-ubl](https://github.com/phax/ph-ubl) marshaller.
* Class `PeppolMLSValidator` can be used to run Schematron validation on Peppol MLS messages.

See https://github.com/phax/peppol-commons/blob/master/peppol-mls/src/test/java/com/helger/peppol/mls/PeppolMLSBuilderTest.java for an example how to use the classes.

## dbnalliance-commons

This was introduced in v10.2.0

This project contains shared components for DBNAlliance usage.

## dbnalliance-xhe

This was introduced in v9.5.0

This project holds support for reading and writing DBNAlliance XHE header.
This is the DBNAlliance version of `peppol-sbdh` based on the [ph-xhe](https://github.com/phax/ph-xhe) library.

## hredelivery-commons

This was introduced in v12.0.3

This project contains shared components for Croatian (HR) eDelivery usage in the area as eRacun.

### Configuration

**Configuration resolution (since v8.2.0)**

The SMP client (both Peppol and OASIS BDXR) uses the file `application.properties` for configuration.
The file `smp-client.properties` is also evaluated for backwards-compatibility reasons but with lower priority.

See https://github.com/phax/ph-commons#ph-config for the new resolution logic.

**Configuration resolution (pre v8.2.0)**

The SMP client (both Peppol and OASIS BDXR) uses the file `smp-client.properties` for configuration. The default file resides in the folder `src/main/resources` of this project. You can change the path of the properties file by setting the environment variable `SMP_CLIENT_CONFIG` (since v7.0.7), the system property `peppol.smp.client.properties.path` (since v4.3.5), the system property `smp.client.properties.path` (available as of v4.2.0) to the absolute path of the configuration file (e.g. by specifying `-Dsmp.client.properties.path=/var/www/smpclient.properties` on Java startup). The name of the file does not matter, but if you specify a different properties file please make sure that you also specify an absolute path to e.g. the trust store!

**Configuration properties**

It supports the following properties:
* **`smpclient.truststore.type`**: the type of key store to be used. Possible values are `JKS`, `PKCS12` and `BCFKS`. Defaults to `JKS`.
    * Must be set to `PKCS12` for Peppol G3 CA
* **`smpclient.truststore.path`**: the location of the Peppol trust store (of the specified type) to be used. If this property is not defined, the value defaults to `truststore/complete-truststore.jks`. By default the SMP client supports the following built-in trust stores (in library [peppol-commons](https://github.com/phax/peppol-commons)):
    * `truststore/2025/smp-prod-truststore.p12` - contains the trust certificates for Peppol G3 production only (root, SMP, Directory, SML)
    * `truststore/2025/smp-test-truststore.p12` - contains the trust certificates for Peppol G3 test only (root, SMP, Directory, SML)
    * `truststore/2018/smp-prod-truststore.jks` - contains the trust certificates for Peppol G2 production only (root, SMP, Directory, SML)
    * `truststore/2018/smp-pilot-truststore.jks` - contains the trust certificates for Peppol G2 pilot only (root, SMP, Directory, SML)
* **`smpclient.truststore.password`**: the password to access the trust store. By default the password `peppol` is used. This password is valid for all built-in trust stores mentioned above.

* **`http.proxy.host`** (before v8.7.2: **`http.proxyHost`**): the host name or IP address to be used as a HTTP proxy for **all** hosts. If you need proxy exemptions use the `http.proxy.nonProxyHosts` configuration.
* **`http.proxy.port`** (before v8.7.2: **`http.proxyPort`**): the port of the HTTP proxy. The port must be specified and has no default value. If you need proxy exemptions use the `http.proxy.nonProxyHosts` configuration.
* **`http.proxy.username`** (before v8.7.2: **`http.proxyUsername`**): the username for the HTTP proxy. This property takes only effect if proxy host and proxy port are defined. 
* **`http.proxy.password`** (before v8.7.2: **`http.proxyPassword`**): the password for the HTTP proxy. This property takes only effect if proxy host, proxy port and proxy username are defined.
* **`http.proxy.nonProxyHosts`** (before v8.7.2: **`http.nonProxyHosts`**) (since v6.2.4): A pipe separated list of non-proxy hosts. E.g. `localhost|127.0.0.1`.
* (deprecated since v8.7.2) **`http.useSystemProperties`** (since v5.2.4): if `true` the system properties (=JVM properties) for HTTP configuration are used for setting up the connection. This implies that the properties `http.proxyHost`, `http.proxyPort`, `http.proxyUsername` and `http.proxyPassword` are ineffective! The default value is `false`. Deprecated since v8.7.2
* **`http.connect.timeout.ms`** (since v7.0.4): set the connect timeout in milliseconds. The default value is `5000` meaning 5 seconds.
* **`http.response.timeout.ms`** (since v8.8.0; before **`http.request.timeout.ms`**): set the response timeout in milliseconds. The default value is `10000` meaning 10 seconds.

### Specifying a proxy server

A proxy server can be specified in two ways:
* A single proxy server for **all** hosts - no exemptions. This can be specified in the configuration file for all `SMPClient` instances or per `SMPClient` instance (same for BDXR client - for all clients based on `AbstractGenericSMPClient`).
* A more complex setup based on the JVM system properties (based on https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html). This can also be specified in the configuration file to enable the usage for all `SMPClient` instances or on a per-instance basis.

**Specify a global proxy server**
The SMP client supports a proxy server. By default the proxy specified in the configuration file (see above) is used (since v4.3.0).

Alternatively call the method `setProxy (org.apache.http.HttpHost)` on an `SMPClient` or `SMPClientReadOnly`. This means you can specify the proxy on a per-call basis.
Proxy authentication is available since v5.2.5 by invoking `setProxyCredentials (org.apache.http.auth.Credentials)` on the SMP or BDXR client.
  
### Example usage

Get the endpoint URL for a participant using a special document type and process:

```java
    // The Peppol participant identifier
    final IParticipantIdentifier aPI_AT_Test = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE,
                                                                aPI_AT_Test,
                                                                ESML.DIGIT_TEST);
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                   EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    // Endpoint address should be "https://test.erechnung.gv.at/as4"
    System.out.println ("The Austrian government test AS4 AP that handles invoices in Peppol BIS 3 is located at: " +
                        sEndpointAddress);
```

If you don't need the DNS lookup you can use the URL of the SMP directly (equivalent to the previous example):

```java
    // The Peppol participant identifier
    final IParticipantIdentifier aPI_AT_Test = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URLHelper.getAsURI ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu"));
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                   EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);

    // Endpoint address should be "https://test.erechnung.gv.at/as4"
    System.out.println ("The Austrian government test AS4 AP that handles invoices in Peppol BIS 3 is located at: " +
                        sEndpointAddress);
```

# Building from source

This project is meant to be build by Maven 3.x.
It requires at least Java 17 to be build.
To build simply call `mvn clean install` in the root folder.

When integrating this in your IDE, ensure to run `mvn process-sources` first, so that the automatically generated files are present.
For the subprojects `peppol-id`, `peppol-commons`, `peppol-sbdh` and `peppol-sml-client` add `target/generated-sources/xjc` to your buildpath afterwards.

# Maven usage

Add the following to your pom.xml to use this artifact, replacing `x.y.z` with the latest version number (see below):

```xml
<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-id</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-commons</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-testfiles</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-sbdh</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-sml-client</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-smp-client</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-directory-businesscard</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-mlr</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-mls</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>dbnalliance-commons</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>dbnalliance-xhe</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>hredelivery-commons</artifactId>
  <version>x.y.z</version>
</dependency>
```

Alternatively use the following code in your `dependencyManagement` section to use it as a BOM:

```xml
<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-commons-parent-pom</artifactId>
  <version>x.y.z</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

Note: prior to v8.1.0 the Maven groupId was `com.helger`.

The binary version of this library can be found on https://repo1.maven.org/maven2/com/helger/peppol/ 
They depend on several other libraries so I suggest you are going for the Maven source integration.

# References

* [Peppol Policy for the use of identifiers v4.x](https://docs.peppol.eu/edelivery/)
* [Peppol Business Message Envelope (SBDH) v2.x](https://docs.peppol.eu/edelivery/)

# News and noteworthy

v12.3.9 - 2026-02-17
* If an inbound message contains an invalid `MLS_TO` value, it is no longer an error but only a warning
* The verification of `MLS_TO` value was improved to include the full SPIS regular expression check
* Added new regular expression constant `PeppolIdentifierHelper.REGEX_SEAT_ID`
* Added new regular expression constant `CPeppolMLS.REGEX_SPID`
* Extended `ESML` with the constants for the new insourced Peppol SMLs

v12.3.8 - 2026-02-02
* Deprecated class `PeppolTrustStores.Config2018` in favour of the `PeppolTrustStores.Config2025`
* Added new class `CPeppolCommonsVersion` to track the peppol-commons version used
* Improved the default truststore configuration for SMP client to match the one for AS4

v12.3.7 - 2026-01-23
* Fixed an error in `EPeppolNetwork.getFromSMLInfo` if an `ESML` was passed
* Added new interface `IPeppolServiceDomain`
* Added new interface `IPeppolNetwork`
* Deprecated the possibility to disable charset checks in `PeppolIdentifierHelper`
* The method `PeppolIdentifierFactory.isParticipantIdentifierValueValid` now correctly follows the Peppol Policy for use of Identifiers instead of all the ISO-8859-1 characters. This **may be a breaking change** if you have created something that is not following the rules.

v12.3.6 - 2026-01-13
* Added additional MLS constants in `CPeppolMLS`
* Added new class `SPIDHelper` which adds some Peppol SPIS/SPID helper methods
* Deprecated the possibility to disable the checking for SBDH Country C1 in `PeppolSBDHDataReader`
* Added new method `EPeppolNetwork.getFromESMLOrNull`
* Extended `I(Participant|DocumentType|Process)IdentifierFactory` with methods to create identifiers based on identifiers from other factories

v12.3.5 - 2026-01-02
* Added the "Fina RDC 2025" certificate to the production trusted CAs in `HREDeliveryTrustedCA`

v12.3.4 - 2025-12-29
* Added constructor overload `HRMPSClientReadOnly(URI)`
* Updated to Peppol eDEC Code Lists v9.5
* Adcded new class `CHREDeliveryID`

v12.3.3 - 2025-12-16
* Updated to ph-commons 12.1.1
* Updated the MLS Schematron rules 1.0.1 to match the MLS 1.1.0 Member Review version
* Fixed an issue with certificate parsing on BDXR 2 redirect processing

v12.3.2 - 2025-12-15
* Added new in-between exception class `SMPClientHttpException` which gives access to the returned HTTP Status code
* The class `SMPDNSResolutionException` received a separate "error code" field to make it better differentiatable. See [#67](https://github.com/phax/peppol-commons/issues/67) - thx @wildhai

v12.3.1 - 2025-12-08
* Added "Fina RDC 2025" CA to predefined HR production truststore
* Disabled following of HTTP Redirects in Peppol SMP Client HTTP settings

v12.3.0 - 2025-11-26
* Added new methods to interface `IPeppolParticipantIdentifierScheme` that were already implemented in `EPredefinedParticipantIdentifierScheme`
* Moved class `EBusinessCardVersion` to top-level
* Extracted interface `IPDBusinessCardMarshallerCustomizer` instead of complex `BiConsumer`
* Extracted methods `PDBusinessCardHelper.createDefaultMarshallerCustomizer`
* Extended interface `IPeppolParticipantIdentifierScheme` with methods `getCountryCode` and `getSchemeName`
* Removed the requirement on all BDXL based lookups that the DNS zone must end with a dot. Works without it as well.
* Added method `getServiceGroup` to interface `IBDXRServiceGroupProvider`
* Moved methods from `IBDXRServiceMetadataProvider` to `IBDXRExtendedServiceMetadataProvider` - similar as for Peppol data

v12.2.0 - 2025-11-16
* Updated to ph-commons 12.1.0
* Using JSpecify annotations

v12.1.3 - 2025-11-14
* Fixed loading of `SMLInfo` objects from XML, so that empty URL suffixes are properly maintained
* Extended `EHREDeliveryStage` API

v12.1.2 - 2025-11-13
* Method `SMPClientReadOnly.getAllDocumentTypes` is now capable of handling percent encoded participant IDs in URLs
* Method `BDXRClientReadOnly.getAllDocumentTypes` is now capable of handling percent encoded participant IDs in URLs

v12.1.1 - 2025-11-12
* Class `HREDeliveryNaptrURLProvider` now uses `Meta:SMP` as the NAPTR service to reflect reality
* Peppol MLS Schematron was updated, to fix an issue with lowercase SPIS values
* Added new class `HREDeliveryTrustedCA`
* Added new enum entry `ESMPTransportProfile.TRANSPORT_PROFILE_ERACUN_AS4_V1`
* Renamed enum entry `ESMPTransportProfile.TRANSPORT_PROFILE_DBNA_AS4_v1` to `TRANSPORT_PROFILE_DBNA_AS4_V1`

v12.1.0 - 2025-10-29
* Class `SMLInfo` was modified to be an immutable class and to use a builder instead
* Class `SMLInfo` was modified to make the URL suffixes for managing SMPs and participants customizable (required for HR)

v12.0.5 - 2025-10-26
* SMP client does not emit any warnings on https usage if the date is &ge; 2025-11-01
* Deprecated class `PeppolConfigurableURLProvider` in favour of `PeppolNaptrURLProvider` which works fine
* Added initial predefined truststores for HR eDelivery
* Added new class `HREDeliveryMPSClientHelper`

v12.0.4 - 2025-10-06
* Updated to eDEC Code Lists v9.4

v12.0.3 - 2025-09-18
* Added new CA `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` to all SML related trust stores
* Included preliminary version for HR eDelivery

v12.0.2 - 2025-09-18
* Class `AbstractBDXLURLProvider` now implements `IBDXLURLProvider` directly
* Deprecated methods `ISMPURLProvider.getSMPURLOfParticipant`
* Removed the default method of `IPeppolURLProvider.getSMPURIOfParticipant`

v12.0.1 - 2025-08-28
* Fixed `SMPExtension.equals` and `SMPExtension.hashCode` because of the DOM `Element` member

v12.0.0 - 2025-08-26
* Requires Java 17 as the minimum version
* Updated to ph-commons 12.0.0
* Removed all code marked as deprecated for removal
* Removed the truststore fallback for the SMP Client configuration - so make sure you configured the `smpclient.truststore.*` properties!

v11.0.6 - 2025-08-12
* Replaced the Let's Encrypt R3 CA with the E5 CA in the predefined trust stores

v11.0.5 - 2025-08-12
* Updated to ph-web 10.5.0
* The `SMPHttpClientSettings` now disables the HTTP protocol upgrade by default, which was introduced by Apache HttpClient 5.4

v11.0.4 - 2025-07-20
* Added Peppol PKI G3 certificates into `PeppolTrustedCA`

v11.0.3 - 2025-07-17
* Updated to Peppol eDEC Code Lists v9.3

v11.0.2 - 2025-07-16
* Added support for the new Peppol G3 PKI and preconfifured trust stores (using PKCS12 format)
* Deprecated the `complete-truststore.jks` constant - it will not be taken over for the Peppol G3 PKI!

v11.0.1 - 2025-06-25
* Fixed the determination of the Peppol Certificate subject for a Redrect, if no `X509SubjectName` element is present.

v11.0.0 - 2025-06-02
* Updated to Peppol eDEC Code Lists v9.2
* Removed `Pfuoi420` annotation and all methods that were marked with it. This means, this version only supports Peppol Policy for use of Identifiers v4.3.0 or later.
* Added a `getClone` method to the the NAPTR based URL providers
* Removed all deprecated methods and classes, that were marked for removal in the previous releases
* Removed the MLS builder "Response Code" per Line Response
* The `PeppolMLSLineResponseBuilder` can now deal with multiple responses per "Line Response"

v10.5.1 - 2025-05-29
* The `PeppolMLSBuilder` now correctly requires the issue time with a mandatory time zone

v10.5.0 - 2025-04-15
* Moved the interface `ISMPFollowRedirectCallback` to its own package
* Extended BDXR1 and BDXR2 clients to also support the `ISMPFollowRedirectCallback`

v10.4.3 - 2025-05-14
* Introduced new interface `ISMPFollowRedirectCallback`
* The `ISMPServiceMetadataProvider.getServiceMetadata(OrNull)` methods received an overload with an optional `ISMPFollowRedirectCallback`

v10.4.2 - 2025-05-13
* Deprecated the eB2B specific trust stores

v10.4.1 - 2025-05-11
* Disabled certain SBDH validations if they payload is non-XML
* Added a predefined method in `PeppolSBDHData` to determine if a payload is XML or not.

v10.4.0 - 2025-05-09
* All created NAPTR URLs are now created in lowercase only
* Improved the `PeppolMLSBuilder` so that it can also be filled from existing `ApplicationResponse` objects
* Made Peppol Participant Identifier Value checks more strict, so that they must start with 4 digits and a colon (as in `0000:id`)
* Added support for `MLS_TO` and `MLS_TYPE` SBDH entries

v10.3.2 - 2025-04-29
* Added new class `PeppolConfigurableURLProvider` that allows to switch between CNAME and NAPTR based lookups

v10.3.1 - 2025-04-29
* Deprecated class `PeppolURLProvider`
* Using class `PeppolNaptrURLProvider` instead of class `PeppolURLProvider` to comply to the Peppol Policy for use of Identifiers 4.4.0

v10.3.0 - 2025-04-28 (supports Peppol Policy for use of Identifiers up to 4.4.0)
* Extended Peppol participant identifier value length to 135 for Peppol Policy for use of Identifiers 4.4.0
* Moved annotations`@Pfuoi420`, `@Pfuoi430` and `@Pfuoi440` to the submodule `peppol-id` into package `com.helger.peppolid.peppol`
* This release deprecates all Policy for use of Identifiers 4.2.0 related classes and methods as they become obsolete on May 15th, 2025

v10.2.1 - 2025-04-14
* Renamed all classes `DBNAllianceXHEDocument*` to `DBNAllianceXHEData*`
* Fixed type case error in regards to XHE `PayloadContent`

v10.2.0 - 2025-04-11
* Requires at least ph-commons 11.2.1
* Added new submodule `dbnalliance-commons` containing shared DBNAlliance stuff
* Removed enum `EPeppolCertificateCheckResult` in favour of `ECertificateCheckResult` from ph-security
* Deprecated class `PeppolCAChecker` in favour of `TrustedCAChecker` from ph-security
* Deprecated remaining methods in `PeppolCertificateHelper` in favour of new class `PeppolTrustedCA`
* Deprecated class `PeppolKeyStoreHelper` in favoid of new class `PeppolTrustStores`

v10.1.0 - 2025-03-16
* Added new submodule `peppol-mls` to support the member review version of Peppol MLS
* Removed submodule `peppol-ap-helper` as it is not a top-level project at https://github.com/phax/peppol-ap-support

v10.0.2 - 2025-03-04
* Updated to Peppol eDEC Code Lists v9.1
* Updated to Peppol eDEC Code Lists XSD v2.5

v10.0.1 - 2025-02-26
* Fixed a stupid copy paste in class `DBNAlliancePayload`. See [#59](https://github.com/phax/peppol-commons/issues/59) - thx @taalexlistex
* Fixed predefined Customization ID for DBNAlliance. See [#60](https://github.com/phax/peppol-commons/issues/60) - thx @taalexlistex
* Added support for eB2B AP Production Trust Store as a predefined truststore

v10.0.0 - 2025-02-03
* Updated to ph-commons 11.2.0
* Added new submodule `peppol-ap-helper`
* Removed all deprecated classes, methods and fields
* Moved the following class and methods into ph-commons and removed the local classes and methods
    * Moved `IURLDownloader` to ph-commons
    * Moved `ExpiringObject` to ph-datetime
    * Moved `TrustedCACertificates`, `CRLCache`, `CRLDownloader`, `CRLHelper`, `AbstractRevocationCheckBuilder`, `CertificateRevocationCheckerDefaults`, `ERevocationCheckMode`, `ERevoked`, `IRevokedIndicator` and `RevocationCheckResultCache` to ph-security
    * Moved method `PeppolKeyStoreHelper.getAllTrustedCertificates` to `KeyStoreHelper`
* Renamed `EPeppolSBDHDocumentReadError` to `EPeppolSBDHDataError` and moved to `com.helger.peppol.sbdh`
* Renamed `PeppolSBDHDocumentReader` to `PeppolSBDHDataReader` and moved to `com.helger.peppol.sbdh`
* Renamed `PeppolSBDHDocumentReadException` to `PeppolSBDHDataReadException` and moved to `com.helger.peppol.sbdh`
* Renamed `PeppolSBDHDocumentWriter` to `PeppolSBDHDataWriter` and moved to `com.helger.peppol.sbdh`
* Removed interface `IParticipantIdentifierScheme`
* Renamed class `ParticipantIdentifierSchemeManager` to `PeppolParticipantIdentifierSchemeManager`

v9.7.2 - 2025-01-08
* Updated to OpenPeppol eDEC Code Lists v9.0

v9.7.1 - 2024-12-20
* Re-release of 9.7.0

v9.7.0 - 2024-12-20
* Reworked the Peppol Document Type Identifier data model to handle non-XML syntax specific IDs as well
* Introduced new classes `(I)PeppolGenericDocumentTypeIdentifierParts` to handle the generic identifier parts
* Deprecated classes `(I)BusdoxDocumentTypeIdentifierParts` for removal

v9.6.1 - 2024-12-16
* Added new class `PeppolNaptrURLProvider`
* Fixed the Peppol Directory URL of `EPeppolNetwork.PRODUCTION`    
* Added new method `PeppolSBDHData.areAllFieldsSet (Consumer<String>)`

v9.6.0 - 2024-11-10 (supports Peppol Policy for use of Identifiers up to 4.3.0)
* Requiring ph-commons 11.1.10
* Deprecated methods `SMPClientReadOnly.getCompleteServiceGroup(OrNull)` and `getServiceGroupReferenceList(OrNull)` because the underlying APIs are non-standard
* Extracted methods from `ISMPServiceMetadataProvider` into `ISMPExtendedServiceMetadataProvider`
* Updated the DBNAlliance Pilot domain name
* Moved method `PeppolCertificateHelper.getAllTrustedCertificates` to class `PeppolKeyStoreHelper`
* Added new methods to support Peppol Policy for use of Identifiers 4.3.0
* Added new annotations `@Pfuoi420` and `@Pfuoi430` to hint methods that are specification specific
* Added support for eB2B AP Pilot Trust Store as a predefined truststore
* Renamed class `CertificateRevocationChecker` to `CertificateRevocationCheckerDefaults`
* Made class `RevocationCheckBuilder` a top-level class
* Totally reworked class `PeppolCertificateChecker` to add flexibility and support multiple Peppol CAs
* Added new class `PeppolCAChecker` to support in the verification of Peppol certificates based on CAs
* Renamed class `PeppolRevocationCache` to `RevocationCheckResultCache`
* Added new enum `EPeppolServiceDomain` to be able to provide specific settings for specific service domains
* Added new enum `EPeppolNetwork` to be able to easily differentiate the Peppol Network stages

v9.5.1 - 2024-08-11
* Make sure that wildcard lookups including a `*` in the Customization ID will always fail
* Added additional `SMPClientReadOnly.getWildcardServiceMetadataOrNull` overload
* Extended `ISMPServiceMetadataProvider` and `ISMPServiceGroupProvider` interfaces

v9.5.0 - 2024-07-29
* Updated to dnsjava 3.6 fixing CVE-2024-25638
* Added new submodule `dbnalliance-xhe`. See [#53](https://github.com/phax/peppol-commons/pull/53) - thx @robinsongarciax
* Marked certain `ESMPTransportProfile` entries as "deleted"
* Added new SMP client exception `SMPClientParticipantNotFoundException` to indicate non-existing Service Groups. This was previously covered in `SMPClientNotFoundException`.
* Updated to OpenPeppol eDEC Code Lists v8.9

v9.4.0 - 2024-05-24
* Renamed `IParticipantIdentifierScheme` to `IPeppolParticipantIdentifierScheme`
* Tried to make the usage of `IIdentifierFactory` more customizable
* Made `AbstractRevocationCheckBuilder` and `PeppolRevocationCache` top-level classes
* Extracted class `TrustedCACertificates` to hold a list of certificates
* Deprecated a lot of methods in `PeppolCertificateChecker` in favour of new APIs. Main idea is to simplify multi-certificate handling.

v9.3.6 - 2024-04-24
* Added new class `PeppolLaxIdentifierFactory`
* Added new enum entry `ESMPIdentifierType.PEPPOL_LAX` for SMP handling. See [smp#275](https://github.com/phax/phoss-smp/issues/275).

v9.3.5 - 2024-04-23
* Updated to OpenPeppol eDEC Code Lists v8.8

v9.3.4 - 2024-04-04
* Added new classes to support the DBNA network as well (`DBNAURLProviderSMP`, `EDBNASML`)
* Allowing empty identifier schemes as being present (for DBNA). See `IIdentifier.hasScheme ()`

v9.3.3 - 2024-03-29
* Ensured Java 21 compatibility

v9.3.2 - 2024-03-29
* Added the DBNA AS4 v1 profile to `ESMPTransportProfile`. See [#52](https://github.com/phax/peppol-commons/issues/52)

v9.3.1 - 2024-03-04
* Updated to ph-ubl 9.0.0 (affects only `peppol-mlr`)
* Changed the default checking mode in `CertificateRevocationChecker` from `CRL_BEFORE_OCSP` to `CRL` to avoid any fallback to OCSP
* Improved logging of certificate revocation checks

v9.3.0 - 2024-01-22
* `PredefinedProcessIdentifierManager` now internally works with the full URI encoded process ID and not just the value
* Improved the CRL caching API and unified it with the Peppol certificate revocation checker cache
* Via `CRLCache` constructor a custom `CRLDownloader` can be configured. The default solution uses the Java runtime `HttpURLConnection`.

v9.2.3 - 2024-01-10
* Changed the default checking mode in `CertificateRevocationChecker` from `OCSP` to `CRL_BEFORE_OCSP` due to https://github.com/phax/phase4/issues/124#issuecomment-1884398195

v9.2.2 - 2024-01-08
* Extended `PeppolSBDHDocumentReader` API to allow to disable check for mandatory C1 Country Code via `.setCheckForCountryC1(boolean)`

v9.2.1 - 2024-01-07
* Updated to ph-web 10.1.7
* Added interface method `ISMPServiceMetadataProvider.getWildcardServiceMetadataOrNull`

v9.2.0 - 2024-01-02
* Made `COUNTRY_C1` in SBDH mandatory
* Updated the Peppol default trust stores to remove the old, soon expiring GlobalSign certificates. It was for a previous SML/SMK setup only.
* Moved the logic of `PeppolSBDHDocument` to `PeppolSBDHData`. The old class became deprecated.
* Extracted the Peppol SBDH validation into a separate method `PeppolSBDHDocumentReader.validateData`
* Extracted class `PeppolWildcardSelector` in preparation to support wildcard matching with different algorithms

v9.1.3 - 2023-12-10
* Fixed the "state" in `ESMPTransportProfile` predefined values

v9.1.2 - 2023-12-06
* Added new submodule `peppol-mlr`. It contains data structures to read and write Peppol Message Level Response messages.

v9.1.1 - 2023-11-22
* Added the "peppol-directory-businesscard" to the known submodules in the BOM part of pom.xml
* Updated to OpenPeppol eDEC Code Lists v8.7
* Improved the Peppol Document Type Identifier value syntax checks - for `busdox-docid-qns` and for `peppol-doctype-wildcard`
* Improved the Peppol Process Identifier value syntax checks
* Identifier value checks can now be different per identifier scheme

v9.1.0 - 2023-11-09
* Added new submodule `peppol-directory-businesscard`. It is meant to replace the `phoss-directory-businesscard` one.

v9.0.8 - 2023-08-22
* Updated to OpenPeppol eDEC Code Lists v8.6
* Added `CRLHelper.setCRLOfURL` to manually add an externally downloaded CRL
* Added `CRLHelper.getCRLFromURL` now considers a maximum caching duration (defaults to 1 day)
* Methods `PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID` and `PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID` now require the full identifier, incl. the scheme, to work around potentially new ambiguities
* Extended `CertificateRevocationChecker` builder with `crlCachingDuration(Duration)`
* Added new enum `EPeppolCertificateCheckResult.NOT_CHECKED` for [phase4/#159](https://github.com/phax/phase4/issues/159)

v9.0.7 - 2023-08-01
* Updated to ph-commons 11.1

v9.0.6 - 2023-05-25
* Improved support for the SBDH field `COUNTRY_C1`

v9.0.5 - 2023-05-17
* Added possibility to customize secure validation in `SMPHttpResponseHandlerSigned` (for the Java 17 SHA-1 issue)
* Added possibility to customize SMP Client to enabled or disable the secure validation via `setSecureValidation` (for the Java 17 SHA-1 issue)
* Added support for the SBDH field `COUNTRY_C1` to fulfill the requirements of the Peppol Business Message Envelope 2.0 specification
* Fixed the splitting of Peppol Document Type Identifier values, so that `::` can occur in Customization IDs 

v9.0.4 - 2023-04-30
* Deprecated classes `PeppolSBDHPayload(Reader|Writer|Validator)` and `EPeppolSBDHPayloadType`
* Added new classes `PeppolSBDHPayloadBinaryMarshaller` and `PeppolSBDHPayloadTextMarshaller` instead
* Extended the API of class `PeppolCertificateChecker` to be able to register custom trusted Peppol certificates, e.g. for the French PoC

v9.0.3 - 2023-04-07
* Updated to OpenPeppol eDEC Code Lists v8.5.1

v9.0.2 - 2023-04-05
* Updated to OpenPeppol eDEC Code Lists v8.5
* Moved internal resources to different folders for overall unifications

v9.0.1 - 2023-03-27
* Updated to OpenPeppol eDEC Code Lists v8.4 with XSD 2.1

v9.0.0 - 2023-02-19
* Using Java 11 as the baseline
* Updated to ph-commons 11
* Updated to JAXB 4.0 and JAX-WS 4.0
* Removed deprecated classes and methods

v8.8.6 - 2023-05-25 (backport)
* Improved support for the SBDH field `COUNTRY_C1`

v8.8.5 - 2023-05-16 (backport)
* Added support for the SBDH field `COUNTRY_C1` to fulfill the requirements of the Peppol Business Message Envelope 2.0 specification

v8.8.4 - 2023-01-23
* Updated to OpenPeppol eDEC Code Lists v8.3.1

v8.8.3 - 2023-01-20
* Added the new enum `ESMPTransportProfileState`
* Deprecated `ISMPTransportProfile.isDeprecated()` and added `ISMPTransportProfile.getState()` instead
* Updated to OpenPeppol eDEC Code Lists v8.3

v8.8.2 - 2022-11-24
* Improved error handling when loading an ill-configured SMP client truststore

v8.8.1 - 2022-11-01
* Updated to ph-web 9.7.1
* Extended `SMPJsonResponse` with the versions for BDXR2
* Deprecated SMP transport profiles `busdox-transport-as2-ver1p0` (Peppol AS2 v1) and `busdox-transport-as2-ver2p0` (Peppol AS2 v2)
* Extended `PeppolSBDHDocument` API
* Extended `PeppolSBDHDocumentWriter` API with a "favour speed" option
* Added support for wildcard querying (DDTS) in Peppol SMP client `SMPClientReadOnly.getWildcardServiceMetadataOrNull`

v8.8.0 - 2022-08-17
* Updated to ph-web 9.7.0
* Updated to Apache HttpClient 5.x
* The setters for the trust store in the SMP clients now accept `null`
* Renamed the SMP client configuration property from `http.request.timeout.ms` to `http.response.timeout.ms`


v8.7.6 - 2022-07-12
* Updated to OpenPeppol eDEC Code Lists v8.2

v8.7.5 - 2022-05-25
* Deprecated package `com.helger.peppol.codelist` in submodule `peppol-commons` because the code lists are out of date
* Updated to OpenPeppol eDEC Code Lists v8.1

v8.7.4 - 2022-03-29
* Fixed an error in `BDXR2ClientReadOnly.getServiceMetadata` when using specific identifier classes

v8.7.3 - 2022-03-28
* Added a new generic SMP extension data model that works with Peppol, OASIS BDXR SMP v1 and OASIS BDXR SMP v2.
* Added new class `BPCURLProviderSMP` that works for the BPC market pilot was added
* Renamed the classes `BDXR2Service(Group|Metadata)Marshaller` to `BDXR2MarshallerService(Group|Metadata)` for consistency with BDXR 1
* Added a missing namespace mapping to `BDXR2NamespaceContext`
* Classes `BDXR1NamespaceContext` and `BDXR2NamespaceContext` now offer a static `getInstance` method
* Added support for handling `ServiceActivationDate` and `ServiceExpirationDate` in the Peppol SMP client. See [issue #38](https://github.com/phax/peppol-commons/issues/38) - thx @Florianisme

v8.7.2 - 2022-03-06
* Updated to BouncyCastle 1.70
* Renamed SMP client proxy properties, to match a standard way. The old properties are still supported for backwards compatibility.
    * `http.proxyHost` is now `http.proxy.host`
    * `http.proxyPort` is now `http.proxy.port`
    * `http.proxyUsername` is now `http.proxy.username`
    * `http.proxyPassword` is now `http.proxy.password`
    * `http.nonProxyHosts` is now `http.proxy.nonProxyHosts`
    * `http.useSystemProperties` is now deprecated

v8.7.1 - 2022-01-20
* Extended base interfaces of predefined identifiers to reflect the new layout.

v8.7.0 - 2022-01-20
* Fixed some SonarCloud issues
* Updated to OpenPeppol eDEC Code Lists v8.0 with the new data layout

v8.6.4 - 2021-10-18
* Added new predefined truststores for SMPs: `truststore/2018/smp-pilot-truststore.jks` and `truststore/2018/smp-prod-truststore.jks`
* Improved the error messages when reading Peppol SBDH messages.

v8.6.3 - 2021-09-27
* Verifying all contained `Signature` elements of an SMP response
* Made the JAXB Marshallers of the SMP clients customizable

v8.6.2 - 2021-07-06
* Avoid NPE in `SMPClientReadOnly.getServiceMetadata` if the returned server response is not XML
* Improved debug logging in `SMPHttpResponseHandlerSigned` in case of error
* Added possibility in `TrustStoreBasedX509KeySelector` to specify a date and time, for which the checks should be performed

v8.6.1 - 2021-05-10
* Updated to OpenPeppol eDEC Code Lists v7.5

v8.6.0 - 2021-05-02
* Updated to ph-commons 10.1
* Changed the JAXB binding for XML Schema date time to `XMLOffset*`

v8.5.2 - 2021-04-30
* Updated the truststore by removing the old (expired) Let's Encrypt root certificates.
* Changed the name of the SMP client configuration properties as follows (leaving the old ones in place with a warning message):
    * `truststore.type` was renamed to `smpclient.truststore.type`
    * `truststore.path` was renamed to `smpclient.truststore.path`
    * `truststore.password` was renamed to `smpclient.truststore.password`
* Extracted class `CertificateRevocationChecker` from `PeppolCertificateChecker` to check certificates
* Added support for validating with CRL as well

v8.5.1 - 2021-03-26
* Updated to OpenPeppol eDEC Code Lists v7.4

v8.5.0 - 2021-03-22
* Updated to ph-commons 10
* Changed the JAXB binding for XML Schema date time to `Offset*`

v8.4.1 - 2021-02-19
* Updated the TrustStore to support the new Let's Encrypt root certificates for Peppol Directory

v8.4.0 - 2021-02-16
* Added constants `CODE_LIST_VERSION` and `CODE_LIST_ENTRY_COUNT` to all the Peppol `EPredefined...Identifier` enums
* Extended public API of `SMPJsonResponse`
* Extracted new sub-modules `peppol-id-datatypes` and `peppol-smp-datatypes` with just the generated JAXB classes
* Changed created package from `com.helger.peppolid` to `com.helger.xsds.peppol.id1`
* Changed created package from `com.helger.smpclient.peppol.jaxb` to `com.helger.xsds.peppol.smp1`

v8.3.1 - 2020-12-04
* Started adding support for `peppol-doctype-wildcard`
* Fixed the consistency check of the "TypeVersion" element in Peppol SBDH to also work for non-UBL documents

v8.3.0 - 2020-11-25
* Reworked the revocation checking configuration for Peppol certificates. Now it is more flexible.

v8.2.7 - 2020-11-23
* Improved some debug logging
* Extended `PeppolCertificateChecker` API to be used more flexible
* Extended checks in `PeppolKeyStoreHelper`

v8.2.6 - 2020-11-18
* Updated to ph-web 9.5.0
* Extended `SMPClientReadOnly` and `BDXRClientReadOnly` with further static methods
* Updated to eDEC code lists 7.3

v8.2.5 - 2020-10-02
* Fixed the data model created by `SMPClient.saveServiceGroup` and `BDXRClient.saveServiceGroup` in the overload with only the participant identifier

v8.2.4 - 2020-09-28
* Extended class `PeppolSBDHDocument` to also read the special `BinaryContent` and `TextContent` nodes easily

v8.2.3 - 2020-09-25
* Allow to disable the value checks when reading Peppol SBDH documents

v8.2.2 - 2020-09-17
* Updated to Jakarta JAXB 2.3.3
* Updated to Jakarta JAXWS 2.3.3
* Updated to ph-sbdh 4.1.1
* Removed deprecated class `PeppolDNSResolutionException`

v8.2.1 - 2020-09-14
* 8.2.0 whysoever didn't make it to Maven Central

v8.2.0 - 2020-09-10
* Removed deprecated class `PeppolKeyStoreHelper.Config2010`
* Removed all deprecated and replaced methods
* Improved debug logging in the SMP client
* SMP client configuration is now read from global configuration and is not necessarily constraint to `smp-client.properties` file. The use of the system properties `peppol.smp.client.properties.path`, `smp.client.properties.path` and `SMP_CLIENT_CONFIG` will no longer work. Use the system property `config.file` or the environment variable `CONFIG_FILE` instead (backwards incompatible change)
* Class `PeppolDNSResolutionException` was replaced with `SMPDNSResolutionException`

v8.1.8 - 2020-09-02
* Fixed XML Schema validation of BDXR SMP v2 client

v8.1.7 - 2020-08-30
* Updated to ph-commons 9.4.7
* Using Java 8 date and time classes for JAXB created classes
* Updated to the latest BDMSL WSDL files
* Reworked the DNS URL provider class hierarchy for greater flexibility. New base class is now `ISMPURLProvider`.

v8.1.6 - 2020-08-25
* Added re-usable SMP result to JSON conversion

v8.1.5 - 2020-08-20
* Updated to eDEC Code Lists 7.2

v8.1.4 - 2020-07-15
* Updated to eDEC Code Lists 7.1

v8.1.3 - 2020-07-11
* Fixed a missing whitespace in soapAction for SML registration of participants - [issue](https://github.com/phax/phoss-smp/issues/137) - regression from 8.0.6

v8.1.2 - 2020-07-06
* The creation of the SMP Migration Key was adopted to the effective BDMSL implementation - [issue #37](https://github.com/phax/peppol-commons/issues/37)

v8.1.1 - 2020-06-04
* Made SMP/BDXR client truststore configurable via the code - [issue #35](https://github.com/phax/peppol-commons/issues/35)

v8.1.0 - 2020-05-26
* Changed the Maven groupId to `com.helger.peppol`
* Updated to ph-web 9.3.0 (using ph-dns) (new Maven groupId)
* Updated to ph-xsds 2.3.0 (new Maven groupId)
* Deprecated class `NAPTRResolver` in favour of the new `NaptrResolver`
* `IBDXLURLProvider` takes custom DNS server now as `InetAddress` instead of as `String`

v8.0.7 - 2020-05-06
* Extended predefined document type ID API
* Updated Peppol codelists to contain deprecation status on process identifiers

v8.0.6 - 2020-05-05
* Updated to official Peppol Codelist v7
* Updated to the official Peppol Codelist XSD files
* Made NAPTR record "Service name" comparison case insensitive to honor RFCs

v8.0.5 - 2020-04-22
* Extracted `BDXR1NamespaceContext` and `BDXR2NamespaceContext`
* Made XML Schema validation for SMP clients customizable
* By default the XML Schema validation for querying service metadata is now enabled (breaking change)
* Removed methods deprecated in v7.x

v8.0.4 - 2020-04-16
* Extended the SMP client API with `getAllDocumentTypes` from a service group

v8.0.3 - 2020-04-01 (not a joke)
* Updated to ph-commons 9.4.1

v8.0.2 - 2020-03-03
* Updated to dnsjava 3.0.1
* Added new enum `ESMPIdentifierType`
* Added `ESMPAPIType.getDisplayName()`
* Added the upcoming CEF SMK/SML cipher suites
* A problem with the OCSP checking of SMP certificates was resolved
* Changed the certificate revocation cache to have a timeout of 6 hours

v8.0.1 - 2020-02-16
* Updated to ph-web 9.1.9
* Changed the SMP client HTTP configuration to use the new `HttpClientSettings` class

v8.0.0 - 2020-02-05
* The SMP client configuration can now also be addressed via the `SMP_CLIENT_CONFIG` environment variable
* Removed the Peppol PKI v2 certificates from the complete trust store as they expired in January 2020
* Moved the SMP code from `peppol-commons` to `peppol-smp-client` and adopted package names
    * The `peppol-smp-client` project was total restructured - everything is now under package `com.helger.smpclient`
    * The generated classes for the Peppol SMP XSD were moved from `com.helger.peppol.smp` to `com.helger.smpclient.peppol.jaxb`
* Unified licensing to Apache 2.0    

v7.0.6 - 2020-01-17
* Updated to Peppol Code List v6
* Improved the SMP client API
* Added possibility to customize the "follow redirects" setting of the SMP client 

v7.0.5 - 2019-12-04
* Added the new SMK TLS certificate chain to `sml-truststore.jks` and `complete-truststore.jks`

v7.0.4 - 2019-11-26
* Made SMP client connection timeout and request timeout configurable via the configuration file (see [#33](https://github.com/phax/peppol-commons/issues/33))
* The new Peppol V6 codelist has no dedicated process code list anymore
* Integrated the classes `PeppolCerticateChecker` and `EPeppolCertificateCheckResult` from phase4 and extended them
* The license of submodule `peppol-commons` changed from MPL 2.0 to Apache 2.0 

v7.0.3 - 2019-11-05
* Started adding support for Code Lists v6 (for preview purposes only)
* Removed the explicit certificate from directory.peppol.eu because it is renewed too often (see issue [#31](https://github.com/phax/peppol-commons/issues/31))
* Added new class `PeppolCertificateHelper`
* Added possibility to customize the User Agent of SMP clients
* Added X509 certificates as constants in `PeppolKeyStoreHelper`

v7.0.2 - 2019-08-16
* Using more base types in certain APIs for better interoperability (binary incompatible change)
* Updated to Peppol Code List v5

v7.0.1 - 2019-06-25
* Fixed a naming issues for predefined identifier 0195

v7.0.0 - 2019-06-10
* Started to rework identifier class hierarchies, interfaces and package assignments - the result is incompatible to the 6.x version
* Started integrating OASIS BDXR SMP v2 CSD01 identifiers into the existing structure
* Renamed `EsensURLProvider` to `BDXLURLProvider`
* Using the OASIS BDXR SMP generated code from `ph-xsds-bdxr-smp1` instead of including it manually
* Updated to ph-xsds-* 2.2.3
* Enabled XSD validation in JAXB marshallers by default
* `SMPClient` and `BDXRClient` throw an Exception if the writable REST API parameters don't follow the XSD
* Added new class `BDXR2ClientReadOnly` as SMP client for OASIS BDXR SMP v2 (WS 06) specification
* Added new subprobject `peppol-id` that moved all the `com.helger.peppol.identifier` packages to `com.helger.peppolid` 

v6.2.5 - 2019-05-07
* Fixed Java 12 compatibility

v6.2.4 - 2019-05-05
* SMPClient got the possibility to configure "non-proxy hosts" using the configuration file setting `http.nonProxyHosts`
* Update the Directory trust store to contain the new server certificates for `directory.peppol.eu` and `test-directory.peppol.eu`
* Added support for the new SMP transport profile `busdox-transport-as2-ver2p0` (Peppol AS2 profile v2)
* Peppol SBDH now supports reading UBL 2.2
* Added support for new SBDH text and binary payloads as specified in Peppol Business Message Envelope v1.2

v6.2.3 - 2019-01-18
* Updated to Peppol code lists version 4

v6.2.2 - 2018-11-30
* Made process identifier scheme optional when using `SimpleIdentifierFactory`

v6.2.1 - 2018-11-22
* Updated to ph-commons 9.2.0
* Added a special truststore to access directory.peppol.eu and added this to the complete truststore as well

v6.2.0 - 2018-10-24
* Added SMP transport profile "Peppol AS4 v2"
* Removed all deprecated methods
* Converted a runtime exception to a checked exception in `IPeppolURLProvider.getDNSNameOfParticipant`. The new exception class is called `PeppolDNSResolutionException`.
* Added new class `SMPClientBadResponseException`
* Optional SMP data structure XSD validation can be now enabled (see `AbstractSMPMarshaller.setValidationEnabled` and `AbstractBDXRMarshaller.setValidationEnabled`). By default it is disabled for backwards compatibility.

v6.1.4 - 2018-10-17
* Updated to final code list V3
* The files `PeppolDocumentTypeIdentifier.*` got new attribute names (`name` &rarr; `profilecode`, `doctypeid` &rarr; `id`, added `scheme`)
* The files `PeppolProcessIdentifier.*` got new attribute names (`name` &rarr; `profilecode`, `bisid` is now optional, added `scheme`)
* Automatically created `EPredefinedTransportProfileIdentifier` plus GC and XML for the transport profile list

v6.1.3 - 2018-09-26
* Requires ph-commons 9.1.3
* Updated to Peppol codelists v3 snapshots
* Added support for Peppol Envelope (SBDH) specification v1.1 from https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-11_2018-08-31.pdf

v6.1.2 - 2018-05-15
* Really fixed OSGI ServiceProvider configuration
* Added interface `IBDXLURLProvider`
* Updated the BDMSL service to the latest version 

v6.1.1 - 2018-05-14
* Fixed OSGI ServiceProvider configuration
* Updated to ph-commons 9.1.0

v6.1.0 - 2018-05-04 (#StarWarsDay release)
* Reworked internal Peppol document type identifier representation (API incompatibility; deleted `OpenPeppolDocumentTypeIdentifierParts`)
* The new official Peppol code lists were integrated. Therefore `EPredefinedIdentifierIssuingAgency` was replaced with `EPredefinedParticipantIdentifierScheme` and `IdentifierIssuingAgencyManager` was renamed to `ParticipantIdentifierSchemeManager`
* The document types were removed from the predefined process identifiers
* The shortcut constants of the predefined process identifiers were slightly changed (the final "0" was removed, so `BIS1A_V20` is now `BIS1A_V2`) 

v6.0.4 - 2018-04-13
* Added SSL certificates of SML into default truststore (`truststore/complete-truststore.jks`)
* Replaced truststore SHA-1 files with SHA-256 checksum files

v6.0.3 - 2018-04-11
* Codelist updated (added Estonian Company Code and Billing BIS v3)

v6.0.2 - 2018-03-06
* Added support for SHA256 and SHA512 in `TrustStoreBasedX509KeySelector` for BDXR
* Fixed error in `BDXRClientReadOnly` certificate parsing

v6.0.1 - 2018-02-13
* Removed unused dependency to BouncyCastle
* Added the new OpenPEPPOL root PKI v3 - valid from 2018-2028
* **Important**: the paths to the preconfigured truststore paths have changed - see below for details 

v6.0.0 - 2018-01-05
* Updated to ph-commons 9.0.0
* SMP client can now handle responses with BOM
* Removed legacy project `peppol-sml-client-swing` again
* Added Peppol AS4 transport protocol ID
* All sub-projects previously licensed under EUPL 1.1 or MPL 1.1 (`peppol-commons`, `peppol-smp-client` and `peppol-sml-client`) are now licensed under MPL 2.0

v5.2.7 - 2017-07-21
* Unified identifier handling concerning `""` and `null`

v5.2.6 - 2017-05-30
* Added possibility to deprecate transport profiles

v5.2.5 - 2017-05-25
* Binds to ph-web 8.8.0
* Added possibility to define SMP client proxy credentials(see issue [#13](https://github.com/phax/peppol-commons/issues/13))
* Added legacy project `peppol-sml-client-swing` due to request

v5.2.4 - 2017-01-09
* Binds to ph-commons 8.6.0
* Updated to dnsjava 2.1.8
* Added possibility to define usage of proxy system properties via configuration file (see issue [#9](https://github.com/phax/peppol-commons/issues/9))

v5.2.3 - 2016-12-28
* Updated to BouncyCastle 1.56
* Binds to ph-web 8.7.1
* SMPClient and BDXRClient extended with writing API to create redirects

v5.2.2 - 2016-12-16
* Added possibility to support more proxy settings via system properties (see issue [#9](https://github.com/phax/peppol-commons/issues/9))

v5.2.1 - 2016-11-21
* Added possibility to disable SMP/BDXR client certificate check (see issue [#8](https://github.com/phax/peppol-commons/issues/8))

v5.2.0 - 2016-10-25
* Reworked identifier API to improve case sensitivity handling (based on identifier scheme). The rules per identifier factory:
* BDXR: identifiers based on participant identifier scheme `iso6523-actorid-upis`, document type identifier scheme `bdx-docid-qns` or process identifier scheme `bdx-procid-transport` are treated case **in**sensitive
* Peppol: identifiers based on participant identifier scheme `iso6523-actorid-upis` are treated case **in**sensitive
* simple: all identifiers are handled case sensitive.

v5.1.5 - 2016-10-17
* Improved BDXR extension API

v5.1.4 - 2016-10-12
* Added missing U-NAPTR resolution in EsensURLProvider - thanks to @jerouris for pointing that out

v5.1.3 - 2016-09-15
* Changed Peppol identifier codelist to 1.2.1, because 1.2.2 was based on a misunderstanding

v5.1.2 - 2016-09-09
* Updated to ph-commons 8.5.x
* Updated Peppol identifier codelist to 1.2.2

v5.1.1 - 2016-08-21
* Updated to ph-commons 8.4.x
* Improved identifier handling for BDXR 

v5.1.0 - 2016-08-01

v5.0.1 - 2016-07-26

v5.0.0 - 2016-07-12
* JDK 8 is now required

v4.3.5 - 2016-02-26
* Made the SMP query API more flexible so that e.g. the Peppol Directory BusinessCards can easily be queried; made the SMP Client more configurable.

v4.3.4 - 2016-01-26
* Reduced the maximum migration key length from 100 to 24 (new SMK 3 requirement) and adopted the API to use String instead of UUID

v4.3.3 - 2015-12-11
* Improved the support for BDXR SMP stuff

v4.3.2 - 2015-11-26
* Improved the support for custom SMP transport profiles
* Updated the BDMSL additional services WSDL to the latest 3.1.0 version

v4.3.1 - 2015-10-30
* Added new BDMSL client to access the new "/cipaservice" in a convenient way (class `BDMSLClient` in project `peppol-sml-client`)
* Loosened the regular expression for participant identifier schemes
* Added a new SML participant delete method with SMP ID to work around an SMK 3.0.0 problem 

v4.3.0 - 2015-10-29
* Added support for CIPA BDMSL 3.0 with the wsse:Security header
* Added BDXR SMP client
* Integrated the BDXR SMP classes into peppol-commons
* Updated the BDMSL Service WSDL corresponding to the CIPA 3.0.0 release

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.

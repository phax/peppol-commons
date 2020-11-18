# Introduction

This project contains different libraries that are commonly used in the Peppol area:
  * [`peppol-id`](#peppol-id) - the ID data structures (since v7.0.0)
  * [`peppol-commons`](#peppol-commons) - the most basic data structures for use with Peppol and BDXR
  * [`peppol-testfiles`](#peppol-testfiles) - a set of UBL and SBDH test files
  * [`peppol-sbdh`](#peppol-sbdh) - Peppol specific SBDH handling
  * [`peppol-sml-client`](#peppol-sml-client) - the Peppol SML client
  * [`peppol-smp-client`](#peppol-smp-client) - the Peppol SMP and BDXR SMP client
  
These project are used implicitly by the following projects:
  * [phoss-directory](https://github.com/phax/phoss-directory/) - the phoss Directory for Peppol and TOOP
  * [phoss-smp](https://github.com/phax/phoss-smp/) - the phoss SMP server with a management GUI
  * [as2-peppol](https://github.com/phax/as2-peppol/) - an AS2 client and server for Peppol
  * [phase4](https://github.com/phax/phase4/) - an AS4 implementation that also supports Peppol 
  
And some legacy Peppol projects:
  * [peppol-lime](https://github.com/phax/peppol-lime/) - the LIME server with AS2 support

This project is licensed under the Apache 2.0 license.

## peppol-id

Java library with shared IDs.
First created in version 7.0.0.

Make sure to run `mvn generate-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

## peppol-commons

Java library with shared Peppol components. It contains the basic algorithms. Since v7 this depends on the `peppol-id` submodule.

Make sure to run `mvn generate-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

### Truststore path change in v6.0.1

Old path names (up to and including v6.0.0):
* truststore/global-truststore.jks (production only PKI v2)
* truststore/pilot-truststore.jks (pilot only PKI v2)
* truststore/complete-truststore.jks (production + pilot PKI v2)

New path names (starting from v6.0.1):
* truststore/2018/prod-truststore.jks (production only PKI v3)
* truststore/2018/pilot-truststore.jks (pilot only PKI v3)
* truststore/complete-truststore.jks (production + pilot PKI v2 + v3)

## peppol-sbdh

Simple SBDH handler for the use with Peppol.
It offers the possibility to extract all meta data from an SBDH document as well as 
set all meta data to an SBDH document.

Make sure to run `mvn generate-sources` before using it in the IDE.
The additional code is created in `target/generated-sources/xjc`. 

This projects implements the "Envelope specification" as listed on
http://www.peppol.eu/ressource-library/technical-specifications/transport-infrastructure/infrastructure-resources.
The detail document this project refers to can be found at
https://joinup.ec.europa.eu/svn/peppol/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-100_2014-01-15.pdf

An example on how to use this project can be found in my **[as2-peppol-servlet](https://github.com/phax/as2-peppol)** project which provides a servlet to receive incoming Peppol AS2 messages. Alternatively you may have a look at my **[as2-peppol-client](https://github.com/phax/as2-peppol)** project which is used to send Peppol AS2 messages.

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

## peppol-smp-client

This project holds the SMP client library used by the Access Points to retrieve service metadata. 
This project supports the Peppol SMP specification, the OASIS BDXR SMP v1 and OASIS BDXR SMP v2 specification. 
This project uses Apache HTTP client to perform the REST lookups on foreign SMPs.

I also provide an OSS [phoss SMP server](https://github.com/phax/phoss-smp) with a nice management GUI.

### Configuration

**Configuration resolution**

Note: this is new in v8.2.0.

The SMP client (both Peppol and OASIS BDXR) uses the file `application.properties` for configuration.
The file `smp-client.properties` is also evaluated for backwards-compatibility reasons but with lower priority.

See https://github.com/phax/ph-commons#ph-config for the new resolution logic.

**Configuration resolution (pre v8.2.0)**

The SMP client (both Peppol and OASIS BDXR) uses the file `smp-client.properties` for configuration. The default file resides in the folder `src/main/resources` of this project. You can change the path of the properties file by setting the environment variable `SMP_CLIENT_CONFIG` (since v7.0.7), the system property `peppol.smp.client.properties.path` (since v4.3.5), the system property `smp.client.properties.path` (available as of version 4.2.0) to the absolute path of the configuration file (e.g. by specifying `-Dsmp.client.properties.path=/var/www/smpclient.properties` on Java startup). The name of the file does not matter, but if you specify a different properties file please make sure that you also specify an absolute path to e.g. the trust store!

**Configuration properties**

It supports the following properties:
* **`truststore.type`** (since v6.0.0): the type of key store to be used. Possible values are `JKS` and `PKCS12`. Defaults to `JKS` (which was the implicit default prior to v6).
* **`truststore.path`** (name before v6: **`truststore.location`**): the location of the Peppol trust store (of the specified type) to be used. If this property is not defined, the value defaults to `truststore/complete-truststore.jks`. By default the SMP client supports the following built-in trust stores (in library [peppol-commons](https://github.com/phax/peppol-commons)):
    * `truststore/complete-truststore.jks` - contains the trust certificates for production and pilot (root, AP, SMP, STS)
    * `truststore/global-truststore.jks` - contains the trust certificates for production only (root, AP, SMP, STS)
    * `truststore/pilot-truststore.jks` - contains the trust certificates for pilot only (root, AP, SMP, STS)
* **`truststore.password`**: the password to access the trust store. By default the password `peppol` is used. This password is valid for all built-in trust stores mentioned above.
* **`http.proxyHost`**: the host name or IP address to be used as a HTTP proxy for **all** hosts. If you need proxy exemptions than the `http.useSystemProperties` is the configuration item of choice.
* **`http.proxyPort`**: the port of the HTTP proxy. The port must be specified and has no default value! If you need proxy exemptions than the `http.useSystemProperties` is the configuration item of choice.
* **`http.proxyUsername`** (since v5.2.5): the username for the HTTP proxy. This property takes only effect if proxy host and proxy port are defined. 
* **`http.proxyPassword`** (since v5.2.5): the password for the HTTP proxy. This property takes only effect if proxy host, proxy port and proxy username are defined. 
* **`http.useSystemProperties`** (since v5.2.4): if `true` the system properties (=JVM properties) for HTTP configuration are used for setting up the connection. This implies that the properties `http.proxyHost`, `http.proxyPort`, `http.proxyUsername` and `http.proxyPassword` are ineffective! The default value is `false`.
* **`http.connect.timeout.ms`** (since 7.0.4): set the connection timeout in milliseconds. The default value is `5000` meaning 5 seconds.
* **`http.request.timeout.ms`** (since 7.0.4): set the request timeout in milliseconds. The default value is `10000` meaning 10 seconds.

### Specifying a proxy server

A proxy server can be specified in two ways:
* A single proxy server for **all** hosts - no exemptions. This can be specified in the configuration file for all `SMPClient` instances or per `SMPClient` instance (same for BDXR client - for all clients based on `AbstractGenericSMPClient`).
* A more complex setup based on the JVM system properties (based on https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html). This can also be specified in the configuration file to enable the usage for all `SMPClient` instances or on a per-instance basis.

**Specify a global proxy server**
The SMP client supports a proxy server. By default the proxy specified in the configuration file (see above) is used (since version 4.3.0).

Alternatively call the method `setProxy (org.apache.http.HttpHost)` on an `SMPClient` or `SMPClientReadOnly`. This means you can specify the proxy on a per-call basis.
Proxy authentication is available since v5.2.5 by invoking `setProxyCredentials (org.apache.http.auth.Credentials)` on the SMP or BDXR client.

**Using the JVM system properties**
Since v5.2.2 the method `SMPClient.setUseProxySystemProperties (true)` can be used to enable the usage of the default system properties for HTTP connections (see the section on the configuration file for details). Since v5.2.4 the configuration file property `http.useSystemProperties` can be used to achieve the same without code changes. By enabling the usage of the system properties, the manually set proxy is ignored; if a proxy is manually set after this setting, it disables the usage of the system properties again.
Note: this of course works for both SMP and BDXR client.

Supported system properties are (based on Apache HTTPClient):
  * `ssl.TrustManagerFactory.algorithm`
  * `javax.net.ssl.trustStoreType`
  * `javax.net.ssl.trustStore`
  * `javax.net.ssl.trustStoreProvider`
  * `javax.net.ssl.trustStorePassword`
  * `ssl.KeyManagerFactory.algorithm`
  * `javax.net.ssl.keyStoreType`
  * `javax.net.ssl.keyStore`
  * `javax.net.ssl.keyStoreProvider`
  * `javax.net.ssl.keyStorePassword`
  * `https.protocols`
  * `https.cipherSuites`
  * `http.proxyHost`
  * `http.proxyPort`
  * `http.nonProxyHosts`
  * `http.keepAlive`
  * `http.maxConnections`
  * `http.agent`
  
### Example usage

Get the endpoint URL for a participant using a special document type and process:
```java
    // The Peppol participant identifier
    final PeppolParticipantIdentifier aPI_AT_Test = PeppolParticipantIdentifier.createWithDefaultScheme ("9915:test");

    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE,
                                                                aPI_AT_Test,
                                                                ESML.DIGIT_PRODUCTION);
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                   EPredefinedProcessIdentifier.BIS4A_V20,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    // Endpoint address should be "https://test.erechnung.gv.at/as2"
    System.out.println ("The Austrian government test AS2 AP that handles invoices in BIS4A V2.0 is located at: " +
                        sEndpointAddress);
```

If you don't need the DNS lookup you can use the URL of the SMP directly (equivalent to the previous example):
```java
    // The Peppol participant identifier
    final PeppolParticipantIdentifier aPI_AT_Test = PeppolParticipantIdentifier.createWithDefaultScheme ("9915:test");

    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URLHelper.getAsURI ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.edelivery.tech.ec.europa.eu"));
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                   EPredefinedProcessIdentifier.BIS4A_V20,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);

    // Endpoint address should be "https://test.erechnung.gv.at/as2"
    System.out.println ("The Austrian government test AS2 AP that handles invoices in BIS4A V2.0 is located at: " +
                        sEndpointAddress);
```

# Building from source

This project is meant to be build by Maven 3.x.
It requires at least Java 1.8 to be build.
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

* [Peppol Policy for the use of identifiers 4.0](https://docs.peppol.eu/edelivery/policies/PEPPOL-EDN-Policy-for-use-of-identifiers-4.0-2019-01-28.pdf)
* [Peppol Business Message Envelope (SBDH) 1.2](https://docs.peppol.eu/edelivery/envelope/PEPPOL-EDN-Business-Message-Envelope-1.2-2019-02-01.pdf)

## Obsoleted references

* [Peppol Policy for the use of identifiers 3.2](https://github.com/OpenPEPPOL/edec-specifications/blob/master/releases/policy-identifier/PEPPOL-EDN-Policy-for-use-of-identifiers-3.2-2019-02-01.pdf)

# News and noteworthy

* v8.2.6 - 2020-11-18
    * Updated to ph-web 9.5.0
    * Extended `SMPClientReadOnly` and `BDXRClientReadOnly` with further static methods
    * Updated to eDEC code lists 7.3
* v8.2.5 - 2020-10-02
    * Fixed the data model created by `SMPClient.saveServiceGroup` and `BDXRClient.saveServiceGroup` in the overload with only the participant identifier
* v8.2.4 - 2020-09-28
    * Extended class `PeppolSBDHDocument` to also read the special `BinaryContent` and `TextContent` nodes easily
* v8.2.3 - 2020-09-25
    * Allow to disable the value checks when reading Peppol SBDH documents
* v8.2.2 - 2020-09-17
    * Updated to Jakarta JAXB 2.3.3
    * Updated to Jakarta JAXWS 2.3.3
    * Updated to ph-sbdh 4.1.1
    * Removed deprecated class `PeppolDNSResolutionException`
* v8.2.1 - 2020-09-14
    * 8.2.0 whysoever didn't make it to Maven Central
* v8.2.0 - 2020-09-10
    * Removed deprecated class `PeppolKeyStoreHelper.Config2010`
    * Removed all deprecated and replaced methods
    * Improved debug logging in the SMP client
    * SMP client configuration is now read from global configuration and is not necessarily constraint to `smp-client.properties` file. The use of the system properties `peppol.smp.client.properties.path`, `smp.client.properties.path` and `SMP_CLIENT_CONFIG` will no longer work. Use the system property `config.file` or the environment variable `CONFIG_FILE` instead (backwards incompatible change)
    * Class `PeppolDNSResolutionException` was replaced with `SMPDNSResolutionException`
* v8.1.8 - 2020-09-02
    * Fixed XML Schema validation of BDXR SMP v2 client
* v8.1.7 - 2020-08-30
    * Updated to ph-commons 9.4.7
    * Using Java 8 date and time classes for JAXB created classes
    * Updated to the latest BDMSL WSDL files
    * Reworked the DNS URL provider class hierarchy for greater flexibility. New base class is now `ISMPURLProvider`.
* v8.1.6 - 2020-08-25
    * Added re-usable SMP result to JSON conversion
* v8.1.5 - 2020-08-20
    * Updated to eDEC Code Lists 7.2
* v8.1.4 - 2020-07-15
    * Updated to eDEC Code Lists 7.1
* v8.1.3 - 2020-07-11
    * Fixed a missing whitespace in soapAction for SML registration of participants - [issue](https://github.com/phax/phoss-smp/issues/137) - regression from 8.0.6
* v8.1.2 - 2020-07-06
    * The creation of the SMP Migration Key was adopted to the effective BDMSL implementation - [issue #37](https://github.com/phax/peppol-commons/issues/37)
* v8.1.1 - 2020-06-04
    * Made SMP/BDXR client truststore configurable via the code - [issue #35](https://github.com/phax/peppol-commons/issues/35)
* v8.1.0 - 2020-05-26
    * Changed the Maven groupId to `com.helger.peppol`
    * Updated to ph-web 9.3.0 (using ph-dns) (new Maven groupId)
    * Updated to ph-xsds 2.3.0 (new Maven groupId)
    * Deprecated class `NAPTRResolver` in favour of the new `NaptrResolver`
    * `IBDXLURLProvider` takes custom DNS server now as `InetAddress` instead of as `String`
* v8.0.7 - 2020-05-06
    * Extended predefined document type ID API
    * Updated Peppol codelists to contain deprecation status on process identifiers
* v8.0.6 - 2020-05-05
    * Updated to official Peppol Codelist v7
    * Updated to the official Peppol Codelist XSD files
    * Made NAPTR record "Service name" comparison case insensitive to honor RFCs
* v8.0.5 - 2020-04-22
    * Extracted `BDXR1NamespaceContext` and `BDXR2NamespaceContext`
    * Made XML Schema validation for SMP clients customizable
    * By default the XML Schema validation for querying service metadata is now enabled (breaking change)
    * Removed methods deprecated in v7.x
* v8.0.4 - 2020-04-16
    * Extended the SMP client API with `getAllDocumentTypes` from a service group
* v8.0.3 - 2020-04-01 (not a joke)
    * Updated to ph-commons 9.4.1
* v8.0.2 - 2020-03-03
    * Updated to dnsjava 3.0.1
    * Added new enum `ESMPIdentifierType`
    * Added `ESMPAPIType.getDisplayName()`
    * Added the upcoming CEF SMK/SML cipher suites
    * A problem with the OCSP checking of SMP certificates was resolved
    * Changed the certificate revocation cache to have a timeout of 6 hours
* v8.0.1 - 2020-02-16
    * Updated to ph-web 9.1.9
    * Changed the SMP client HTTP configuration to use the new `HttpClientSettings` class
* v8.0.0 - 2020-02-05
    * The SMP client configuration can now also be addressed via the `SMP_CLIENT_CONFIG` environment variable
    * Removed the Peppol PKI v2 certificates from the complete trust store as they expired in January 2020
    * Moved the SMP code from `peppol-commons` to `peppol-smp-client` and adopted package names
        * The `peppol-smp-client` project was total restructured - everything is now under package `com.helger.smpclient`
        * The generated classes for the Peppol SMP XSD were moved from `com.helger.peppol.smp` to `com.helger.smpclient.peppol.jaxb`
    * Unified licensing to Apache 2.0    
* v7.0.6 - 2020-01-17
    * Updated to Peppol Code List v6
    * Improved the SMP client API
    * Added possibility to customize the "follow redirects" setting of the SMP client 
* v7.0.5 - 2019-12-04
    * Added the new SMK TLS certificate chain to `sml-truststore.jks` and `complete-truststore.jks`
* v7.0.4 - 2019-11-26
    * Made SMP client connection timeout and request timeout configurable via the configuration file (see [#33](https://github.com/phax/peppol-commons/issues/33))
    * The new Peppol V6 codelist has no dedicated process code list anymore
    * Integrated the classes `PeppolCerticateChecker` and `EPeppolCertificateCheckResult` from phase4 and extended them
    * The license of submodule `peppol-commons` changed from MPL 2.0 to Apache 2.0 
* v7.0.3 - 2019-11-05
    * Started adding support for Code Lists v6 (for preview purposes only)
    * Removed the explicit certificate from directory.peppol.eu because it is renewed too often (see issue [#31](https://github.com/phax/peppol-commons/issues/31))
    * Added new class `PeppolCertificateHelper`
    * Added possibility to customize the User Agent of SMP clients
    * Added X509 certificates as constants in `PeppolKeyStoreHelper`
* v7.0.2 - 2019-08-16
    * Using more base types in certain APIs for better interoperability (binary incompatible change)
    * Updated to Peppol Code List v5
* v7.0.1 - 2019-06-25
    * Fixed a naming issues for predefined identifier 0195
* v7.0.0 - 2019-06-10
    * Started to rework identifier class hierarchies, interfaces and package assignments - the result is incompatible to the 6.x version
    * Started integrating OASIS BDXR SMP v2 CSD01 identifiers into the existing structure
    * Renamed `EsensURLProvider` to `BDXLURLProvider`
    * Using the OASIS BDXR SMP generated code from `ph-xsds-bdxr-smp1` instead of including it manually
    * Updated to ph-xsds-* 2.2.3
    * Enabled XSD validation in JAXB marshallers by default
    * `SMPClient` and `BDXRClient` throw an Exception if the writable REST API parameters don't follow the XSD
    * Added new class `BDXR2ClientReadOnly` as SMP client for OASIS BDXR SMP v2 (WS 06) specification
    * Added new subprobject `peppol-id` that moved all the `com.helger.peppol.identifier` packages to `com.helger.peppolid` 
* v6.2.5 - 2019-05-07
    * Fixed Java 12 compatibility
* v6.2.4 - 2019-05-05
    * SMPClient got the possibility to configure "non-proxy hosts" using the configuration file setting `http.nonProxyHosts`
    * Update the Directory trust store to contain the new server certificates for `directory.peppol.eu` and `test-directory.peppol.eu`
    * Added support for the new SMP transport profile `busdox-transport-as2-ver2p0` (Peppol AS2 profile v2)
    * Peppol SBDH now supports reading UBL 2.2
    * Added support for new SBDH text and binary payloads as specified in Peppol Business Message Envelope v1.2
* v6.2.3 - 2019-01-18
    * Updated to Peppol code lists version 4
* v6.2.2 - 2018-11-30
    * Made process identifier scheme optional when using `SimpleIdentifierFactory`
* v6.2.1 - 2018-11-22
    * Updated to ph-commons 9.2.0
    * Added a special truststore to access directory.peppol.eu and added this to the complete truststore as well
* v6.2.0 - 2018-10-24
    * Added SMP transport profile "Peppol AS4 v2"
    * Removed all deprecated methods
    * Converted a runtime exception to a checked exception in `IPeppolURLProvider.getDNSNameOfParticipant`. The new exception class is called `PeppolDNSResolutionException`.
    * Added new class `SMPClientBadResponseException`
    * Optional SMP data structure XSD validation can be now enabled (see `AbstractSMPMarshaller.setValidationEnabled` and `AbstractBDXRMarshaller.setValidationEnabled`). By default it is disabled for backwards compatibility.
* v6.1.4 - 2018-10-17
    * Updated to final code list V3
    * The files `PeppolDocumentTypeIdentifier.*` got new attribute names (`name` &rarr; `profilecode`, `doctypeid` &rarr; `id`, added `scheme`)
    * The files `PeppolProcessIdentifier.*` got new attribute names (`name` &rarr; `profilecode`, `bisid` is now optional, added `scheme`)
    * Automatically created `EPredefinedTransportProfileIdentifier` plus GC and XML for the transport profile list
* v6.1.3 - 2018-09-26
    * Requires ph-commons 9.1.3
    * Updated to Peppol codelists v3 snapshots
    * Added support for Peppol Envelope (SBDH) specification v1.1 from https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-11_2018-08-31.pdf
* v6.1.2 - 2018-05-15
    * Really fixed OSGI ServiceProvider configuration
    * Added interface `IBDXLURLProvider`
    * Updated the BDMSL service to the latest version 
* v6.1.1 - 2018-05-14
    * Fixed OSGI ServiceProvider configuration
    * Updated to ph-commons 9.1.0
* v6.1.0 - 2018-05-04 (#StarWarsDay release)
    * Reworked internal Peppol document type identifier representation (API incompatibility; deleted `OpenPeppolDocumentTypeIdentifierParts`)
    * The new official Peppol code lists were integrated. Therefore `EPredefinedIdentifierIssuingAgency` was replaced with `EPredefinedParticipantIdentifierScheme` and `IdentifierIssuingAgencyManager` was renamed to `ParticipantIdentifierSchemeManager`
    * The document types were removed from the predefined process identifiers
    * The shortcut constants of the predefined process identifiers were slightly changed (the final "0" was removed, so `BIS1A_V20` is now `BIS1A_V2`) 
* v6.0.4 - 2018-04-13
    * Added SSL certificates of SML into default truststore (`truststore/complete-truststore.jks`)
    * Replaced truststore SHA-1 files with SHA-256 checksum files
* v6.0.3 - 2018-04-11
    * Codelist updated (added Estonian Company Code and Billing BIS v3)
* v6.0.2 - 2018-03-06
    * Added support for SHA256 and SHA512 in `TrustStoreBasedX509KeySelector` for BDXR
    * Fixed error in `BDXRClientReadOnly` certificate parsing
* v6.0.1 - 2018-02-13
    * Removed unused dependency to BouncyCastle
    * Added the new OpenPEPPOL root PKI v3 - valid from 2018-2028
    * **Important**: the paths to the preconfigured truststore paths have changed - see below for details 
* v6.0.0 - 2018-01-05
    * Updated to ph-commons 9.0.0
    * SMP client can now handle responses with BOM
    * Removed legacy project `peppol-sml-client-swing` again
    * Added Peppol AS4 transport protocol ID
    * All sub-projects previously licensed under EUPL 1.1 or MPL 1.1 (`peppol-commons`, `peppol-smp-client` and `peppol-sml-client`) are now licensed under MPL 2.0
* v5.2.7 - 2017-07-21
    * Unified identifier handling concerning `""` and `null`
* v5.2.6 - 2017-05-30
    * Added possibility to deprecate transport profiles
* v5.2.5 - 2017-05-25
    * Binds to ph-web 8.8.0
    * Added possibility to define SMP client proxy credentials(see issue [#13](https://github.com/phax/peppol-commons/issues/13))
    * Added legacy project `peppol-sml-client-swing` due to request
* v5.2.4 - 2017-01-09
    * Binds to ph-commons 8.6.0
    * Updated to dnsjava 2.1.8
    * Added possibility to define usage of proxy system properties via configuration file (see issue [#9](https://github.com/phax/peppol-commons/issues/9))
* v5.2.3 - 2016-12-28
    * Updated to BouncyCastle 1.56
    * Binds to ph-web 8.7.1
    * SMPClient and BDXRClient extended with writing API to create redirects
* v5.2.2 - 2016-12-16
    * Added possibility to support more proxy settings via system properties (see issue [#9](https://github.com/phax/peppol-commons/issues/9))
* v5.2.1 - 2016-11-21
    * Added possibility to disable SMP/BDXR client certificate check (see issue [#8](https://github.com/phax/peppol-commons/issues/8))
* v5.2.0 - 2016-10-25
    * Reworked identifier API to improve case sensitivity handling (based on identifier scheme). The rules per identifier factory:
    * BDXR: identifiers based on participant identifier scheme `iso6523-actorid-upis`, document type identifier scheme `bdx-docid-qns` or process identifier scheme `bdx-procid-transport` are treated case **in**sensitive
    * Peppol: identifiers based on participant identifier scheme `iso6523-actorid-upis` are treated case **in**sensitive
    * simple: all identifiers are handled case sensitive.
* v5.1.5 - 2016-10-17
    * Improved BDXR extension API
* v5.1.4 - 2016-10-12
    * Added missing U-NAPTR resolution in EsensURLProvider - thanks to @jerouris for pointing that out
* v5.1.3 - 2016-09-15
    * Changed Peppol identifier codelist to 1.2.1, because 1.2.2 was based on a misunderstanding
* v5.1.2 - 2016-09-09
    * Updated to ph-commons 8.5.x
    * Updated Peppol identifier codelist to 1.2.2
* v5.1.1 - 2016-08-21
    * Updated to ph-commons 8.4.x
    * Improved identifier handling for BDXR 
* v5.1.0 - 2016-08-01
* v5.0.1 - 2016-07-26
* v5.0.0 - 2016-07-12
    * JDK 8 is now required
    * Please check the separate [Update to version 5](UpdateV5.md) page.
* v4.3.5 - 2016-02-26
    * Made the SMP query API more flexible so that e.g. the Peppol Directory BusinessCards can easily be queried; made the SMP Client more configurable.
* v4.3.4 - 2016-01-26
    * Reduced the maximum migration key length from 100 to 24 (new SMK 3 requirement) and adopted the API to use String instead of UUID
* v4.3.3 - 2015-12-11
    * Improved the support for BDXR SMP stuff
* v4.3.2 - 2015-11-26
    * Improved the support for custom SMP transport profiles
    * Updated the BDMSL additional services WSDL to the latest 3.1.0 version
* v4.3.1 - 2015-10-30
    * Added new BDMSL client to access the new "/cipaservice" in a convenient way (class `BDMSLClient` in project `peppol-sml-client`)
    * Loosened the regular expression for participant identifier schemes
    * Added a new SML participant delete method with SMP ID to work around an SMK 3.0.0 problem 
* v4.3.0 - 2015-10-29
    * Added support for CIPA BDMSL 3.0 with the wsse:Security header
    * Added BDXR SMP client
    * Integrated the BDXR SMP classes into peppol-commons
    * Updated the BDMSL Service WSDL corresponding to the CIPA 3.0.0 release

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a> |
Kindly supported by [YourKit Java Profiler](https://www.yourkit.com)
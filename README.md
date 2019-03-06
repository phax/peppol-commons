# Introduction

This project contains different libraries that are commonly used in the PEPPOL area:
  * [`peppol-commons`](#peppol-commons) - the most basic data structures for use with PEPPOL and BDXR
  * [`peppol-testfiles`](#peppol-testfiles) - a set of UBL and SBDH test files
  * [`peppol-sbdh`](#peppol-sbdh) - PEPPOL specific SBDH handling
  * [`peppol-sml-client`](#peppol-sml-client) - the PEPPOL SML client
  * [`peppol-smp-client`](#peppol-smp-client) - the PEPPOL SMP and BDXR SMP client
  
These project are used implicitly by the following projects:
  * [peppol-directory](https://github.com/phax/peppol-directory/) - the PEPPOL Directory (formerly Yellow Pages)
  * [peppol-smp-server](https://github.com/phax/peppol-smp-server/) - the phoss SMP server with a management GUI
  * [as2-peppol-client](https://github.com/phax/as2-peppol-client/) - the AP client library
  * [as2-peppol-server](https://github.com/phax/as2-peppol-server/) - the AP server stub
  
And some legacy PEPPOL projects:
  * [peppol-lime](https://github.com/phax/peppol-lime/) - the LIME server with AS2 support

## Licensing

**Note:** the sub-projects use different licenses (for historic reasons)!

Since (and including) version 6 the sub-projects `peppol-commons`, `peppol-sml-client` and `peppol-smp-client` are licensed under the MPL 2.0.
Before version 6 these 3 sub-projects were licensed under EUPL 1.1 or the MPL 1.1.
The sub-projects `peppol-sbdh` and `peppol-testfiles` are licensed under the Apache 2.0 license (no change in v6).

# News and noteworthy

* v6.2.4 - work in progress
    * SMPClient got the possibility to configure "non-proxy hosts" using the configuration file setting `http.nonProxyHosts`
* v6.2.3 - 2019-01-18
    * Updated to PEPPOL code lists version 4
* v6.2.2 - 2018-11-30
    * Made process identifier scheme optional when using `SimpleIdentifierFactory`
* v6.2.1 - 2018-11-22
    * Updated to ph-commons 9.2.0
    * Added a special truststore to access directory.peppol.eu and added this to the complete truststore as well
* v6.2.0 - 2018-10-24
    * Added SMP transport profile "PEPPOL AS4 v2"
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
    * Updated to PEPPOL codelists v3 snapshots
    * Added support for PEPPOL Envelope (SBDH) specification v1.1 from https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-11_2018-08-31.pdf
* v6.1.2 - 2018-05-15
    * Really fixed OSGI ServiceProvider configuration
    * Added interface `IBDXLURLProvider`
    * Updated the BDMSL service to the latest version 
* v6.1.1 - 2018-05-14
    * Fixed OSGI ServiceProvider configuration
    * Updated to ph-commons 9.1.0
* v6.1.0 - 2018-05-04 (#StarWarsDay release)
    * Reworked internal PEPPOL document type identifier representation (API incompatibility; deleted `OpenPeppolDocumentTypeIdentifierParts`)
    * The new official PEPPOL code lists were integrated. Therefore `EPredefinedIdentifierIssuingAgency` was replaced with `EPredefinedParticipantIdentifierScheme` and `IdentifierIssuingAgencyManager` was renamed to `ParticipantIdentifierSchemeManager`
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
    * Added PEPPOL AS4 transport protocol ID
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
    * PEPPOL: identifiers based on participant identifier scheme `iso6523-actorid-upis` are treated case **in**sensitive
    * simple: all identifiers are handled case sensitive.
* v5.1.5 - 2016-10-17
    * Improved BDXR extension API
* v5.1.4 - 2016-10-12
    * Added missing U-NAPTR resolution in EsensURLProvider - thanks to @jerouris for pointing that out
* v5.1.3 - 2016-09-15
    * Changed PEPPOL identifier codelist to 1.2.1, because 1.2.2 was based on a misunderstanding
* v5.1.2 - 2016-09-09
    * Updated to ph-commons 8.5.x
    * Updated PEPPOL identifier codelist to 1.2.2
* v5.1.1 - 2016-08-21
    * Updated to ph-commons 8.4.x
    * Improved identifier handling for BDXR 
* v5.1.0 - 2016-08-01
* v5.0.1 - 2016-07-26
* v5.0.0 - 2016-07-12
    * JDK 8 is now required
    * Please check the separate [Update to version 5](UpdateV5.md) page.
* v4.3.5 - 2016-02-26
    * Made the SMP query API more flexible so that e.g. the PEPPOL Directory BusinessCards can easily be queried; made the SMP Client more configurable.
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

## peppol-commons
Java library with shared PEPPOL components. It contains the basic algorithms and the handling for the identifiers.

This is based on the cipa-commons-busdox and cipa-peppol-types project version 2.2.3 but without the support for the START protocol.

This project is licensed under the MPL 2.0 license.

### Truststore path change in v6.0.1

Old path names (up to and including v6.0.0):
* truststore/global-truststore.jks (production only PKI v2)
* truststore/pilot-truststore.jks (pilot only PKI v2)
* truststore/complete-truststore.jks (production + pilot PKI v2)

New path names (starting from v6.0.1):
* truststore/2010/prod-truststore.jks (production only PKI v2)
* truststore/2010/pilot-truststore.jks (pilot only PKI v2)
* truststore/2018/prod-truststore.jks (production only PKI v3)
* truststore/2018/pilot-truststore.jks (pilot only PKI v3)
* truststore/complete-truststore.jks (production + pilot PKI v2 + v3)

## peppol-sbdh
Simple SBDH handler for the use with PEPPOL.
It offers the possibility to extract all meta data from an SBDH document as well as 
set all meta data to an SBDH document.

This projects implements the "Envelope specification" as listed on
http://www.peppol.eu/ressource-library/technical-specifications/transport-infrastructure/infrastructure-resources.
The detail document this project refers to can be found at
https://joinup.ec.europa.eu/svn/peppol/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-100_2014-01-15.pdf

An example on how to use this project can be found in my **[as2-peppol-servlet](https://github.com/phax/as2-peppol-servlet)** project which provides a servlet to receive incoming PEPPOL AS2 messages. Alternatively you may have a look at my **[as2-peppol-client](https://github.com/phax/as2-peppol-client)** project which is used to send PEPPOL AS2 messages.

This project is licensed under the Apache 2 License.

## peppol-testfiles
A Java library with a lot of UBL and SBDH test files suitable for different scenarios.  

SimpleInvoicing test files are used from https://github.com/SimplerInvoicing/testset

This project is licensed under the Apache 2 License.

## peppol-sml-client
This project contains the SML client library used by the SMP's to interact with the SML.
It is based on cipa-sml-client-library 2.2.3.
This library is usually only used within SMP servers, to communicate the changes to the central SML.

This project contains 2 main classes for talking to the PEPPOL SML:
  * `ManageServiceMetadataServiceCaller` which is used to change SMP assignments in the SML. This must be called for a new SMP to register it once at the SML.
  * `ManageParticipantIdentifierServiceCaller` which is used to manage the assignment of participants to SMPs. This must be invoked from the SMP server every time a new participant is registered (or an existing one is modified or deleted).
  
Both classes offer the possibility to set an optional custom `SSLSocketFactory` as well as a custom optional `HostnameVerifier`. The implementation of this is in the base class `AbstractSMLClientCaller`.

This project is used by [peppol-smp-server](https://github.com/phax/peppol-smp-server/) the SMP server with a management GUI and flexible backends.

This project is licensed under the MPL 2.0 license.

## peppol-smp-client
This project holds the SMP client library used by the access points to retrieve service metadata. It is based on cipa-smp-client-library 2.2.3. This project also contains the BDXR SMP client (since version 4.3.0). 
This project uses Apache HTTP client to perform the REST lookups on foreign SMPs. The reason to not use the Jersey 1.x client is an incompatibility with Java 8. This means that this version is compliant with Java 1.6+.

I also provide an OSS [phoss SMP server](https://github.com/phax/peppol-smp-server) with a nice management GUI.

This project is licensed under the MPL 2.0 license.

### Configuration
The SMP client (both PEPPOL and BDXR) uses the file `smp-client.properties` for configuration. The default file resides in the folder `src/main/resources` of this project. You can change the path of the properties file by setting the system property `smp.client.properties.path` (available as of version 4.2.0) to the absolute path of the configuration file (e.g. by specifying `-Dsmp.client.properties.path=/var/www/smpclient.properties` on Java startup). The name of the file does not matter, but if you specify a different properties file please make sure that you also specify an absolute path to the trust store!

It supports the following properties:
  * **`truststore.type`** (since v6.0.0): the type of key store to be used. Possible values are `JKS` and `PKCS12`. Defaults to `JKS` (which was the implicit default prior to v6).
  * **`truststore.path`** (name before v6: **`truststore.location`**): the location of the PEPPOL trust store (of the specified type) to be used. If this property is not defined, the value defaults to `truststore/complete-truststore.jks`. By default the SMP client supports the following built-in trust stores (in library [peppol-commons](https://github.com/phax/peppol-commons)):
    * `truststore/complete-truststore.jks` - contains the trust certificates for production and pilot (root, AP, SMP, STS)
    * `truststore/global-truststore.jks` - contains the trust certificates for production only (root, AP, SMP, STS)
    * `truststore/pilot-truststore.jks` - contains the trust certificates for pilot only (root, AP, SMP, STS)
  * **`truststore.password`**: the password to access the trust store. By default the password `peppol` is used. This password is valid for all built-in trust stores mentioned above.
  * **`http.proxyHost`**: the host name or IP address to be used as a HTTP proxy for **all** hosts. If you need proxy exemptions than the `http.useSystemProperties` is the configuration item of choice.
  * **`http.proxyPort`**: the port of the HTTP proxy. The port must be specified and has no default value! If you need proxy exemptions than the `http.useSystemProperties` is the configuration item of choice.
  * **`http.proxyUsername`** (since v5.2.5): the username for the HTTP proxy. This property takes only effect if proxy host and proxy port are defined. 
  * **`http.proxyPassword`** (since v5.2.5): the password for the HTTP proxy. This property takes only effect if proxy host, proxy port and proxy username are defined. 
  * **`http.useSystemProperties`** (since v5.2.4): if `true` the system properties (=JVM properties) for HTTP configuration are used for setting up the connection. This implies that the properties `http.proxyHost`, `http.proxyPort`, `http.proxyUsername` and `http.proxyPassword` are ineffective! The default value is `false`.
  
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
  
### Example usage (V5 and later only)

Get the endpoint URL for a participant using a special document type and process:
```java
    // The PEPPOL participant identifier
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
    // The PEPPOL participant identifier
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

When integrating this in your IDE, ensure to run `mvn generate-sources` first, so that the automatically generated files are present.
For the subprojects `peppol-commons` and `peppol-sml-client` add `target/generated-sources/xjc` to your buildpath afterwards.


# Maven usage
Add the following to your pom.xml to use this artifact:

```xml
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-commons</artifactId>
  <version>6.2.3</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-testfiles</artifactId>
  <version>6.2.3</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-sbdh</artifactId>
  <version>6.2.3</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-sml-client</artifactId>
  <version>6.2.3</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-smp-client</artifactId>
  <version>6.2.3</version>
</dependency>
```

Alternatively use the following code in your `dependencyManagement` section to use it as a BOM:

```xml
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-commons-parent-pom</artifactId>
  <version>6.2.3</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

Note: `peppol-bdxr` was integrated in `peppol-commons` as of version 4.3.0.

The binary version of this library can be found on http://repo2.maven.org/maven2/com/helger/ 
They depend on several other libraries so I suggest you are going for the Maven source integration.

# References

* [PEPPOL Policy for the use of identifiers 3.2](https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/PEPPOL-EDN-Policy-for-use-of-identifiers-3.2-2019-02-01.pdf)
* [PEPPOL Policy for the use of identifiers 4.0](https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/PEPPOL-EDN-Policy-for-use-of-identifiers-4.0-2019-01-28.pdf)
* [PEPPOL Business Message Envelope (SBDH) 1.2](https://github.com/OpenPEPPOL/documentation/blob/master/TransportInfrastructure/PEPPOL-EDN-Business-Message-Envelope-1.2-2019-02-01.pdf)

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a>

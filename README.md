#Introduction
This project contains different libraries that are commonly used in the PEPPOL area:
  * [`peppol-commons`](#peppol-commons) - the most basic data structures for use with PEPPOL and BDXR
  * [`peppol-testfiles`](#peppol-testfiles) - a set of UBL and SBDH test files
  * [`peppol-sbdh`](#peppol-sbdh) - PEPPOL specific SBDH handling
  * [`peppol-sml-client`](#peppol-sml-client) - the PEPPOL SML client
  * [`peppol-smp-client`](#peppol-smp-client) - the PEPPOL SMP and BDXR SMP client
  
These project are used implicitly by the following projects:
  * [peppol-smp-server](https://github.com/phax/peppol-smp-server/) - the SMP server with a management GUI
  * [as2-peppol-client](https://github.com/phax/as2-peppol-client/) - the AP client library
  * [as2-peppol-server](https://github.com/phax/as2-peppol-server/) - the AP server stub
  * [peppol-lime](https://github.com/phax/peppol-lime/) - the LIME server with AS2 support
  * [peppol-directory](https://github.com/phax/peppol-directory/) - the PEPPOL Directory (formerly Yellow Pages) development draft

Note: the sub-projects use different licenses!

## News and noteworthy

  * version 5.0.0
    * JDK 8 is now required
    * Please check the separate [Update to version 5](UpdateV5.md) page.
  * version 4.3.5
    * Made the SMP query API more flexible so that e.g. the PEPPOL Directory BusinessCards can easily be queried; made the SMP Client more configurable.
  * version 4.3.4
    * Reduced the maximum migration key length from 100 to 24 (new SMK 3 requirement) and adopted the API to use String instead of UUID
  * version 4.3.3
    * Improved the support for BDXR SMP stuff
  * version 4.3.2
    * Improved the support for custom SMP transport profiles
    * Updated the BDMSL additional services WSDL to the latest 3.1.0 version
  * version 4.3.1 - 2015-10-30
    * Added new BDMSL client to access the new "/cipaservice" in a convenient way (class `BDMSLClient` in project `peppol-sml-client`)
    * Loosened the regular expression for participant identifier schemes
    * Added a new SML participant delete method with SMP ID to work around an SMK 3.0.0 problem 
  * version 4.3.0 - 2015-10-29
    * Added support for CIPA BDMSL 3.0 with the wsse:Security header
    * Added BDXR SMP client
    * Integrated the BDXR SMP classes into peppol-commons
    * Updated the BDMSL Service WSDL corresponding to the CIPA 3.0.0 release

## peppol-commons
Java library with shared PEPPOL components. It contains the basic algorithms and the handling for the identifiers.

This is based on the cipa-commons-busdox and cipa-peppol-types project version 2.2.3 but without the support for the START protocol.

Versions <= 3.1.3 are compatible with ph-commons < 6.0.
Versions >= 4.0.0 are compatible with ph-commons >= 6.0.

This project is licensed under EUPL 1.1 or MPL 1.1 - like CIPA e-Delivery.

## peppol-sbdh
Simple SBDH handler for the use with PEPPOL.
It offers the possibility to extract all meta data from an SBDH document as well as 
set all meta data to an SBDH document.

This projects implements the "Envelope specification" as listed on
http://www.peppol.eu/ressource-library/technical-specifications/transport-infrastructure/infrastructure-resources.
The detail document this project refers to can be found at
https://joinup.ec.europa.eu/svn/peppol/TransportInfrastructure/ICT-Transport-OpenPEPPOL-Envelope_Specification-100_2014-01-15.pdf

From a technical point of view, this library can be used with Java 1.6 or higher.

An example on how to use this project can be found in my **[as2-peppol-servlet](https://github.com/phax/as2-peppol-servlet)** project which provides a servlet to receive incoming PEPPOL AS2 messages. Alternatively you may have a look at my **[as2-peppol-client](https://github.com/phax/as2-peppol-client)** project which is used to send PEPPOL AS2 messages.

Versions <= 1.0.1 are compatible with ph-commons < 6.0.
Versions >= 2.0.0 are compatible with ph-commons >= 6.0.

This project is licensed under the Apache 2 License.

## peppol-testfiles
A Java library with a lot of UBL and SBDH test files suitable for different scenarios.  

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

This project is licensed under EUPL 1.1 or MPL 1.1 - like CIPA e-Delivery.

Versions <= 3.1.3 are compatible with ph-commons < 6.0.
Versions >= 4.0.0 are compatible with ph-commons >= 6.0.

## peppol-smp-client
This project holds the SMP client library used by the access points to retrieve service metadata. It is based on cipa-smp-client-library 2.2.3. This project also contains the BDXR SMP client (since version 4.3.0). 
This project uses Apache HTTP client to perform the REST lookups on foreign SMPs. The reason to not use the Jersey 1.x client is an incompatibility with Java 8. This means that this version is compliant with Java 1.6+.

Versions <= 3.1.3 are compatible with ph-commons < 6.0.
Versions >= 4.0.0 are compatible with ph-commons >= 6.0.

I also provide an OSS [SMP server](https://github.com/phax/peppol-smp-server) with a nice management GUI. 

### Configuration
The SMP client (both PEPPOL and BDXR) uses the file `smp-client.properties` for configuration. The default file resides in the folder `src/main/resources` of this project. You can change the path of the properties file by setting the system property `smp.client.properties.path` (available as of version 4.2.0) to the absolute path of the configuration file (e.g. by specifying `-Dsmp.client.properties.path=/var/www/smpclient.properties` on Java startup). The name of the file does not matter, but if you specify a different properties file please make sure that you also specify an absolute path to the trust store!

It supports the following properties:
  * **`truststore.location`**: the location of the PEPPOL trust store (of type JKS) to be used. If this property is not defined, the value defaults to `truststore/complete-truststore.jks`. By default the SMP client supports the following built-in trust stores (in library [peppol-commons](https://github.com/phax/peppol-commons)):
    * `truststore/complete-truststore.jks` - contains the trust certificates for production and pilot (root, AP, SMP, STS)
    * `truststore/global-truststore.jks` - contains the trust certificates for production only (root, AP, SMP, STS)
    * `truststore/pilot-truststore.jks` - contains the trust certificates for pilot only (root, AP, SMP, STS)
  * **`truststore.password`**: the password to access the trust store. By default the password `peppol` is used. This password is valid for all built-in trust stores mentioned above.
  * **`http.proxyHost`**: the host name or IP address to be used as a HTTP proxy
  * **`http.proxyPort`**: the port of the HTTP proxy. The port must be specfied and has no default value!
  
### Specifying a proxy server
The SMP client supports a proxy server. By default the proxy specified in the configuration file (see above) is used (since version 4.3.0).
Simply call the method `setProxy (org.apache.http.HttpHost)` on an `SMPClient` or `SMPClientReadOnly`. This means you can specify the proxy on a per-call basis.
Proxy authentication is currently not supported.
  
### Example usage

Get the endpoint URL for a participant using a special document type and process:
```java
    // The participant identifier
    final SimpleParticipantIdentifier aPI_AT_Test = SimpleParticipantIdentifier.createWithDefaultScheme ("9915:test");
    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (aPI_AT_Test, ESML.DIGIT_PRODUCTION);
    // Resolve the endpoint address
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                   EPredefinedProcessIdentifier.BIS4A_V20,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    // Endpoint address should be "https://test.erb.gv.at/as2"
    System.out.println ("The Austrian government test AS2 AP that handles invoices in BIS4A V2.0 is located at: " +
                        sEndpointAddress);
```

If you don't need the DNS lookup you can use the URL of the SMP directly (equivalent to the previous example):
```java
    // The participant identifier
    final SimpleParticipantIdentifier aPI_AT_Test = SimpleParticipantIdentifier.createWithDefaultScheme ("9915:test");
    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URLHelper.getAsURI ("B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.edelivery.tech.ec.europa.eu"));
    // Resolve the endpoint address
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                   EPredefinedProcessIdentifier.BIS4A_V20,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    // Endpoint address should be "https://test.erb.gv.at/as2"
    System.out.println ("The Austrian government test AS2 AP that handles invoices in BIS4A V2.0 is located at: " +
                        sEndpointAddress);
```

#Building from source
This project is meant to be build by Maven 3.x.
It requires at least Java 1.6 to be build.
To build simply call `mvn clean install` in the root folder.

#Maven usage
Add the following to your pom.xml to use this artifact:
```
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-commons</artifactId>
  <version>4.3.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-testfiles</artifactId>
  <version>4.3.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-sbdh</artifactId>
  <version>4.3.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-sml-client</artifactId>
  <version>4.3.5</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-smp-client</artifactId>
  <version>4.3.5</version>
</dependency>
```

Alternatively use the following code in your `dependencyManagement` section to use it as a BOM:
```
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-commons-parent-pom</artifactId>
  <version>4.3.5</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

Note: `peppol-bdxr` was integrated in `peppol-commons` as of version 4.3.0.

The binary version of this library can be found on http://repo2.maven.org/maven2/com/helger/ 
They depend on several other libraries so I suggest you are going for the Maven source integration.

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodeingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a>

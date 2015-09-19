# peppol-commons
Java library with shared PEPPOL components.

This is based on the cipa-commons-busdox and cipa-peppol-types project version 2.2.3 but without the support for the START protocol.

This project is used implicitly by the following projects:
  * [peppol-sml-client](https://github.com/phax/peppol-sml-client/) - the SML client library
  * [peppol-smp-client](https://github.com/phax/peppol-smp-client/) - the SMP client library
  * [peppol-smp-server-library](https://github.com/phax/peppol-smp-server-library/) - the SMP server library with the shared code use in all SMP server implementations.
  * [peppol-smp-server](https://github.com/phax/peppol-smp-server/) - the SMP server with a database backend
  * [peppol-smp-server-lightweight](https://github.com/phax/peppol-smp-server-lightweight/) - the SMP server with a file-based backend

This project is licensed under EUPL 1.1 or MPL 1.1 - like CIPA e-Delivery.

Versions <= 3.1.3 are compatible with ph-commons < 6.0.
Versions >= 4.0.0 are compatible with ph-commons >= 6.0.

#peppol-sbdh
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

#Building from source
This project is meant to be build by Maven 3.x.
It requires at least Java 1.6 to be build.
After you initially checked out this project, please make sure to call `mvn generate-sources` to create all the needed generated files. Afterwards `mvn clean install` does the trick.

#Maven usage
Add the following to your pom.xml to use this artifact:
```
<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-commons</artifactId>
  <version>4.0.1</version>
</dependency>

<dependency>
  <groupId>com.helger</groupId>
  <artifactId>peppol-sbdh</artifactId>
  <version>2.0.0</version>
</dependency>
```

---

On Twitter: <a href="https://twitter.com/philiphelger">Follow @philiphelger</a>

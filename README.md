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
  <version>3.1.0</version>
</dependency>
```

---

On Twitter: <a href="https://twitter.com/philiphelger">Follow @philiphelger</a>

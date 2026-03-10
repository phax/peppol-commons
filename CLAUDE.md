# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**peppol-commons** is a Java library collection implementing Peppol (Pan-European Public Procurement Online) e-invoicing and eDelivery standards. It provides ID handling, SMP/SML clients, SBDH processing, and related infrastructure. Dual-licensed under MPL 2.0 and Apache 2.0. Author: Philip Helger.

## Build Commands

```bash
# Full build (requires Java 17+)
mvn clean install

# Build skipping tests
mvn clean install -DskipTests

# Run tests for a single module
mvn test -pl peppol-commons

# Run a single test class
mvn test -pl peppol-commons -Dtest=PeppolTrustStoresTest

# Deploy snapshots (CI uses this on Java 17 only)
mvn -P release-snapshot deploy
```

## Module Architecture

The project has 14 Maven modules organized in layers:

**Core ID Layer:**
- `peppol-id-datatypes` — JAXB-generated classes from XSD for Peppol identifiers
- `peppol-id` — Higher-level ID data structures (ParticipantID, DocumentTypeID, ProcessID)

**Core Infrastructure:**
- `peppol-commons` — Foundation: trust stores, SML/SMP enums, certificate handling, version info
- `peppol-testfiles` — Shared test resources (UBL, SBDH, SimpleInvoicing files)

**Protocol Implementations:**
- `peppol-sbdh` — Standard Business Document Header reader/writer
- `peppol-sml-client` — SML web service client (WSDL-generated, for participant/SMP management)
- `peppol-smp-datatypes` — JAXB classes for SMP (Peppol + OASIS BDXR formats)
- `peppol-smp-client` — SMP/BDXR REST client for service discovery (Apache HttpClient5)
- `peppol-mlr` — Message Level Response builder/marshaller
- `peppol-mls` — Message Level Status builder with Schematron validation

**Directory & Extensions:**
- `peppol-directory-businesscard` — Peppol Directory business card data model (v1-v3)
- `dbnalliance-commons` — DBNAlliance security components
- `dbnalliance-xhe` — Exchange Header Envelope for DBNAlliance
- `hredelivery-commons` — Croatian eDelivery (eRacun) support

Dependencies flow downward: id-datatypes → id → commons → protocol modules.

## Code Generation

Several modules use code generation — do not edit generated files directly:
- **JAXB** (`jaxb-maven-plugin`): Generates Java from XSD schemas in `src/main/resources/external/schemas/` with bindings in `src/main/jaxb/`. Uses `ph-jaxb-plugin` for enhanced output (null-marked, equals/hashCode/toString).
- **JAX-WS** (`jaxws-maven-plugin`): Generates web service stubs in `peppol-sml-client` from WSDL.
- Generated sources land in `target/generated-sources/`.

## Coding Conventions

- **Null safety**: Use `@NonNull` / `@Nullable` from org.jspecify
- **Immutability**: Annotate with `@Immutable` where applicable
- **Logging**: SLF4J with `private static final Logger LOGGER = LoggerFactory.getLogger(...)`
- **Test framework**: JUnit 4 (`@Test`, static assertions)
- **Test naming**: `{ClassName}Test`
- **Package root**: `com.helger.peppol*`, `com.helger.peppolid*`, `com.helger.smpclient*`, `com.helger.dbnalliance*`
- **License header**: Apache 2.0 / MPL 2.0 dual license, years 2015-2026
- **OSGi**: All modules are OSGi bundles with explicit export packages and automatic module names

## Key Configuration

Trust stores are in `peppol-commons/src/main/resources/truststore/` (JKS, PKCS12, BCFKS formats). Default password: `"peppol"`. Supports Peppol G2 (2018) and G3 (2025) PKI infrastructure.

SMP client configuration is loaded from (in order): system property `peppol.smp.client.properties.path`, environment variable `SMP_CLIENT_CONFIG`, `application.properties`, or `smp-client.properties`.

## CI

GitHub Actions (`.github/workflows/maven.yml`): builds on push against Java 17, 21, and 25. Snapshot deployment runs only on Java 17.

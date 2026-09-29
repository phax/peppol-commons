# Contributing

Contributions to peppol-commons are welcome. The library is used by SMP and AP implementations, by validation
tooling and by command line utilities, so a change here reaches a lot of code - which makes a clear description of
*why* a change is needed as valuable as the change itself.

## Getting started

1. Fork the repository and clone it locally.
2. Build it: `mvn clean install -DskipTests`
3. Read the module overview in [README.md](README.md). The modules are layered - network-neutral pieces
   (`edelivery-*`) below the Peppol-specific ones - and a change usually belongs in the lowest layer that can carry
   it.

### Generated sources - read this before opening the project in an IDE

Several modules contain **no Java sources in `src/main/java` for their data types**: the JAXB classes are generated
from XML Schemas at build time into `target/generated-sources/xjc`, and `peppol-sml-client` additionally generates
JAX-WS client stubs from the SML WSDLs.

The modules concerned are:

| Module | Generated from |
|--------|----------------|
| `peppol-id-datatypes` | `peppol-identifiers-v1.xsd` |
| `peppol-codelist-datatypes` | `peppol-codelists-v2.6.xsd` |
| `peppol-smp-datatypes` | the Peppol SMP XSDs |
| `peppol-sbdh` | the SBDH XSDs |
| `peppol-directory-businesscard` | the Business Card XSDs (v1, v2, v3) |
| `peppol-sml-client` | the SML WSDLs, plus XSDs |

A fresh clone opened in an IDE without a prior build therefore shows **hundreds of "cannot resolve symbol" errors**
in the modules that consume those types. Nothing is broken - the sources simply do not exist yet.

Run one of the following before importing the project, or whenever the schemas change:

```sh
mvn generate-sources          # enough to make the IDE happy
mvn clean install -DskipTests # full build, also installs the artifacts locally
```

Then let the IDE re-import the Maven projects, so that `target/generated-sources/xjc` is picked up as a source
folder.

Do not edit anything under `target/` - it is overwritten on every build. The same applies to the predefined code
list enums in `peppol-id` (`EPredefinedDocumentTypeIdentifier`, `EPredefinedProcessIdentifier`,
`EPredefinedParticipantIdentifierScheme`, `EPredefinedTransportProfileIdentifier`,
`EPredefinedSPISUseCaseIdentifier`): they *are* checked in, but they carry a
`This file was automatically generated. Do NOT edit!` header and are produced by the generator in the test sources
from the code list XMLs. Change the code list XML and re-run the generator instead.

## Submitting changes

1. Create a feature branch.
2. Keep commits focused - one logical change per commit.
3. Ensure it compiles and the tests pass: `mvn clean install`
4. Add a test for the behaviour you changed. Identifier, code list and helper logic is all testable without
   network access.
5. Add an entry to the `News and noteworthy` section of [README.md](README.md), under the current
   `- work in progress` version heading.
6. Open a pull request describing what the change does and why.

## Code conventions

Match the surrounding code. In particular:

* Hungarian notation for variables (`sText`, `nIndex`, `aList`, `eType`), `m_` for instance fields, `s_` for static
  fields, `ALL_UPPER_CASE` for constants and `LOGGER` for the logger.
* All parameters `final`, with `@NonNull` / `@Nullable` / `@Nonempty` annotations on non-primitives.
* Interfaces prefixed `I`, enums prefixed `E`, abstract classes prefixed `Abstract`.
* A space before the parentheses of a method call or declaration: `method ()`, `new Foo ()`, `if (x)`.
* ph-commons collection types (`ICommonsList`, `CommonsArrayList`) as return types; standard `List` / `Map` as
  parameter types.
* Add `@since <next version>` to new public API, taking the version from the current `-SNAPSHOT`.
* Apache 2.0 licence header on every new file.

## Reporting issues

Please include the peppol-commons version, the module, and - for anything identifier or discovery related - the
actual identifier or URL involved, since the failure usually depends on the exact value.

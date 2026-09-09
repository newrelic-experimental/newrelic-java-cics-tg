## Version: v1.0.0 | Created: 2026-09-09

### Features
- Added CICS Transaction Gateway (CTG) instrumentation module (`cics-tg-9.2`): weaves `com.ibm.ctg.client.JavaGateway.flow()` so CICS backend calls appear as distinct external nodes in APM Service Maps and Distributed Tracing.
- `JavaGateway_Instrumentation` reads `ECIRequest.Server`/`.Program`, `EPIRequest.Server`, or `ESIRequest.getServer()` to identify the target CICS region.
- `CicsTgUtils` sanitizes the CICS server name to a URI-safe host (allowlist `[A-Za-z0-9.-]`) and reports via `HttpParameters`, producing a Distributed Tracing client span (`span.kind=client`, populated `server.address`).

### Files Changed
- cics-tg-9.2/src/main/java/com/ibm/ctg/client/JavaGateway_Instrumentation.java
- cics-tg-9.2/src/main/java/com/newrelic/instrumentation/labs/cicstg/CicsTgUtils.java
- cics-tg-9.2/build.gradle

### Build
- `moduleVersion=1.0` (gradle.properties) → jar `Implementation-Version` for cics-tg-9.2
- Tag: v1.0.0 (first release)

### Breaking Changes
- None (initial release).

## Installation

To install:

1. Download the latest release jar files.
2. In the New Relic Java directory (the one containing newrelic.jar), create a directory named extensions if it does not already exist.
3. Copy the downloaded jars into the extensions directory.
4. Restart the application.   

<a href="https://opensource.newrelic.com/oss-category/#new-relic-experimental"><picture><source media="(prefers-color-scheme: dark)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/dark/Experimental.png"><source media="(prefers-color-scheme: light)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"><img alt="New Relic Open Source experimental project banner." src="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"></picture></a>


![GitHub forks](https://img.shields.io/github/forks/newrelic-experimental/newrelic-java-cics-tg?style=social)
![GitHub stars](https://img.shields.io/github/stars/newrelic-experimental/newrelic-java-cics-tg?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/newrelic-experimental/newrelic-java-cics-tg?style=social)

![GitHub all releases](https://img.shields.io/github/downloads/newrelic-experimental/newrelic-java-cics-tg/total)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/newrelic-experimental/newrelic-java-cics-tg)
![GitHub last commit](https://img.shields.io/github/last-commit/newrelic-experimental/newrelic-java-cics-tg)
![GitHub Release Date](https://img.shields.io/github/release-date/newrelic-experimental/newrelic-java-cics-tg)


![GitHub issues](https://img.shields.io/github/issues/newrelic-experimental/newrelic-java-cics-tg)
![GitHub issues closed](https://img.shields.io/github/issues-closed/newrelic-experimental/newrelic-java-cics-tg)
![GitHub pull requests](https://img.shields.io/github/issues-pr/newrelic-experimental/newrelic-java-cics-tg)
![GitHub pull requests closed](https://img.shields.io/github/issues-pr-closed/newrelic-experimental/newrelic-java-cics-tg)

# New Relic Java Instrumentation for IBM CICS Transaction Gateway

This instrumentation serves as a custom extension for the New Relic Java Agent, adding observability for applications that use IBM CICS Transaction Gateway (CTG). It weaves `com.ibm.ctg.client.JavaGateway.flow()` so CICS backend calls appear as distinct external nodes in APM Service Maps and Distributed Tracing.

## Installation

1. Download the latest release, or build the jar as described in the Building section below.
2. In the New Relic Java Agent directory (the directory containing `newrelic.jar`), create a directory named `extensions` if it does not already exist.
3. Copy the `cics-tg-9.2` jar into the `extensions` directory.
4. Restart the application.

## Configuration [optional]
The following setting is necessary only if you wish to add a prefix to each CICS node. This can be configured in `newrelic.yml` under the `common` section.

```yaml
# newrelic.yml
common:
  # ... other common agent settings ...

  # Custom host/node prefix (i.e: CTG-CP1DGNA1)
  cics_tg:
    host_prefix: "CTG-"
```

## Building

To build the instrumentation jar requires that Gradle is installed.

1. Set the environment variable `NEW_RELIC_EXTENSIONS_DIR` to a local directory. If building on the same machine as the application, use the `extensions` directory of the New Relic Java Agent.
2. Build and install the `cics-tg-9.2` module:
   ```bash
   ./gradlew cics-tg-9.2:clean cics-tg-9.2:install
   ```
3. Restart the application.

> **JAVA_HOME note:** if the build machine has a browser JRE plugin installed, `/usr/libexec/java_home` may resolve to it ahead of a real JDK, and `compileJava` will fail with `Could not find tools.jar`. Set `JAVA_HOME` explicitly to a real JDK if this happens.

> **Proprietary dependency:** the `cics-tg-9.2` module compiles against `lib/ctgclient.jar` (the CTG SDK client jar), which is not committed to this repository. Place a copy in `cics-tg-9.2/lib/ctgclient.jar` before building, or `compileJava` will fail.

## Support

New Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Issues and contributions should be reported to the project here on GitHub.

We encourage you to bring your experiences and questions to the [Explorers Hub](https://discuss.newrelic.com) where our community members collaborate on solutions and new ideas.

## Contributing

We encourage your contributions to improve this project! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project. If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company, please drop us an email at opensource@newrelic.com.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).

## License

New Relic Java Instrumentation for IBM CICS Transaction Gateway is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.

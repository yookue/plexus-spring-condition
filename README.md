# Plexus Spring Condition

Yookue plexus-spring-condition is a package of powerful condition annotations for Spring.

## Quickstart

- Import dependencies

```xml
    <dependency>
        <groupId>com.yookue.commonplexus</groupId>
        <artifactId>plexus-spring-condition</artifactId>
        <version>LATEST</version>
    </dependency>
```

- General conditions:
  - @ConditionalOnActiveProfile
  - @ConditionalOnAnnotation
  - @ConditionalOnEnvironment
  - @ConditionalOnFile
  - @ConditionalOnHostname
  - @ConditionalOnIpAddress
  - @ConditionalOnLibrary
  - @ConditionalOnLinux
  - @ConditionalOnLocale
  - @ConditionalOnMacAddress
  - @ConditionalOnMacOs
  - @ConditionalOnMultipleCandidates
  - @ConditionalOnPackage
  - @ConditionalOnPort
  - @ConditionalOnPropertyPrefix
  - @ConditionalOnUnixOs
  - @ConditionalOnWindowsOs

- General missing conditions:
  - @ConditionalOnMissingEnvironment
  - @ConditionalOnMissingFile
  - @ConditionalOnMissingHostname
  - @ConditionalOnMissingIpAddress
  - @ConditionalOnMissingLibrary
  - @ConditionalOnMissingLocale
  - @ConditionalOnMissingPackage
  - @ConditionalOnMissingProperty
  - @ConditionalOnMissingPropertyPrefix
  - @ConditionalOnMissingResource

- Combination of all conditions:
  - @ConditionalOnAllAnnotations
  - @ConditionalOnAllBeans
  - @ConditionalOnAllClasses
  - @ConditionalOnAllEnvironments
  - @ConditionalOnAllExpressions
  - @ConditionalOnAllFiles
  - @ConditionalOnAllLibraries
  - @ConditionalOnAllProperties
  - @ConditionalOnAllSingleCandidates

- Combination of missing all conditions:
  - @ConditionalOnMissingAllBeans
  - @ConditionalOnMissingAllClasses
  - @ConditionalOnMissingAllFiles
  - @ConditionalOnMissingAllHostnames
  - @ConditionalOnMissingAllIpAddresses
  - @ConditionalOnMissingAllLibraries
  - @ConditionalOnMissingAllProperties

- Combination of any conditions:
  - @ConditionalOnAnyActiveProfiles
  - @ConditionalOnAnyAnnotations
  - @ConditionalOnAnyBeans
  - @ConditionalOnAnyClasses
  - @ConditionalOnAnyEnvironments
  - @ConditionalOnAnyExpressions
  - @ConditionalOnAnyFiles
  - @ConditionalOnAnyHostNames
  - @ConditionalOnAnyIpAddresses
  - @ConditionalOnAnyLibraries
  - @ConditionalOnAnyLocales
  - @ConditionalOnAnyPackages
  - @ConditionalOnAnyPorts
  - @ConditionalOnAnyProperties
  - @ConditionalOnAnyPropertyPrefixes
  - @ConditionalOnAnyResources
  - @ConditionalOnAnySingleCandidates

- Combination of missing any conditions:
  - @ConditionalOnMissingAnyBeans
  - @ConditionalOnMissingAnyClasses
  - @ConditionalOnMissingAnyEnvironments
  - @ConditionalOnMissingAnyFiles
  - @ConditionalOnMissingAnyLibraries
  - @ConditionalOnMissingAnyPackages
  - @ConditionalOnMissingAnyProperties
  - @ConditionalOnMissingAnyPropertyPrefixes
  - @ConditionalOnMissingAnyResources

## Document

- Github: https://github.com/yookue/plexus-spring-condition

## Requirement

- jdk 17+

## License

This project is under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

See the `NOTICE.txt` file for required notices and attributions.

## Donation

You like this package? Then [donate to Yookue](https://yookue.com/public/donate) to support the development.

## Website

- Yookue: https://yookue.com

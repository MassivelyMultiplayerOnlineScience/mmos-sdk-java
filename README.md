
[Site](http://mmos.ch/) |
[Docs](https://github.com/MassivelyMultiplayerOnlineScience/mmos-sdk-java/tree/master/doc/) |
[Developer Portal](https://devportal.mmos.ch/) |
[Twitter](https://twitter.com/the_mmos) |

# MMOS SDK - Java edition

The MMOS SDK gives easy access to the MMOS API by providing an abstraction layer and encapsulating the authentication modules.

## Usage

If you don't use Maven build, you can either import the fat jar release, or import the normal jar release and its dependencies (httpclient and javax.json) in your build.
For maven, add the JAR release as a dependency for your java project.

Example:
```xml
<dependency>
    <groupId>com.mmos</groupId>
    <artifactId>mmos-sdk-java</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/mmos-sdk-java-1.0.jar</systemPath>
</dependency>
```

## Usage example

```java
// create api instance
Api api = new Api(
                MMOS_API_KEY,
                MMOS_API_SECRET,
                MMOS_API_GAME,
                MMOS_API_PROTOCOL,
                MMOS_API_HOST,
                MMOS_API_PORT);

String playerCode = "playerCode";

try {
    String jsonResponse = api.v2.players.get(playerCode);

    //use any json wrapper process the results
} catch (MMOSRequestException | MMOSAuthenticationException ex) {
    System.out.println(ex.getMessage());
}
```

## Releases

Our releases include a binary, a source and a test jar

## Documentation

API blueprint is available in compiled [html](doc/blueprint/mmos-api-public.html) and [apib](doc/blueprint/mmos-api-public.apib) format.

JavaDoc can also be generated using your favorite IDE or by running the mvn script:

```shell
$ mvn javadoc:javadoc
```


## Running automated tests

Presently the MMOS SDK automated tests use the MMOS Developer Portal (see below)

In order to run the tests, first you'll need to creare an account on the Developer Portal.

Please note that the test rely on specific projects to be avaliable for the game, which is presently the Exoplanet research project by the University of Geneva.
So first you'll need to add the Unige Exoplanet project to your available projects on the Developer Portal.
Please note that this may change in the future and thus you may need to update to the latest version of the SDK and follow the up-to-date instructions to be able to run the automated tests.

Once the account is created, there are three environment variables that need to be set in order to be able to run the tests:
* MMOS_SDK_TEST_API_KEY - The MMOS API Key
* MMOS_SDK_TEST_API_SECRET - The MMOS API Secret
* MMOS_SDK_TEST_GAME - The game id that is generated from your email address

Windows example:
```bat
$ SET MMOS_SDK_TEST_API_KEY=apiKey
$ SET MMOS_SDK_TEST_API_SECRET=secret
$ SET MMOS_SDK_TEST_GAME=my-game
```

Linux example:
```shell
$ export MMOS_SDK_TEST_API_KEY=apiKey
$ export MMOS_SDK_TEST_API_SECRET=secret
$ export MMOS_SDK_TEST_GAME=my-game
```

The MMOS Java SDK uses the Maven build tool, you need this to run the tests. Running the tests is as simple as follows:
```shell
$ mvn clean test
```

### JUnit5 Console launcher

Alternatively, you can use the ConsoleLauncher which is a command-line Java application that lets you launch the JUnit5 Platform from the console.

## Developer Portal overview

The MMOS Developer Portal helps developers understand how the MMOS API works through a set of publicly available test projects.

Registration is publicly available at (https://devportal.mmos.ch/).

An application ApiKey can be requested to be used with the SDK (see Usage above), just make sure to assign the project to your game on the projects page.

## Authentication

Please see the [authentication docs](doc/authentication/api-hmac-authentication.md) for details.

## Acknowledgments

The GAPARS project has received funding from the European Union’s Horizon 2020 research and innovation programme under grant agreement Nr 732703

![EU flag](https://github.com/MassivelyMultiplayerOnlineScience/mmos-sdk-java/raw/master/doc/logo/eu.jpg)
# What about Gradle #

Pause Quafé is built with [Gradle](http://www.gradle.org/). [Gradle](http://www.gradle.org/) is an enterprise-grade build system which provides:

  * A very flexible general purpose build tool like Ant.
  * Switchable, build-by-convention frameworks a la Maven, for Java, Groovy and Scala projects. But we never lock you in!
  * Groovy build scripts.
  * Powerful support for multi-project builds.
  * Powerful dependency management (based on Apache Ivy).
  * Full support for your existing Maven or Ivy repository infrastructure.
  * Support for transitive dependency management without the need for remote repositories and pom.xml or ivy.xml files (optional).
  * Ant tasks and builds as first class citizens.

Gradle is licensed under the [Apache License](http://www.gradle.org/license.html).

  * [Download latest version of Gradle](http://code.taglab.com/hudson/job/Gradle/)
  * [Gradle User's Guide](http://www.gradle.org/0.8/docs/userguide/userguide.html)

# Build Process #

Gradle runs a single script to build all distributions (Windows, MacOSX & Linux)

Just type :
```
gradle clean windowsDistrib macosxDistrib linuxDistrib
```
at the root of the project to run the build. You can omit one or several `xxxDistrib` tasks if you want to. The `clean` task deletes the `build` directory before building.



All generated artifacts will be in :
```
build/distributions/
```
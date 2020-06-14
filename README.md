# scalajs-logging

`scalajs-logging` is a tiny logging API for use by the Scala.js linker and JS environments.
It is not a general-purpose logging library, and you should probably not use it other than to interface with the Scala.js linker API and the `JSEnv` API.

## Setup

Normally you should receive the dependency on scalajs-logging through scalajs-linker or scalajs-js-envs.
If you really want to use it somewhere else, add the following line to your settings:

```scala
libraryDependencies += "org.scala-js" %% "scalajs-logging" % "1.1.1"
```

## Usage

```scala
import org.scalajs.logging._

// get a logger that discards all logs
val logger = NullLogger // discard all logs

// or get a logger that prints all logs of severity Warn and above to the console
val logger = new ScalaConsoleLogger(Level.Error)

// or create your own Logger:
val logger = new Logger {
  def log(level: Level, message: => String): Unit = ???
  def trace(t: => Throwable): Unit = ???
}
```

then give your logger instance to an API.

Available severity levels are `Debug`, `Info`, `Warn` and `Error`.

For more details, consult [the Scaladoc of scalajs-logging](https://javadoc.io/doc/org.scala-js/scalajs-logging_2.13/latest/org/scalajs/logging/).

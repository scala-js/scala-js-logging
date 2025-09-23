val previousVersion: Option[String] = Some("1.2.0")
val newScalaBinaryVersionsInThisRelease: Set[String] = Set.empty

inThisBuild(Def.settings(
  organization := "org.scala-js",
  scalaVersion := "2.12.11",
  crossScalaVersions := Seq("2.11.12", "2.12.11", "2.13.2", "3.3.6"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-Xfatal-warnings",
    "-encoding", "utf-8",
  ),

  versionScheme := Some("semver-spec"),

  // Licensing
  homepage := Some(url("https://github.com/scala-js/scala-js-logging")),
  startYear := Some(2013),
  licenses += (("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))),
  scmInfo := Some(ScmInfo(
      url("https://github.com/scala-js/scala-js-logging"),
      "scm:git:git@github.com:scala-js/scala-js-logging.git",
      Some("scm:git:git@github.com:scala-js/scala-js-logging.git"))),

  // Publishing
  pomExtra := (
    <developers>
      <developer>
        <id>sjrd</id>
        <name>Sébastien Doeraene</name>
        <url>https://github.com/sjrd/</url>
      </developer>
      <developer>
        <id>gzm0</id>
        <name>Tobias Schlatter</name>
        <url>https://github.com/gzm0/</url>
      </developer>
      <developer>
        <id>nicolasstucki</id>
        <name>Nicolas Stucki</name>
        <url>https://github.com/nicolasstucki/</url>
      </developer>
    </developers>
  ),
  pomIncludeRepository := { _ => false },
))

val commonSettings = Def.settings(
  // Scaladoc linking
  apiURL := {
    val name = moduleName.value
    val scalaBinVer = scalaBinaryVersion.value
    val ver = version.value
    Some(url(s"https://javadoc.io/doc/org.scala-js/${name}_$scalaBinVer/$ver/"))
  },
  autoAPIMappings := true,

  // sbt-header configuration
  headerLicense := Some(HeaderLicense.Custom(
    s"""Scala.js Logging (${homepage.value.get})
       |
       |Copyright EPFL.
       |
       |Licensed under Apache License 2.0
       |(https://www.apache.org/licenses/LICENSE-2.0).
       |
       |See the NOTICE file distributed with this work for
       |additional information regarding copyright ownership.
       |""".stripMargin
  )),

  mimaFailOnNoPrevious := newScalaBinaryVersionsInThisRelease.isEmpty,
  // MiMa auto-configuration
  mimaPreviousArtifacts ++= {
    val scalaBinaryV = scalaBinaryVersion.value
    val thisProjectID = projectID.value
    previousVersion match {
      case None =>
        Set.empty
      case _ if newScalaBinaryVersionsInThisRelease.contains(scalaBinaryV) =>
        // New in this release, no binary compatibility to comply to
        Set.empty
      case Some(prevVersion) =>
        Set(thisProjectID.organization %% thisProjectID.name % prevVersion)
    }
  },
)

lazy val `scalajs-logging` = project
  .in(file("."))
  .settings(
    commonSettings,
  )

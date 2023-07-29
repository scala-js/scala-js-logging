val previousVersion: Option[String] = Some("1.1.1")
val newScalaBinaryVersionsInThisRelease: Set[String] = Set()

inThisBuild(Def.settings(
  version := "1.1.2-SNAPSHOT",
  organization := "org.scala-js",
  scalaVersion := crossScalaVersions.value.last,
  crossScalaVersions := Seq("2.11.12", "2.12.18", "2.13.11", "3.3.0"),
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
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.endsWith("-SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
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

  // MiMa auto-configuration
  mimaPreviousArtifacts ++= {
    val scalaV = scalaVersion.value
    val scalaBinaryV = scalaBinaryVersion.value
    val thisProjectID = projectID.value
    previousVersion match {
      case None =>
        Set.empty
      case _ if newScalaBinaryVersionsInThisRelease.contains(scalaBinaryV) =>
        // New in this release, no binary compatibility to comply to
        Set.empty
      case Some(prevVersion) =>
        /* Filter out e:info.apiURL as it expects 1.1.1-SNAPSHOT, whereas the
         * artifact we're looking for has 1.1.0 (for example).
         */
        val prevExtraAttributes =
          thisProjectID.extraAttributes.filterKeys(_ != "e:info.apiURL")
        val prevProjectID =
          (thisProjectID.organization % thisProjectID.name % prevVersion)
            .cross(thisProjectID.crossVersion)
            .extra(prevExtraAttributes.toSeq: _*)
        Set(prevProjectID)
    }
  },
)

lazy val `scalajs-logging` = project
  .in(file("."))
  .settings(
    commonSettings,
  )

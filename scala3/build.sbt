name := "itto-csv"

version      := "2.1.3"
organization := "com.github.gekomad"

scalaVersion := "3.3.7"

val fs2Version = "3.13.0"

libraryDependencies += "com.github.gekomad" %% "scala-regex-collection" % "2.0.2"
libraryDependencies += "co.fs2"             %% "fs2-core"               % fs2Version
libraryDependencies += "co.fs2"             %% "fs2-io"                 % fs2Version
libraryDependencies += "org.apache.commons"  % "commons-csv"            % "1.14.1" % Test
libraryDependencies += "org.scalameta"      %% "munit"                  % "1.2.4"  % Test

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Wunused:implicits",
  "-Wunused:explicits",
  "-Wunused:imports",
  "-Wunused:locals",
  "-Wunused:params",
  "-Wunused:privates",
  "-Xfatal-warnings"
)

//sonatype
import xerial.sbt.Sonatype._
sonatypeCredentialHost := "central.sonatype.com"
sonatypeRepository     := "https://central.sonatype.com/api/v1/publisher"

pomExtra :=
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
    <developers>
      <developer>
        <id>gekomad</id>
        <name>Giuseppe Cannella</name>
        <url>https://github.com/gekomad</url>
      </developer>
    </developers>
    <scm>
      <url>https://github.com/gekomad/itto-csv</url>
      <connection>scm:git:https://github.com/gekomad/itto-csv</connection>
    </scm>
    <url>https://github.com/gekomad/itto-csv</url>

name      := "itto-csv"

import org.scalajs.linker.interface.{ESVersion, ModuleSplitStyle}

lazy val scala3Js = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    version                         := "2.1.2",
    scalaVersion                    := "3.5.2",
    organization                    := "com.github.gekomad",
    scalaJSUseMainModuleInitializer := false,
    scalaJSLinkerConfig ~= (_.withESFeatures(_.withESVersion(ESVersion.ES2018))),
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("scala3Js")))
    },
    scalacOptions ++= Seq("-Xfatal-warnings"),
    libraryDependencies += "co.fs2"             %%% "fs2-core"               % "3.11.0",
    libraryDependencies += "co.fs2"             %%% "fs2-io"                 % "3.11.0",
    libraryDependencies += "com.github.gekomad" %%% "scala-regex-collection" % "2.0.1",
    libraryDependencies += "org.scala-js"       %%% "scalajs-dom"            % "2.8.0",
    libraryDependencies += "org.scalameta"      %%% "munit"                  % "1.1.0"  % Test,
    libraryDependencies += "org.scalacheck"     %%% "scalacheck"             % "1.18.1" % Test
  )

//sonatype
publishTo := sonatypePublishToBundle.value

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

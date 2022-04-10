import sbt.Keys.{organization, _}

lazy val root = (project in file("."))
  .enablePlugins(PlayService, PlayLayoutPlugin)
  .settings(
    scalaVersion := "2.13.6",

    organization := "com.ruksnaitis.modestas",
    name := "gth-web-api",
    version := "1.0-SNAPSHOT",

    libraryDependencies ++= Seq(
      guice,
      "org.joda" % "joda-convert" % "2.2.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.0.1",
      "io.lemonlabs" %% "scala-uri" % "4.0.1",
      "net.codingwell" %% "scala-guice" % "5.0.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.11.0" % Test
    ),

    resolvers += Resolver.typesafeRepo("releases"),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8", // yes, this is 2 args
      "-target:jvm-1.8",
      "-feature",
      "-unchecked",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings"
    ),
    Test / scalacOptions ++= Seq("-Yrangepos"),
    autoAPIMappings := true
  )
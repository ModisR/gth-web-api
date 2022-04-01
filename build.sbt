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
      "org.joda" % "joda-convert" % "2.2.1",
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "1.5.1",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),

    resolvers += Resolver.typesafeRepo("releases"),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8", // yes, this is 2 args
      "-target:jvm-1.8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings",
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    ),
    Test / scalacOptions ++= Seq("-Yrangepos"),
    autoAPIMappings := true
  )
lazy val scala213 = "2.13.14"
lazy val scala3 = "3.3.3"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  //.enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend
  .settings(
    name := "backend",
    version := "1.0-SNAPSHOT",
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213, scala3),
    libraryDependencies ++= Seq(
      guice,
      javaJpa,
      "org.postgresql" % "postgresql" % "42.6.0",
      "org.hibernate" % "hibernate-core" % "6.6.0.Final",
      "io.dropwizard.metrics" % "metrics-core" % "4.2.27",
      "com.palominolabs.http" % "url-builder" % "1.1.5",
      "javax.persistence" % "javax.persistence-api" % "2.2",
      "net.jodah" % "failsafe" % "2.4.4",
    ),
    PlayKeys.externalizeResources := false,
    (Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),
    javacOptions ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    )
  )

val gatlingVersion = "3.9.5"
lazy val gatling = (project in file("gatling"))
  .enablePlugins(GatlingPlugin)
  .settings(
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213, scala3),
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % Test,
      "io.gatling" % "gatling-test-framework" % gatlingVersion % Test
    )
  )

name := "smd1"

version := "0.1"

//scalaVersion := "2.12.8"
//
//val elastic4sVersion = "7.3.1"
//libraryDependencies ++= Seq(
//  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
//
//  // for the default http client
//  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
//
//  // if you want to use reactive streams
//  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion,
//
//  // testing
//  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test"
//)

//lazy val root = Project("elastic4s-http-client-sbt", file("."))
//  .settings(name := "elastic4s-http-client-sbt")
//  .settings(scalaVersion := "2.12.6")
//  .settings(libraryDependencies ++= Seq(
//    "com.sksamuel.elastic4s" %% "elastic4s-http" % "6.3.3",
//"com.sksamuel.elastic4s" %% "elastic4s-embedded" % "6.3.3",
//    "org.slf4j"                 % "slf4j-api"         % "1.7.5",
//  ))


// major.minor are in sync with the elasticsearch releases
val elastic4sVersion = "5.6.0"
libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  // for the tcp client
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion,

  // for the http client
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,

  // if you want to use reactive streams
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % elastic4sVersion,

  // a json library
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-play-json" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-spray-json" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json4s" % elastic4sVersion,

  // testing
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion % "test")

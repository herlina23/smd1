name := "smd1"

version := "0.1"

scalaVersion := "2.12.8"



val elastic4sVersion = "5.6.0"
libraryDependencies ++= Seq(  
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  // for the tcp client
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion,

  // for the http client
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,

  // if you want to use reactive streams
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % elastic4sVersion,

  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion,

  // a json library
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-play-json" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-spray-json" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json4s" % elastic4sVersion,

  // testing
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion % "test"
)

//#######################################3
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"

libraryDependencies += "joda-time" % "joda-time" % "2.10.3"

libraryDependencies += "joda-time" % "joda-time" % "2.10.3"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"

lazy val akkaVersion = "2.5.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

//libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.1"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-file" % "1.1.1"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "1.1.1"


//libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.10"
libraryDependencies +="com.typesafe.akka" %% "akka-stream-kafka" % "0.21.1"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"

libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json" % "1.21.0"

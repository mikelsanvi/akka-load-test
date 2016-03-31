organization := "org.mikel.tests"
name := "akkaload"

scalaVersion := "2.11.8"

resolvers +=
  "Maven Central" at "http://repo1.maven.org/maven2"
resolvers +=
  "Nexus Repository Manager" at "http://ec2-52-48-62-134.eu-west-1.compute.amazonaws.com:8081/nexus/"


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % "2.4.6",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14"
)

publishMavenStyle := true

publishTo := {
  val nexus = "http://ec2-52-48-62-134.eu-west-1.compute.amazonaws.com:8081/nexus/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "content/repositories/releases")
}

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
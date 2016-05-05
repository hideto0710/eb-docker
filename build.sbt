import com.typesafe.sbt.packager.docker._

lazy val DockerExposePort = 9000
lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin)
  .enablePlugins(PlayScala)
  .settings(
    name := "eb-docker",
    version := "1.0.0",
    scalaVersion := "2.11.7",
    scalacOptions := Seq("-language:_", "-deprecation", "-unchecked", "-feature", "-Xlint"),
    transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
    sources in (Compile, doc) := Nil,
    publishArtifact in (Compile, packageDoc) := false,
    parallelExecution in Test := false
  )
  .settings(
    resolvers += Resolver.file(
      "local-ivy-repos", file(Path.userHome + "/.ivy2/local")
    )(Resolver.ivyStylePatterns),
    libraryDependencies ++= Seq(
      jdbc,
      evolutions,
      "mysql" % "mysql-connector-java" % "5.1.36",
      "org.skinny-framework" %% "skinny-orm" % "2.0.7",
      "org.scalikejdbc" %% "scalikejdbc" % "2.3.5",
      "org.scalikejdbc" %% "scalikejdbc-config" % "2.3.5",
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.5.0",
      "org.scalikejdbc" %% "scalikejdbc-test" % "2.3.5" % "test",
      "org.scalactic" %% "scalactic" % "2.2.6",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test",
      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
    )
  )
  .settings(
    javaOptions in Test ++= Seq("-Dconfig.file=conf/local.conf")
  )
  .settings(
    packageName in Docker := "eb-docker",
    maintainer in Docker := "INAMURA Hideto <h.inamura0710@gmail.com>",
    dockerExposedPorts := Seq(DockerExposePort, DockerExposePort),
    dockerRepository := Some("docker.io/shuto0710"),
    dockerCommands := dockerCommands.value.filterNot {
      case ExecCmd("ENTRYPOINT", args @ _*) => true
      case ExecCmd("CMD", args @ _*) => args.isEmpty
      case cmd => false
    },
    dockerCommands ++= Seq(
      ExecCmd("CMD", "bin/eb-docker", "-Dplay.evolutions.db.default.autoApply=true")
    )
  )

routesGenerator := InjectedRoutesGenerator

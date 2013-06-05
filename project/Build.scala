import sbt._
import Keys._ 
import com.typesafe.sbteclipse.plugin.EclipsePlugin._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys._
import spray.revolver.RevolverPlugin._

object BuildSettings {
  val buildOrganization = "nandie"
  val buildVersion      = "0.1.1"
  val buildScalaVersion = "2.10.1"
  val buildScalacOptions = Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", 
   "-Yresolve-term-conflict:package",
   "-language:reflectiveCalls","-language:implicitConversions", "-language:postfixOps",
   "-language:dynamics","-language:higherKinds","-language:existentials",
   "-language:experimental.macros" ) 
  
  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    scalaVersion in Test := buildScalaVersion,
    scalacOptions := buildScalacOptions,   
    scalacOptions in Test := buildScalacOptions,
    javacOptions ++= Seq("-encoding", "UTF-8")
  )
  
  val eclipseSettings = Seq(
    unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_)),
    unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_)),
    createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource ,         
    withSource := true
 )
}


object Dependencies {
  import BuildSettings._

  val akkaVersion = "2.1.4" 
  val sprayVersion = "1.1-M8-SNAPSHOT"
  
  val deps = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion, 
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion, 
    "com.typesafe.akka" %% "akka-agent" % akkaVersion, 
    "ch.qos.logback" % "logback-classic" % "1.0.9",
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-client" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" % "spray-httpx" % sprayVersion,
    "io.spray" % "spray-util" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.2.3"
  )
}

object ProjectBuild extends Build {
 import BuildSettings._
 import Dependencies._

 lazy val core = Project(id = "spray-406-test",
     base = file("."),  
     settings = buildSettings ++ 
     Revolver.settings ++ 
     eclipseSettings ++ 
     Seq(
       libraryDependencies ++= (deps),
       unmanagedResourceDirectories in Compile <+= baseDirectory (_ / "src/main/web") 
       
    )
   )
}

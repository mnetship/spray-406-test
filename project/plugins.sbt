
resolvers ++= Seq( 
  Classpaths.typesafeResolver,
  Classpaths.typesafeSnapshots,
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/" ,
  "spray repo" at "http://repo.spray.io",
  Resolver.url("artifactory", 
    url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases")
  )(Resolver.ivyStylePatterns)
)


addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.1")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.6.2")


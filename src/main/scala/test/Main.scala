package test

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http

import spray.http.ContentType._
import org.parboiled.common.FileUtils

import spray.routing._
import spray.routing.directives._
import spray.util._
import spray.http._
import MediaTypes._
import CacheDirectives._
import spray.http._
import MediaTypes._
import StatusCodes._
import HttpHeaders._
import org.slf4j._
import akka._
import akka.actor._

object Main extends App {

  val system = ActorSystem()
  
  class DemoService extends Actor with HttpServiceBase {

    implicit val settings = RoutingSettings.default

    def receive: Actor.Receive = runRoute(route)

    val route: Route =
      path("/") { 
        _.redirect("/index.html")
      } ~ path(Rest) { path: String =>
        get {
          getFromResource(path)
        }
      }
  }

  {
    implicit val actorSystem = system
    val handler = system.actorOf(Props[DemoService], name = "handler")
    IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
  }
}
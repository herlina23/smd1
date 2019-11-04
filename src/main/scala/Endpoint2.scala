//
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.stream.ActorMaterializer
//import akka.http.scaladsl.server.Directives._
//import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
//import play.api.libs.json.{Format, Json}
//
//object Endpoint2 extends PlayJsonSupport{
//
//  // needed to run the route
//  implicit val system = ActorSystem()
//  implicit val materializer = ActorMaterializer()
//  implicit val executionContext = system.dispatcher
//
//
//  final case class ResultFill(
//                               id: Long,
//                               resource_type: String,
//                               specific_resource_type: String,
//                               stream_type: String,
//                               from_id: String,
//                               from_name: String,
//                               from_display_picture: String,
//                               content: String,
//                               final_sentiment: String,
//                               timestamp: String,
//                               object_content: String)
//  object ResultFill{
//    implicit val formatter:Format[ResultFill]=Json.format[ResultFill]
//  }
//
//
//
//  def main(args: Array[String]) {
//
//    val getNew1 = NewVal.GetValue // List[Resultfill]
//
//
//    val route =
//      concat(
//        path("rawstream") {
//          concat(
//            get {
//              //complete(List(Project(135,"2017-09-22T08:29:49.000Z","AngkasaPura","Angkasa Pura","2020-03-06T11:56:26.000+0000"),Project(175,"2017-10-25T04:36:44.000Z","E-Commerce","E-Commerce","2019-12-31T00:00:00.000+0000")))
//            //complete(getNew1) // error : expected => ToResponseMarshallable
//              complete("ahahah")
//            }
//          )
//        }
//
//      )
//
//    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
//    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
//    //
//
//  }
//}
//
//
//

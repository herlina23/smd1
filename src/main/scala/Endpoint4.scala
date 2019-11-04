
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.sksamuel.elastic4s.http.ElasticDsl.{rangeQuery, search, _}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}
import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader}
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import javax.print.attribute.standard.MediaSize.NA
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder

import scala.concurrent.Future
import scala.util.{Failure, Success}



final case class RawStream(
                            id: Long,
                            resource_type: String,
                            specific_resource_type: String,
                            stream_type: String,
                            from_id: String,
                            from_name: String,
                            from_display_picture: String,
                            content: String,
                            final_sentiment: String,
                            timestamp: String,
                            object_content: String)
object RawStream{
  implicit val formatter:Format[RawStream]=Json.format[RawStream]

  implicit val HitReader: HitReader[RawStream] = new HitReader[RawStream] {
    override def read(hit: Hit): Either[Throwable, RawStream] = {
      val obj =
        RawStream(
          hit.sourceAsMap("id").toString.toLong,
          hit.sourceAsMap("resource_type").toString,
          hit.sourceAsMap("specific_resource_type").toString,
          hit.sourceAsMap("stream_type").toString,
          hit.sourceAsMap("from_id").toString,
          hit.sourceAsMap("from_name").toString,
          hit.sourceAsMap("from_display_picture").toString,
          hit.sourceAsMap("content").toString,
          hit.sourceAsMap("final_sentiment").toString,
          hit.sourceAsMap("timestamp").toString,
          hit.sourceAsMap("object_content").toString,
        )
      Right(obj)
    }
  }
}

case class Pg2(next:Option[String] =null)
{
  lazy val pgn = next match {
    case Some(e) => e
    case null => None
  }
}
object Pg2{
  implicit val formatter:Format[Pg2]=Json.format[Pg2]
}

case class newStream(result:List[RawStream],paging:Option[Pg2]=None){
  lazy val pgn = paging match {
    case Some(e) => e
    case None => None
  }
}
object newStream{
  implicit val formatter:Format[newStream]=Json.format[newStream]
}

object Endpoint4 extends PlayJsonSupport{

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  def main(args: Array[String]) {

    val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })


    import akka.http.scaladsl.server.Directives._

    val route =
      concat(
        pathPrefix("objects"/LongNumber/"rawStream"){ id=>
          get{
            parameter("timestamp_start","timestamp_end","next".?,"count".as[Int]){(ts,te,nx,count)=>
              val tsSplit = ts.split(" ")(0)
              val teSplit= te.split(" ")(0)
              val nxValue = nx.getOrElse("")
              val nxSplit = if (nxValue!="") nxValue.split(" ")(0) else ""

              //val upFilter = if (nxValue == "") te else nxValue
              val upFilter = if (nxSplit == "") teSplit else nxSplit

                val resp: SearchResponse = client.execute {
                  search("xei" / "ae")  postFilter{
                    rangeQuery("timestamp") lte upFilter gte tsSplit
                  }  sortByFieldDesc ("timestamp") limit(count)
                }.await
                val respon = resp.to[RawStream].toList
              val getLast = respon.last.timestamp
              val getLastOpt = {
                if(respon.length < count) null  else getLast
              }
              complete(newStream(respon,Option(Pg2(Option(getLastOpt)))))

            }
          }
        },

//        pathPrefix("objects"/LongNumber/"rawStream"){ id=>
//          get{
//            parameter("timestamp_start","timestamp_end","next".?,"count".as[Int]){(ts,te,nx,count)=>
//              val tsSplit = ts.split(" ")(0)
//              val teSplit= te.split(" ")(0)
//              val nxValue = nx.getOrElse("")
//              val nxSplit = if (nxValue!="") nxValue.split(" ")(0) else ""
//              val upFilter = if (nxSplit == "") teSplit else nxSplit
//              val resp: Future[SearchResponse] = client.execute {
//                search("xei" / "ae")  postFilter{
//                  rangeQuery("timestamp") lte upFilter gte tsSplit
//                }  sortByFieldDesc ("timestamp") limit(count)
//              }.map{
//                res=>
//                  val respon = res.to[RawStream].toList
//                  respon
//              }
//              complete("OK")
//
//            }
//          }
//        },




      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }
}




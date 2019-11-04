//
//
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.stream.ActorMaterializer
//import com.sksamuel.elastic4s.http.ElasticDsl.search
//import com.sksamuel.elastic4s.searches.RichSearchResponse
//import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
//import play.api.libs.json.{Format, Json}
//import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader}
//import com.sksamuel.elastic4s.http.ElasticDsl._
//import com.sksamuel.elastic4s.http.HttpClient
//import com.sksamuel.elastic4s.http.search.SearchResponse
//import org.apache.http.client.config.RequestConfig
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
//
//import org.joda.time._
//
//final case class RawStream(
//                            id: Long,
//                            resource_type: String,
//                            specific_resource_type: String,
//                            stream_type: String,
//                            from_id: String,
//                            from_name: String,
//                            from_display_picture: String,
//                            content: String,
//                            final_sentiment: String,
//                            timestamp: String,
//                            object_content: String)
//object RawStream{
//  implicit val formatter:Format[RawStream]=Json.format[RawStream]
//
//  implicit val HitReader: HitReader[RawStream] = new HitReader[RawStream] {
//    override def read(hit: Hit): Either[Throwable, RawStream] = {
//      val obj =
//        RawStream(
//          hit.sourceAsMap("id").toString.toLong,
//          hit.sourceAsMap("resource_type").toString,
//          hit.sourceAsMap("specific_resource_type").toString,
//          hit.sourceAsMap("stream_type").toString,
//          hit.sourceAsMap("from_id").toString,
//          hit.sourceAsMap("from_name").toString,
//          hit.sourceAsMap("from_display_picture").toString,
//          hit.sourceAsMap("content").toString,
//          hit.sourceAsMap("final_sentiment").toString,
//          hit.sourceAsMap("timestamp").toString,
//          hit.sourceAsMap("object_content").toString,
//        )
//      Right(obj)
//    }
//  }
//}
//
//object Elas8{
//
//  // needed to run the route
//  implicit val system = ActorSystem()
//  implicit val materializer = ActorMaterializer()
//  implicit val executionContext = system.dispatcher
//  def main(args: Array[String]) {
//
//    val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })
//
//    val resp: SearchResponse = client.execute {
//      search("xei" / "ae") matchAllQuery() size(3)
//    }.await
//    val respon = resp.to[RawStream].toList
//
//    val takeSome = respon(2)
//
//    val tgl1 = "2019-09-22T17:00:00+0000"
//    val pars1 = tgl1.split('+')(0)
//
//
//    println(respon)
//    println("\n")
//    println(pars1)
//  }
//}
//
//
//

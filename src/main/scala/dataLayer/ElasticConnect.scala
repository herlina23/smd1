//package dataLayer
//
//class ElasticConnect {
//
//}
//
//
//// BIKIN GIT IGNORE!!!!
//
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.stream.ActorMaterializer
//import com.sksamuel.elastic4s.http.ElasticDsl.{rangeQuery, search, _}
//import com.sksamuel.elastic4s.searches.RichSearchResponse
//import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
//import play.api.libs.json.{Format, Json}
//import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader}
//import com.sksamuel.elastic4s.http.HttpClient
//import com.sksamuel.elastic4s.http.search.SearchResponse
//import org.apache.http.client.config.RequestConfig
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
//import org.elasticsearch.search.sort.{SortMode, SortOrder}
//import org.joda.time._
//
//
//
////https://github.com/sksamuel/elastic4s/blob/master/elastic4s-tests/src/test/scala/com/sksamuel/elastic4s/search/SearchDslTest.scala
//
//object ElasticConnect extends PlayJsonSupport{
//
//  implicit val system = ActorSystem()
//  implicit val materializer = ActorMaterializer()
//  implicit val executionContext = system.dispatcher
//  def main(args: Array[String]) {
//
//    val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })
//
//    def sortNext(timestamp_start:String, timestamp_end:String, count:Int)={
//      val ts = timestamp_start.split('+')(0)
//      val te = timestamp_end.split('+')(0)
//      //val cnt = if count
//      val resp: SearchResponse = client.execute {
//        search("xei" / "ae") postFilter {
//          rangeQuery("timestamp") gte ts lte te
//        }sortByFieldDesc ("timestamp") limit count
//      }.await
//      val respon = resp.to[RawStream].toList
//      val nx = count-1
//      respon(nx).timestamp
//    }
//
//    def searchByParam(timestamp_start:String, timestamp_end:String, count:Int)={
//      val ts = timestamp_start.split('+')(0)
//      val te = timestamp_end.split('+')(0)
//      val resp: SearchResponse = client.execute {
//        search("xei" / "ae") postFilter {
//          rangeQuery("timestamp") gte ts lte te
//        }sortByFieldDesc  ("timestamp") limit count
//      }.await
//      val respon = resp.to[RawStream].toList
//      respon
//    }
//
//
//  }
//}
//
//
//

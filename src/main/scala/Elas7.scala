import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder


//cari pembenaran dari import yang masih merah


object Elas7 extends App {
  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })

  case class ResultFill(
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
  implicit object ResultFillReader extends HitReader[ResultFill] {
    override def read(hit: Hit): Either[Throwable, ResultFill] = {
      Right(ResultFill(
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
      ))
    }
  }

  case class Paging(next:String)
  implicit object PagingReader extends HitReader[Paging] {
    override def read(hit: Hit): Either[Throwable, Paging] = {
      Right(Paging(hit.sourceAsMap("next").toString))
    }
  }
  case class newstream (resultFill: ResultFill,paging: Paging)


  implicit object NewStreamHitReader extends HitReader[newstream] {
    override def read(hit: Hit): Either[Throwable, newstream] = {
      val obj = newstream(
        ResultFill(

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

        ),
        Paging(hit.sourceAsMap("next").toString)
      )
      Right(obj)
    }
  }

  val searchAll = client.execute {
    multi(
      search("xei" / "ae") postFilter {
        rangeQuery("timestamp") gte "2019-09-23T17:00:00+0000" lte "2019-09-25T17:00:00+0000"
      },
      search("xem" / "aa") query "12345678"
    )
  }.await

  val respon = searchAll.responses.map(_.to[ResultFill])

  val hasil2 = searchAll.responses.map(_.hits.hits.map(_.sourceAsMap)).flatten


  println(respon)

// kenapa mesti pake list of user : karena kalau pake list of user datanya...
//  -lbh mudah di manipulasi
//  - msl error mudah dideteksi
//  - lbh mudah akses, coding tdk kompleks (?)
//  - functional programming -> apa yg memudahkan ?
//  -
  // ambigu * buat MVP * spesifikasi

  // kenapa kalo list of result itu ga

  //println(hsl)
}
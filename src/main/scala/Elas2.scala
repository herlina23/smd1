import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder


//cari pembenaran dari import yang masih merah


object Elas2 extends App {
  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })

  case class ABC(aa: String, bb: String)
  implicit object ABCHitReader extends HitReader[ABC] {
    override def read(hit: Hit): Either[Throwable, ABC] = {
      Right(ABC(hit.sourceAsMap("aa").toString, hit.sourceAsMap("bb").toString))
    }
  }


  // https://stackoverflow.com/questions/40517893/elastic4s-deserializing-search-results

  // now we can search for the document we just indexed
  val resp = client.execute {
    search("zox" / "test1") query "lalal"
  }.await
  val abc_new: Seq[ABC] = resp.to[ABC]

//  case class Rassasa(rs:Result,pg:Paging)
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
   case class Result(results: List[ResultFill])
  implicit object ResultHitReader extends HitReader[Result] {
    override def read(hit: Hit): Either[Throwable, Result] = {
      val obj = Result(
        hit.sourceField("results").asInstanceOf[List[AnyRef]].map { entry =>
          ResultFill(
            hit.sourceAsMap("results.id").toString.toLong,
            hit.sourceAsMap("results.resource_type").toString,
            hit.sourceAsMap("results.specific_resource_type").toString,
            hit.sourceAsMap("results.stream_type").toString,
            hit.sourceAsMap("results.from_id").toString,
            hit.sourceAsMap("results.from_name").toString,
            hit.sourceAsMap("results.from_display_picture").toString,
            hit.sourceAsMap("results.content").toString,
            hit.sourceAsMap("results.final_sentiment").toString,
            hit.sourceAsMap("results.timestamp").toString,
            hit.sourceAsMap("results.object_content").toString,
          )
        }
      )
      Right(obj)
    }
  }

// case class PagingFormat(paging:Option[Paging])

  case class RawStream(result: List[ResultFill],paging:Paging)

  implicit object RawStreamHitReader extends HitReader[RawStream] {
    override def read(hit: Hit): Either[Throwable, RawStream] = {
      val obj = RawStream(
        hit.sourceField("result").asInstanceOf[List[AnyRef]].map { entry =>
          ResultFill(
            hit.sourceAsMap("result.id").toString.toLong,
            hit.sourceAsMap("result.resource_type").toString,
            hit.sourceAsMap("result.specific_resource_type").toString,
            hit.sourceAsMap("result.stream_type").toString,
            hit.sourceAsMap("result.from_id").toString,
            hit.sourceAsMap("result.from_name").toString,
            hit.sourceAsMap("result.from_display_picture").toString,
            hit.sourceAsMap("result.content").toString,
            hit.sourceAsMap("result.final_sentiment").toString,
            hit.sourceAsMap("result.timestamp").toString,
            hit.sourceAsMap("result.object_content").toString,
          )
        },
        Paging(hit.sourceField("paging.next").toString),
      )
      Right(obj)
    }
  }

  val resAll2 = client.execute{
    search("zaj" / "aa") matchAllQuery()
  }.await

  val respon: Seq[RawStream] = resAll2.to[RawStream]



  println(respon)


  //println(hsl)
}
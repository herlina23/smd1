import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder


//cari pembenaran dari import yang masih merah


object Elas5 extends App {
  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })

  case class ABC(aa: String, bb: String)
  implicit object ABCHitReader extends HitReader[ABC] {
    override def read(hit: Hit): Either[Throwable, ABC] = {
      Right(ABC(hit.sourceAsMap("aa").toString, hit.sourceAsMap("bb").toString))
    }
  }

  // now we can search for the document we just indexed
  val resp = client.execute {
    search("zox" / "test1") query "lalal"
  }.await
  val abc_new: Seq[ABC] = resp.to[ABC]




   println(abc_new)


  //println(hsl)
}
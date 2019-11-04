import com.sksamuel.elastic4s.{ElasticsearchClientUri, Hit, HitReader, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder



object Elas3 extends App {

  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })

  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  client.execute {
    indexInto("bands" / "artists") fields ("name" -> "coldplay") refresh(RefreshPolicy.IMMEDIATE)
  }.await

  // now we can search for the document we just indexed
  val resp = client.execute {
    search("bands" / "artists") query "coldplay"
  }.await

  //search : Hitreader, coba lihat sosmed elasticsearch bagian hit reader, type class

  println(resp)
}
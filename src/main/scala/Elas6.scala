import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl.{search, _}
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.HitReader
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder


//cari pembenaran dari import yang masih merah


object Elas6 extends App {
  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })


  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  //  client.execute {
  //    indexInto("bands" / "artists") fields ("name" -> "Jazzy") refresh(RefreshPolicy.IMMEDIATE)
  //  }.await




  // now we can search for the document we just indexed
  val resp = client.execute {
    search("zox" / "test1") query "lalal"
  }.await

  val resp2 = client.execute {
    multi(
      search("xei" / "ae") query "instagram",
        search("xei" / "ae") query "joker"
    )
  }.await

  val req1 = search("xei" / "ae") postFilter {
    rangeQuery("timestamp") gte "2019-09-23T17:00:00+0000" lte "2019-09-25T17:00:00+0000"
  }

  //req2 approved
  val req2 = client.execute {
    search("xei" / "ae") postFilter {
      rangeQuery("timestamp") gte "2019-09-21T17:00:00+0000" lte "2019-09-23T17:00:00+0000"
    }
  }.await


  val searchAll = client.execute {
   multi(
     search("xei" / "ae") postFilter {
       rangeQuery("timestamp") gte "2019-09-23T17:00:00+0000" lte "2019-09-25T17:00:00+0000"
     },
     search("xem" / "aa") query "12345678"
   )
  }.await


  //search : Hitreader, coba lihat sosmed elasticsearch bagian hit reader, type class

  val hasil = req2.hits.hits.toList.map(_.sourceAsMap)
  val hasil2 = searchAll.responses.map(_.hits.hits.map(_.sourceAsMap)).flatten


  println(hasil2)


  //println(hsl)
}
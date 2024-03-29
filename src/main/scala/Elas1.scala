import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.http.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import com.sksamuel.elastic4s.http.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import com.sksamuel.elastic4s.HitReader
import com.sksamuel.elastic4s.searches.queries.matches.MatchQueryBuilder


//cari pembenaran dari import yang masih merah


object Elas1 extends App {
  // Here we create an instance of the TCP client
  val client = HttpClient(ElasticsearchClientUri("localhost",9200),(requestConfigBuilder: RequestConfig.Builder) => { requestConfigBuilder.setConnectionRequestTimeout(60000) .setSocketTimeout(60000) .setConnectTimeout(60000) }, (httpClientBuilder: HttpAsyncClientBuilder) => { httpClientBuilder })


  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
//  client.execute {
//    indexInto("bands" / "artists") fields ("name" -> "Jazzy") refresh(RefreshPolicy.IMMEDIATE)
//  }.await

// searc matchAll
  val resAll = client.execute{
    search("pjs" / "pj") matchAllQuery()
  }.await

  val resAll2 = client.execute{
    search("zaj" / "aa") matchAllQuery()
  }.await


  // now we can search for the document we just indexed
  val resp = client.execute {
    search("zox" / "test1") query "lalal"
  }.await



  //search : Hitreader, coba lihat sosmed elasticsearch bagian hit reader, type class

  val hasil = resAll2.hits.hits.toList
  //val hasil2

  val hsl = resAll2.hits.hits.map {
    tr=>
      val ddd = tr.sourceAsMap
      println(ddd)
  }

 // println(hasil)


  //println(hsl)
}
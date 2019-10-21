import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy

object Elas3 extends App {

  // Here we create an instance of the TCP client
  val client = TcpClient.transport(ElasticsearchClientUri("localhost",9200))

  // await is a helper method to make this operation synchronous instead of async
  // You would normally avoid doing this in a real program as it will block your thread
  client.execute {
    indexInto("bands" / "artists") fields ("name" -> "coldplay") refresh(RefreshPolicy.IMMEDIATE)
  }.await

  // now we can search for the document we just indexed
  val resp = client.execute {
    search("bands" / "artists") query "coldplay"
  }.await

  println(resp)
}
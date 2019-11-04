package model

//class RawStream_Model {
//}


import play.api.libs.json.{Format, Json}
import com.sksamuel.elastic4s.{Hit, HitReader}

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
case class Pg(next:String)
object Pg{
  implicit val formatter:Format[Pg]=Json.format[Pg]
}
case class TryStream(result:List[RawStream],paging:Pg)
object TryStream{
  implicit val formatter:Format[TryStream]=Json.format[TryStream]
}

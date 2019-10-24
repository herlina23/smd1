////import java.time.LocalDate
////
////import akka.http.documenteddsl.directives.UnmarshallingDDirectives._
////import akka.http.documenteddsl.documentation.OutDocumentation._
////import akka.http.documenteddsl.documentation.{JsonSchema, OutDocumentation, RouteDocumentation}
////import akka.http.scaladsl.model.StatusCodes
////import akka.http.scaladsl.testkit.ScalatestRouteTest
////import com.fasterxml.jackson.databind.jsonschema.JsonSchema
////import org.scalatest.MustMatchers._
////import org.scalatest.WordSpec
////import play.api.libs.json.{Format, Json}
////
////class UnmarshallingDDirectivesSpec extends WordSpec with DDirectivesSpec with ScalatestRouteTest {
////  import UnmarshallingDDirectivesSpec._
////
////  "Out" must {
////    val now = LocalDate.now()
////
////    "be applied to route documentation" in {
////      Out[TestOut].describe(RouteDocumentation()).out mustBe Some(OutDocumentation(
////        success = List(
////          Payload.Success(
////            status = Status(StatusCodes.OK),
////            contentType = "application/json",
////            schema = JsonSchema.resolveSchema[TestOut],
////            example = None))))
////    }
////    "be applied to route documentation (concatenated)" in {
////      val out = Out(StatusCodes.Created, TestOut("id", Some("name"), now)) & Out(StatusCodes.NotFound, "entity not found")
////      out.describe(RouteDocumentation()).out mustBe Some(OutDocumentation(
////        failure = List(
////          Payload.Failure(
////            status = Status(StatusCodes.NotFound),
////            contentType = None,
////            description = Some("entity not found"))),
////        success = List(
////          Payload.Success(
////            status = Status(StatusCodes.Created),
////            contentType = "application/json",
////            schema = JsonSchema.resolveSchema[TestOut],
////            example = Some(Json toJson TestOut("id", Some("name"), now))))))
////    }
////  }
////
////}
////
////object UnmarshallingDDirectivesSpec {
////  case class TestOut(id: String, name: Option[String], createdAt: LocalDate)
////  implicit val testInFormat: Format[TestOut] = Json.format[TestOut]
////}
//
//
//
//import com.sksamuel.elastic4s.{Hit, HitReader}
//
//
//// the other case class
//
//case class Character(name: String, location: String)
//
//implicit object CharacterHitReader extends HitReader[Character] {
//  override def read(hit: Hit): Either[Throwable, Character] = {
//    Right(Character(hit.sourceAsMap("name").toString, hit.sourceAsMap("location").toString))
//  }
//}
//
////https://stackoverflow.com/questions/49752112/avoid-await-in-elastic4s-query?rq=1
////https://stackoverflow.com/questions/40517893/elastic4s-deserializing-search-results
//
//
////https://static.javadoc.io/com.sksamuel.elastic4s/elastic4s-core_2.11/5.2.0/index.html#com.sksamuel.elastic4s.Hit
//
////val resp = client.execute {
////  search("gameofthrones" / "characters").query("kings landing")
////}.await // don't block in real code
////
////// .to[Character] will look for an implicit HitReader[Character] in scope
////// and then convert all the hits into Characters for us.
////val characters :Seq[Character] = resp.to[Character]
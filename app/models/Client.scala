package models

import play.api.Play.current
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.novus.salat.global._

case class Client(
  @Key("_id") id: ObjectId = new ObjectId,
  clientId: Int,
  pageType: String
)

object Client extends ModelCompanion[Client, ObjectId] {
  val collection = mongoCollection("clients")
  val dao = new SalatDAO[Client, ObjectId](collection = collection) {}

  def findOneByClientId(clientId: Int): Option[Client] = dao.findOne(MongoDBObject("clientId" -> clientId))
  
  def options: Seq[(String,String, String)] = {
    find(MongoDBObject.empty).map(it => (it.id.toString, it.clientId.toString, it.pageType)).toSeq
  }
}

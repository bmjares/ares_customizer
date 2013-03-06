package models

import org.bson.types.ObjectId;

import play.api._
import play.api.Play.current
import com.novus.salat._
import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.novus.salat.global._

case class Client(
  @Key("_id") id: ObjectId = new ObjectId,
  clientId: Int,
  pageType: String
)

//object Client extends ModelCompanion[Client, ObjectId] {
//  val collection = mongoCollection("clients")
//  val dao = new SalatDAO[Client, ObjectId](collection = collection) {}
//
//  def findOneByClientId(clientId: Int): Option[Client] = dao.findOne(MongoDBObject("clientId" -> clientId))
//  
//  def options: Seq[(String,String, String)] = {
//    find(MongoDBObject.empty).map(it => (it.id.toString, it.clientId.toString, it.pageType)).toSeq
//  }
//}

object ClientsObj extends ModelCompanion[Client, ObjectId] {
  val clients = MongoConnection()("ares_customizer")("clients")
  val dao = new SalatDAO[Client, ObjectId](collection = clients) {}
  
  def all = clients.map(grater[Client].asObject(_)).toList
  
  def findClient(id: ObjectId): Option[Client] = dao.findOne(MongoDBObject("_id" -> id))
  def findClientbyClientId(clientId: Int) = dao.findOne(MongoDBObject("clientId" -> clientId))
  
  def create(client: Client) {
    clients += grater[Client].asDBObject(client)
  }
  
  def saveClient(client: Client) {
    var foundClient = findClient(client.id)
    Logger.info("client in model saveClient: "+ client.toString)
    if (foundClient.isDefined) {
      Logger.info("in found client")
      val toSaveClient = new Client(
          id = client.id,
          clientId = client.clientId,
          pageType = client.pageType)
      dao.save(toSaveClient)
      
    }
  }
  
}
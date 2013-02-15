package models

import play.api.Play.current
import com.novus.salat._
import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.novus.salat.global._

case class Header(
  clientId: Int,
  headline: String,
  headerLayout: String
)

object HeaderObj {
  val headers = MongoConnection()("ares_customizer")("headers")
  
  def all = headers.map(grater[Header].asObject(_)).toList
  def create(header: Header) {
    headers += grater[Header].asDBObject(header)
  }
}
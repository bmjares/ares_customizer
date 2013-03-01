package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import org.bson.types.ObjectId

import play.api.Play.current
import se.radley.plugin.salat._
import se.radley.plugin.salat.Formats._
import com.novus.salat._
import com.novus.salat.dao._
import com.novus.salat.global._

import com.mongodb.casbah.Imports._
import views._
import models._

object Headers extends Controller {
  
  val collection = mongoCollection("headers")
  val dao = new SalatDAO[Header, ObjectId](collection = collection) {}  
  
    def headerForm = Form(
    mapping(
      "id" -> ignored(new ObjectId),        
      "clientId" -> number,
      "headline" -> text,
      "headerLayout" -> text
    )(Header.apply)(Header.unapply)
  )
  
  def edit = TODO
  
  //request.queryString.map { case (k,v) => k -> v.mkString }
  def create(clientId: Int) = Action { implicit request =>
    val body = request.body
    Logger.info("clientId in headers create: "+ clientId.toString)
    headerForm.bindFromRequest.fold(
      form => BadRequest(views.html.createHeader(clientId, form)),
      header => {
        HeaderObj.create(header)
        Ok(views.html.createHeader(header.clientId, headerForm)).flashing("message" -> "Submitted")
      }
    )
  }
}
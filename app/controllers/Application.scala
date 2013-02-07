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

import com.mongodb.casbah.Imports._
import views._
import models._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("TEST"))
  }
  
//  def create = Action {
//    Ok(views.html.createForm(ClientForm(), Client.options))
//  }
  
  def clientForm(id: ObjectId = new ObjectId) = Form(
    mapping(
      "id" -> ignored(id),
      "clientId" -> number,
      "pageType" -> text
    )(Client.apply)(Client.unapply)
  )

}

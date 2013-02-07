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


object Clients extends Controller {
  def clientForm = Form(
    mapping(
      "id" -> ignored(new ObjectId),
      "clientId" -> number,
      "pageType" -> text
    )(Client.apply)(Client.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.index("ares"))
  }
  
  def save = TODO

  def create = Action { implicit request =>
    clientForm.bindFromRequest.fold(
      form => BadRequest(views.html.createForm(clientForm)),
      client => {
        ClientsObj.create(client)
        Redirect(routes.Application.index()).flashing("message" -> "Submitted")
      }
    )
  }
}
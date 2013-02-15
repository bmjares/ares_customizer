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

object Headers extends Controller {
  
    def headerForm = Form(
    mapping(
      "clientId" -> number,
      "headline" -> text,
      "headerLayout" -> text
    )(Header.apply)(Header.unapply)
  )
  
  def create = Action { implicit request =>
    val body = request.body
    Logger.info("body in headers create: "+ body.toString)
    headerForm.bindFromRequest.fold(
      form => BadRequest(views.html.createHeader(form)),
      header => {
        HeaderObj.create(header)
        Redirect(routes.Headers.create()).flashing("message" -> "Submitted")
      }
    )
  }
}
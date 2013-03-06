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
    //change 1 to _ 
    val clientsList = ClientsObj.all(1)
    Ok(views.html.index(clientsList.toString, clientForm))
  }
  
  def update = Action { implicit request =>
    clientForm.bindFromRequest.fold(
      form => BadRequest(views.html.client(form)),
      client => {
	  	ClientsObj.findClientbyClientId(client.clientId).map( client =>
	  	    ClientsObj.saveClient(client)
	  	)
        Redirect(routes.Clients.create).flashing("message" -> "Submitted")
      }
    )
  }
  
  def find(clientId: Int) = Action { implicit request =>
    ClientsObj.findClientbyClientId(clientId).map { client =>
      Ok(views.html.client(clientForm.fill(client)))
    }.getOrElse(NotFound)
  }
  
  def create = Action { implicit request =>
    clientForm.bindFromRequest.fold(
      form => BadRequest(views.html.client(form)),
      client => {
        Logger.info("access clientid: "+ client.clientId)
        ClientsObj.create(client)
        Redirect(routes.Clients.create).flashing("message" -> "Submitted")
      }
    )
  }
}
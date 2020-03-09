package com.kostya.taskboard.controllers

import com.kostya.taskboard.shared.Model.Ticket
import database.Schema
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.filters.csrf.CSRF
import slick.jdbc.JdbcProfile
import views.implicits.scalatags._

import scala.concurrent.ExecutionContext

@Singleton
class CreateTicket @Inject()(
                              protected val dbConfigProvider: DatabaseConfigProvider,
                              cc: ControllerComponents,
                              schema: Schema
                            )(
                              implicit ec: ExecutionContext
                            )
  extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  // get
  def createTicketPage() = Action { implicit request =>
    Ok(views.pages.createTicketView)
  }

  // post
  def createTicket() = Action.async { implicit request =>
    val ticket = views.forms.createTicketForm.bindFromRequest.get
    schema.insert(ticket).map(createdId => Ok(s"ID: $createdId"))
  }
}

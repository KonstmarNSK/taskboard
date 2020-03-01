package com.kostya.taskboard.controllers

import com.kostya.taskboard.shared.Model.Ticket
import com.kostya.taskboard.shared.SharedMessages
import database.Schema
import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import views.Home
import views.implicits._

import scala.concurrent.ExecutionContext

@Singleton
class Application @Inject()(
                             protected val dbConfigProvider: DatabaseConfigProvider,
                             cc: ControllerComponents,
                             schema: Schema
                           )(
                             implicit ec: ExecutionContext
                           ) extends AbstractController(cc)
  with HasDatabaseConfigProvider[JdbcProfile] {

  def index = Action.async {
    schema.init.map(_ =>
      Ok(Home.homepage(SharedMessages.itWorks)))
  }

  def createTicket(title: String, description: String) = Action.async {
    schema.insert(Ticket(title, description)).map(_ => Ok("123"))
  }

  //  def getTicket(id: Long) = Action.async{ implicit request =>
  //
  //  }
}

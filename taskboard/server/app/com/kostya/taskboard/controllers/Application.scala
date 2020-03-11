package com.kostya.taskboard.controllers

import com.kostya.taskboard.shared.Model.Ticket
import com.kostya.taskboard.shared.SharedMessages
import database.Schema
import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import views.Home
import views.implicits.scalatags._

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
      schema.getAllProjects map (all => Ok(views.pages.homepageView(SharedMessages.itWorks, all)))
  }

  def getAllTickets = Action.async {
    schema.getAllTickets map (all => Ok(all.mkString("\n")))
  }

  def getAllProjects = Action.async {
    schema.getAllProjects map (all => Ok(all.mkString("\n")))
  }

  def viewProjectBoard(projId: Long) = Action.async { implicit request =>
    schema.getProjectsTickets(projId) map { allTickets =>
      Ok(views.pages.projectBoardPage("Proj name", allTickets))
    }
  }
}

package com.kostya.taskboard.controllers

import database.Schema
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import views.implicits.scalatags._

import scala.concurrent.ExecutionContext

class CreateProject@Inject()(
                              protected val dbConfigProvider: DatabaseConfigProvider,
                              cc: ControllerComponents,
                              schema: Schema
                            )(
                              implicit ec: ExecutionContext
                            )
  extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  // get
  def createProjectPage() = Action { implicit request =>
    Ok(views.pages.createProjectView)
  }

  // post
  def createProject() = Action.async { implicit request =>
    val project = views.forms.createProjectForm.bindFromRequest.get
    schema.createProject(project) map (createdId => Ok(s"ID: $createdId"))
  }
}

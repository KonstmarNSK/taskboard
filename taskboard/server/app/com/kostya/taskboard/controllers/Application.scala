package com.kostya.taskboard.controllers

import com.kostya.taskboard.shared.SharedMessages
import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import views.Home
import views.implicits._

import scala.concurrent.ExecutionContext

@Singleton
class Application @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(
  implicit ec: ExecutionContext
) extends AbstractController(cc)
  with HasDatabaseConfigProvider[JdbcProfile] {
  import dbConfig.profile.api._

  def index = Action {
    Ok(Home.homepage(SharedMessages.itWorks))
  }
}

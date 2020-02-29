package com.kostya.taskboard.controllers

import com.kostya.taskboard.shared.SharedMessages
import javax.inject._
import play.api.mvc._
import views.Home
import views.Implicits._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(Home.homepage(SharedMessages.itWorks))
  }
}

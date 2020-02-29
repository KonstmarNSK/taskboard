package com.kostya.taskboard.controllers

import javax.inject._
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(getResponse(A("3", "2"))))
  }

  case class A(val x: String, val y: String)

  def getResponse(a: A) = a match {
    case A("1", y) => y
    case A(_, "2") => "Fgfnzgfnz"
  }
}

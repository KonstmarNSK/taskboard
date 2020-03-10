package views

import com.kostya.taskboard.shared.Model.{Project, Ticket}
import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object ProjectBoard {
  def projectBoardPage(projectName: String,
                       opened: Seq[Ticket],
                       inProcess: Seq[Ticket],
                       done: Seq[Ticket],
                       willNotDo: Seq[Ticket])(implicit request : RequestHeader) = {

    val maxSize = Seq[Int](opened.size, inProcess.size, done.size, willNotDo.size).max

    def drawRow(number : Int) = {

      def drawTicket(ticket : Ticket) =
        div(
          `class` := "card",
          div(
            `class` := "card-body",
            h5(ticket.ticketName),
            p(ticket.ticketDescription),
          ),
        )

      div(
        `class` := "row",

        div(
          `class` := "col",
          if(opened.size > number) drawTicket(opened(number)) else ""
        ),

        div(
          `class` := "col",
          if(inProcess.size > number) drawTicket(inProcess(number)) else ""
        ),

        div(
          `class` := "col",
          if(done.size > number) drawTicket(done(number)) else ""
        ),

        div(
          `class` := "col",
          if(willNotDo.size > number) drawTicket(willNotDo(number)) else ""
        ),
      )
    }

    scalatags.Text.all.html(
      head(
        title := s"Project $projectName",
        styles(paths.styles.bootstrap.min),
      ),
      body(
        div(
          `class` := "container",
          for(n <- 0 until maxSize) yield drawRow(n)
        ),
      )
    )
  }
}

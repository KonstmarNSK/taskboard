package views

import com.kostya.taskboard.shared.Model.{Project, Ticket}
import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object ProjectBoard {
  def projectBoardPage(projectName: String, tickets: Seq[Ticket]*)(implicit request : RequestHeader) = {

    val maxSize = tickets.map(_.size).max

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

      // one ticket from each seq. 1 seq - 1 column
      div(
        `class` := "row",

          for(
            ticketSeq <- tickets
          ) yield {
            div(
              `class` := "col",
              if(ticketSeq.size > number) drawTicket(ticketSeq(number)) else ""
            )
          }
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

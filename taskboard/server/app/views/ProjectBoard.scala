package views

import com.kostya.taskboard.shared.Model.Ticket
import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object ProjectBoard {
  def projectBoardPage(projectName: String, tickets: Seq[Ticket])(implicit request: RequestHeader) = {

    case class Column(name: String, tickets: Seq[Ticket])

    val ticketsByStatus = tickets.groupBy(_.state).map { case (status, tickets) => Column(status, tickets) }
    val rowsCount = ticketsByStatus.map(_.tickets.size).max

    def drawRow(number: Int) = {

      def drawTicket(ticket: Ticket) =
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

        for (
          ticketSeq <- ticketsByStatus.map(_.tickets).toSeq
        ) yield {
          div(
            `class` := "col",
            if (ticketSeq.size > number) drawTicket(ticketSeq(number)) else ""
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

          // column titles
          div(
            `class` := "row",
            for (
              status: String <- ticketsByStatus.map(_.name).toSeq
            ) yield
              div(
                `class` := "col",
                h5(status),
              ),
          ),

          // tickets
          for (n <- 0 until rowsCount) yield drawRow(n)
        ),
      )
    )
  }
}

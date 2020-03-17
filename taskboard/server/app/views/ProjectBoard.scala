package views

import com.kostya.taskboard.shared.Model.Ticket
import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object ProjectBoard {

  /**
   * Draws project board page with project's tickets grouped in columns by status
   *
   * @param projectName - name of the project
   * @param tickets - list of tickets to draw
   * @return scalatags' Tag, representing the page
   */
  def projectBoardPage(projectName: String, tickets: Seq[Ticket]) = {

    case class Column(name: String, tickets: Seq[Ticket])

    val ticketsByStatus = tickets.groupBy(_.state).map { case (status, tickets) => Column(status, tickets) }
    val rowsCount = ticketsByStatus.map(_.tickets.size).max

    /**
     * Draws a row of tickets (one ticket in column)
     *
     * @param number - number of ticket
     * @return sclatags' Tag (div), representing row
     */
    def drawRow(number: Int) = {

      /**
       * Draws a ticket
       * @param ticket - ticket to draw
       * @return scalatags' Tag (div), representing ticket
       */
      def drawTicket(ticket: Ticket) =
        div(
          `class` := "card",
          div(
            `class` := "card-body",
            h5(ticket.ticketName),
            p(ticket.ticketDescription),
          ),
        )

      // one ticket from each seq. 1 state - 1 seq - 1 column
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
        styles(links.styles.bootstrap.min),
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

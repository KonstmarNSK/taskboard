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
                       willNotDo: Seq[Ticket])(implicit request : RequestHeader) = scalatags.Text.all.html(
    head(
      title := s"Project $projectName",
      styles(paths.styles.bootstrap.min),
    ),
    body(
      div(
        `class` := "container",
        div(
          `class` := "row",
          drawTickets("Opened", opened),
          drawTickets("In process", inProcess),
          drawTickets("Done", done),
          drawTickets("Will not do", willNotDo),
        )
      ),
    )
  )

  // draws column. For example, column with opened tickets
  private[this] def drawTickets(title: String, tickets: Seq[Ticket]) = div(
    `class` := "col-md-3",

    h4(title),
    for(
      t <- tickets
    ) yield ticket(t),
  )

  private[this] def ticket(ticket: Ticket) = div(
    ticket.ticketName,
    br,
    ticket.ticketDescription
  )
}

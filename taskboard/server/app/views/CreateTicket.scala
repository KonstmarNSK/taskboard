package views

import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object CreateTicket {

  private[this] object ids {
    val ticketNameInput = "ticket-name-input"
    val ticketDescInput = "ticket-description-input"
    val projectIdInput = "project-id-input"
    val priorityInput = "ticket-priority-input"
  }

  def createTicketPage(implicit request: RequestHeader) = scalatags.Text.all.html(
    head(
      title := "Create new ticket",
      styles(paths.styles.bootstrap.min),
    ),
    body(
      div(
        `class` := "container",
        createTicketForm(request),
      ),
    )
  )

  def createTicketForm(requestHeader: RequestHeader) = div(
    `class` := "row",
    div(
      `class` := "col-md-8",
      h4("Create ticket"),

      form(
        action := paths.api.createTicket,
        method := "post",
        csrfFormElement(requestHeader),

        // ticket title
        div(
          `class` := "row",
          div(
            `class` := "col-md-6",
            label(
              `for` := ids.ticketNameInput,
              "Title: "
            ),
            input(
              id := ids.ticketNameInput,
              name := formParamNames.createTicket.ticketTitle,
              `class` := "form-control",
              `type` := "text",
              placeholder := "Some ticket name",
              required,
            )
          )
        ),

        // description
        div(
          `class` := "row",
          div(
            `class` := "col",
            label(
              `for` := ids.ticketDescInput,
              "Description: ",
            ),
            textarea(
              id := ids.ticketDescInput,
              name := formParamNames.createTicket.ticketDescription,
              `class` := "form-control",
              rows := "6",
              placeholder := "Ticket description",
              required,
            )
          )
        ),

        // priority
        div(
          `class` := "row",
          div(
            `class` := "col",
            label(
              `for` := ids.priorityInput,
              "Priority: ",
            ),
            select(
              id := ids.priorityInput,
              name := formParamNames.createTicket.priority,
              `class` := "form-control",
              required,
              option(
                selected,
                value := "LOW",
                "Low"
              ),
              option(
                value := "MEDIUM",
                "Medium"
              ),
              option(
                value := "HIGH",
                "High"
              ),
            )
          )
        ),

        // projectId
        div(
          `class` := "row",
          div(
            `class` := "col",
            label(
              `for` := ids.projectIdInput,
              "Project: ",
            ),
            select(
              id := ids.projectIdInput,
              name := formParamNames.createTicket.projectId,
              `class` := "form-control",
              required,
              option(
                selected,
                value := "1",
                "Default"
              ),
            )
          )
        ),

        // submit
        hr(
          `class` := "mb-4"
        ),
        div(
          `class` := "row",
          div(
            `class` := "col-md-4",
            button(
              `class` := "btn btn-primary btn-block",
              `type` := "submit",
              "Create ticket"
            )
          ),
        ),
      )
    )
  )
}

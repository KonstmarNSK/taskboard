package views

import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object CreateTicket {

  private[this] object ids {
    val ticketNameInput = "ticket-name-input"
    val ticketDescInput = "ticket-description-input"
  }

  def createTicketPage(implicit request : RequestHeader) = scalatags.Text.all.html(
    head(
      title := "Hello, Scala",
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

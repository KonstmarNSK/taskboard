package views

import scalatags.Text.all._

object Home {
  def homepage(message: String) = scalatags.Text.all.html(
    head(
      title := "Hello, Scala",
      styles(paths.styles.bootstrap.min),
    ),
    body(
      h2("Play and Scala.js share a same message"),
      ul(
        li(
          "Play shouts out: ",
          em(message),
        ),
        li(
          "Scala.js shouts out: ",
          em(id := "scalajsShoutOut"),
        )
      ),
      script(src := paths.scripts.main),
      div(
        `class` := "container",
        createTicket.createTicketForm,
      ),
    )
  )

  private[this] object createTicket {

    private[this] object ids {
      val ticketNameInput = "ticket-name-input"
      val ticketDescInput = "ticket-description-input"
    }

    private[Home] val createTicketForm = div(
      `class` := "row",
      div(
        `class` := "col-md-8",
        h4("Create ticket"),
        form(
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
                `class` := "form-control",
                `type` := "text",
                placeholder := "Some ticket name",
              )
            )
          ),
          div(
            `class` := "row",
            div(
              `class` := "col",
              label(
                `for` := ids.ticketDescInput,
                "Description: ",
                required,
              ),
              textarea(
                id := ids.ticketDescInput,
                `class` := "form-control",
                rows := "6",
                placeholder := "Ticket description",
                required,
              )
            )
          ),
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

  private[this] def styles(path: String) = link(
    rel := "stylesheet",
    href := path,
  )

}
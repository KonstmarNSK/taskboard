package views

import scalatags.Text.all._

object Home {
  def homepage(message: String) = scalatags.Text.all.html(
    head(
      title := "Hello, Scala",
      link(
        rel := "stylesheet",
        href := paths.styles.bootstrap.gridMin,
      ),
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
      tryingBootstrap
    )
  )

  private[this] val tryingBootstrap = div(
    `class` := "container",
    div(
      `class` := "row",
      div(
        `class` := "col",
        backgroundColor := "blue",
        "123"
      ),
      div(
        `class` := "col",
        backgroundColor := "red",
        "456"
      ),
    ),
  )
}
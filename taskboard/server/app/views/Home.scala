package views
import scalatags.Text.all._

object Home {
  def homepage(message: String) = scalatags.Text.all.html(
    head(
      title := "Hello, Scala"
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
      script(src := paths.scripts.main)
    )
  )
}
package views

import play.api.mvc.RequestHeader
import scalatags.Text.all._
import views.internal._
import views.internal.tagsFunctions._

private[views] object CreateProject {

  private[this] object ids {
    val projectNameInput = "project-name-input"
    val projectDescInput = "project-description-input"
  }

  def createProjectPage(implicit request : RequestHeader) = scalatags.Text.all.html(
    head(
      title := "Create new project",
      styles(links.styles.bootstrap.min),
    ),
    body(
      div(
        `class` := "container",
        createProjectForm,
      ),
    )
  )

  def createProjectForm(implicit requestHeader: RequestHeader) = div(
    `class` := "row",
    div(
      `class` := "col-md-8",
      h4("Create project"),

      form(
        action := links.api.createProject,
        method := "post",
        csrfFormElement,

        // project name
        div(
          `class` := "row",
          div(
            `class` := "col-md-6",
            label(
              `for` := ids.projectNameInput,
              "Name: "
            ),
            input(
              id := ids.projectNameInput,
              name := formParamNames.createProject.projectName,
              `class` := "form-control",
              `type` := "text",
              placeholder := "Some project name",
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
              `for` := ids.projectDescInput,
              "Description: ",
            ),
            textarea(
              id := ids.projectDescInput,
              name := formParamNames.createProject.projectDescription,
              `class` := "form-control",
              rows := "6",
              placeholder := "Project description",
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
              "Create project"
            )
          ),
        ),
      )
    )
  )
}

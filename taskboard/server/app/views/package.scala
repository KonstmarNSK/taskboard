import com.kostya.taskboard.shared.Model.{Project, Ticket}
import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.mvc.{Codec, RequestHeader}
import scalatags.Text.all._

/**
 *  Package object contains mappings from scalatags' tags to play's Writable,
 *  functions that return pages, forms and things that are private to this package
 */
package object views {

  object implicits {

    // map scalatags' tags into Writable to make it possible to return the pages in controllers
    object scalatags {
      implicit def contentTypeOfTag(implicit codec: Codec): ContentTypeOf[Tag] = {
        ContentTypeOf[Tag](Some(ContentTypes.HTML))
      }

      implicit def writeableOfTag(implicit codec: Codec): Writeable[Tag] = {
        Writeable(tag => codec.encode("<!DOCTYPE html>\n" + tag.render))
      }
    }
  }

  // views that can be returned from controllers
  object pages{

    def homepageView(s: String, projects: Seq[Project]) : scalatags.Text.TypedTag[String] = Home.homepage(s, projects)
    def projectBoardView(projectName: String, tickets: Seq[Ticket]) =
      ProjectBoard.projectBoardPage(projectName, tickets)

    def createTicketView(projects: Seq[Project])
                        (implicit req : RequestHeader) : scalatags.Text.TypedTag[String] = CreateTicket.createTicketPage(projects)

    def createProjectView(implicit req : RequestHeader) : scalatags.Text.TypedTag[String] = CreateProject.createProjectPage

  }

  object forms {
      import com.kostya.taskboard.shared.Model._
      import internal.formParamNames._
      import play.api.data.Forms._
      import play.api.data._

      // todo: add validation
      val createTicketForm = Form(
        mapping(
          createTicket.ticketTitle -> nonEmptyText,
          createTicket.ticketDescription -> nonEmptyText,
          createTicket.priority -> nonEmptyText,
          createTicket.projectId -> longNumber,
        )
        // id isn't in form parameters and new tickets are always opened
        (Ticket.apply(_, _, _, ticketStates.opened, _, 0L))
        (Ticket.unapply(_) map {
           case ( name,
                    desc,
                    priority,
                    state,
                    projectId,
                    id ) => (name, desc, priority, projectId) }
        )
      )

      val createProjectForm = Form(
        mapping(
          createProject.projectName -> nonEmptyText,
          createProject.projectDescription -> nonEmptyText,
        )
        // id isn't in form parameters
        (Project.apply(_, _, 0L))
        (Project.unapply(_) map { case (name, desc, id) => (name, desc) })
      )
    }

  // things that are specific for classes in this package
  private[views] object internal {

    object links {

      object scripts {
        val main = "/assets/client-fastopt.js"
      }

      object styles {

        object bootstrap {
          val min = "/assets/stylesheets/bootstrap/bootstrap.min.css"

          val grid = "/assets/stylesheets/bootstrap/bootstrap-grid.css"
          val gridMin = "/assets/stylesheets/bootstrap/bootstrap-grid.min.css"
        }
      }

      object api{
        val createTicket = "/api-rest/create-ticket"
        val createProject = "/api-rest/create-project"
      }

      def projectBoard(projId: Long) = s"/views/view-project-board?projId=$projId"
    }


    object tagsFunctions {

      def styles(path: String) = link(
        rel := "stylesheet",
        href := path,
      )

      def csrfFormElement(implicit requestHeader: RequestHeader) =
        input(
          `type` := "hidden",
          name := "csrfToken",
          // fixme: not good
          value := requestHeader.session.get("csrfToken").getOrElse("NoToken")
        )
    }

    object formParamNames{
      object createTicket{
        val ticketTitle = "title"
        val ticketDescription = "description"
        val projectId = "projectId"
        val priority = "priority"
      }

      object createProject{
        val projectName = "projectName"
        val projectDescription = "projectDescription"
      }
    }

  }
}

package database

import com.kostya.taskboard.shared.Model.{Project, Ticket}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


class Schema @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class TicketsTable(tag: Tag) extends Table[Ticket](tag, "TICKETS") {
    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def title = column[String]("TITLE")
    def description = column[String]("DESCRIPTION")

    def * = (title, description, id).mapTo[Ticket]
  }

  class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECTS") {
    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def name = column[String]("NAME")
    def description = column[String]("DESCRIPTION")

    def * = (name, description, id).mapTo[Project]
  }

  lazy val tickets = TableQuery[TicketsTable]
  lazy val projects = TableQuery[ProjectsTable]

  def createTicket(ticket: Ticket) = db.run(tickets returning tickets.map(_.id) += ticket)
  def getAllTickets: Future[Seq[Ticket]] = db.run(tickets.result)

  def createProject(project: Project) = db.run(projects returning projects.map(_.id) += project)
  def getAllProjects: Future[Seq[Project]] = db.run(projects.result)
}

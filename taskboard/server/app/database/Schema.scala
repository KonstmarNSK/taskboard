package database

import com.kostya.taskboard.shared.Model
import com.kostya.taskboard.shared.Model.{Project, Ticket, TicketPriority, TicketState, Workflow}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Schema @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                      (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  private[this] lazy val tickets = Tickets(dbConfigProvider).table
  private[this] lazy val projects = Projects(dbConfigProvider).table
  private[this] lazy val workflowTables = Workflows(dbConfigProvider).tables

  def createTicket(ticket: Ticket) = db.run(tickets returning tickets.map(_.id) += ticket)

  def getAllTickets: Future[Seq[Ticket]] = db.run(tickets.result)

  def createProject(project: Project) = db.run(projects returning projects.map(_.id) += project)

  def getAllProjects: Future[Seq[Project]] = db.run(projects.sortBy(_.name).result)

  def getProjectsTickets(projId: Long) = {
    db.run(tickets.filter(_.projectId === projId).result)
  }

  def getProjectWorkflow(projId: Long) = {

  }
}

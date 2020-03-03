package database

import com.kostya.taskboard.shared.Model.Ticket
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

  lazy val tickets = TableQuery[TicketsTable]

  def insert(ticket: Ticket) = db.run(tickets returning tickets.map(_.id) += ticket)
  def getAll: Future[Seq[Ticket]] = db.run(tickets.result)

}

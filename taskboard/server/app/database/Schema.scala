package database

import com.kostya.taskboard.shared.Model.Ticket
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext


class Schema @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class TicketsTable(tag: Tag) extends Table[Ticket](tag, "TICKETS") {
    import O._

    def id = column[Long]("id", PrimaryKey, AutoInc)
    def title = column[String]("title")
    def description = column[String]("description")

    def * = (title, description, id).mapTo[Ticket]
  }

  lazy val tickets = TableQuery[TicketsTable]

  def init = db.run(tickets.schema.create)
  def insert(ticket: Ticket) = db.run(tickets += ticket)

}

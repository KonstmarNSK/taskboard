package database

import com.kostya.taskboard.shared.Model.{Ticket, TicketPriority, TicketState}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

private[database] class Tickets(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // implicits are needed for using priority and state classes as slick's columns' types
  implicit val stateColumnType = MappedColumnType.base[TicketState, String](
    { state => state.value },    // map state to string
    { str => TicketState(str) } // map string to state
  )

  implicit val priorityColumnType = MappedColumnType.base[TicketPriority, String](
    { priority => priority.value },    // map priority to string
    { str => TicketPriority(str) } // map string to priority
  )

  class TicketsTable(tag: Tag) extends Table[Ticket](tag, "TICKETS") {

    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def title = column[String]("TITLE")
    def description = column[String]("DESCRIPTION")
    def state = column[TicketState]("STATE")
    def priority = column[TicketPriority]("PRIORITY")
    def projectId = column[Long]("PROJECT")

    def * = (title, description, priority, state, projectId, id).mapTo[Ticket]
  }

  // todo: refactor
  private object t{
    lazy val table = TableQuery[TicketsTable]
  }

  lazy val table = t.table

}

private[database] object Tickets{
  def apply(dbConfigProvider: DatabaseConfigProvider): Tickets = new Tickets(dbConfigProvider)
}
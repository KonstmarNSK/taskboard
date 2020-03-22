package database

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

private[database] class Workflows(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._


  class WorkflowTable(tag: Tag) extends Table[(Long, String, Long)](tag, "WORKFLOWS"){

    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def name = column[String]("WF_NAME")
    def initialStateId = column[Long]("INITIAL_STATE")

    override def * = (id, name, initialStateId)
  }


  class TicketPrioritiesTable(tag: Tag) extends Table[(Long, String, Long)](tag, "PRIORITIES"){

    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def name = column[String]("STR")
    def wfId = column[Long]("WORKFLOW_ID")

    override def * = (id, name, wfId)
  }


  class TicketStatesTable(tag: Tag) extends Table[(Long, String, Long)](tag, "STATES"){

    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def name = column[String]("STR")
    def wfId = column[Long]("WORKFLOW_ID")

    override def * = (id, name, wfId)
  }


  object tables{
    lazy val workflows = TableQuery[WorkflowTable]
    lazy val possiblePriorities = TableQuery[TicketPrioritiesTable]
    lazy val possibleStates = TableQuery[TicketStatesTable]
  }
}

private[database] object Workflows{
  def apply(dbConfigProvider: DatabaseConfigProvider): Workflows = new Workflows(dbConfigProvider)
}
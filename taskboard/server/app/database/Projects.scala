package database

import com.kostya.taskboard.shared.Model.Project
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

private[database] class Projects(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._


  class ProjectsTable(tag: Tag) extends Table[Project](tag, "PROJECTS") {

    import O._

    def id = column[Long]("ID", PrimaryKey, AutoInc)
    def name = column[String]("NAME")
    def description = column[String]("DESCRIPTION")
    def workflowId = column[Long]("WORKFLOW_ID")

    def * = (name, description, workflowId, id).mapTo[Project]
  }

  // todo: refactor
  private object t{
    lazy val table = TableQuery[ProjectsTable]
  }

  lazy val table = t.table
}

private[database] object Projects {
  def apply(dbConfigProvider: DatabaseConfigProvider): Projects = new Projects(dbConfigProvider)
}



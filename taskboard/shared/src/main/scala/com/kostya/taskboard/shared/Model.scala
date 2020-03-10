package com.kostya.taskboard.shared

object Model {

  object ticketStates{
    val opened = "OPENED"
    val inProcess = "IN_PROCESS"
    val done = "DONE"
    val willNotDo = "WILL_NOT_DO"
  }

  object ticketPriorities{
    val low = "LOW"
    val medium = "MEDIUM"
    val high = "HIGH"
  }

  case class Ticket(ticketName: String,
                    ticketDescription: String,
                    priority: String = ticketPriorities.low,
                    state: String = ticketStates.opened,

                    projectId: Long = 1L,
                    id: Long = 0L,
                   )

  case class Project(projectName: String, projectDescription: String, id: Long = 0L)

}

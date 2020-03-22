package com.kostya.taskboard.shared

object Model {

  case class Ticket(ticketName: String,
                    ticketDescription: String,
                    priority : TicketPriority,
                    state: TicketState,

                    projectId: Long = 1L,
                    id: Long = 0L,
                   )

  case class Project(projectName: String, projectDescription: String, workflowId: Long, id: Long = 0L)

  /**
   * Represents project's workflow.
   * Contains information about possible tickets' priorities and states
   * and whether tickets can pass from one state to another
   */
  trait Workflow{
    def initialTicketState : TicketState
    def possibleTicketStates : Seq[TicketState]
    def possibleStatesTransitions : Map[TicketState, TicketState]

    def possibleTicketPriorities : Seq[TicketPriority]
  }

  case class TicketPriority(value: String)
  case class TicketState(value: String)

}

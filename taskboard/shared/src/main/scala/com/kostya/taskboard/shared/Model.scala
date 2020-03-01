package com.kostya.taskboard.shared

object Model {
  case class Ticket(ticketName: String, ticketDescription: String, id: Long = 0L)
}

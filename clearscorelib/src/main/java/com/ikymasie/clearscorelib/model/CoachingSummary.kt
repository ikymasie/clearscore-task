package com.ikymasie.clearscorelib

data class CoachingSummary (
  val activeTodo: Boolean,
  val activeChat: Boolean,
  val numberOfTodoItems: Long,
  val numberOfCompletedTodoItems: Long,
  val selected: Boolean
)
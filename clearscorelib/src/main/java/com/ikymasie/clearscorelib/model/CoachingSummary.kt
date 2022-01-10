package com.ikymasie.clearscorelib

import java.io.Serializable


data class CoachingSummary (
  val activeTodo: Boolean,
  val activeChat: Boolean,
  val numberOfTodoItems: Long,
  val numberOfCompletedTodoItems: Long,
  val selected: Boolean
): Serializable
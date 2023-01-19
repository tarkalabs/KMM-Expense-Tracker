package com.tarkalabs.expensetracker.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Expense(
  val id: String,
  val amount: Float,
  val category: Category,
  val expenseDate: LocalDate,
  val note: String?,
  val createdAt: LocalDateTime
)
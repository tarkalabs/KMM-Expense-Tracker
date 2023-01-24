package com.tarkalabs.expensetracker.domain

data class Expense(
  val id: String,
  val amount: Float,
  val category: Category,
  val expenseDate: Long,
  val note: String?,
  val createdAt: Long
)
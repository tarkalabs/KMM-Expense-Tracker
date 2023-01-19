package com.tarkalabs.expensetracker.data

import com.tarkalabs.expensetracker.db.ExpenseDb
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.domain.Expense
import com.tarkalabs.expensetracker.domain.UUID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

class ExpenseRepository(private val databaseHelper: DatabaseHelper) {
  suspend fun addExpense(
    amount: Float,
    category: Category,
    note: String?,
    expenseDate: LocalDate
  ) {
    databaseHelper.addExpense(
      id = UUID().toString(),
      amount = amount,
      category = category.name,
      expenseDate = expenseDate.atStartOfDayIn(
        TimeZone.currentSystemDefault(),
      ).toEpochMilliseconds(),
      note = note,
    )
  }
}

fun ExpenseDb.map(): Expense {
  return Expense(
    id = id, category = Category.valueOf(category), amount = amount.toFloat(),
    expenseDate = Instant.fromEpochMilliseconds(expense_date).toLocalDateTime(
      TimeZone.currentSystemDefault()
    ).date, note = note, createdAt = Instant.fromEpochMilliseconds(created_at)
    .toLocalDateTime(TimeZone.currentSystemDefault())
  )
}
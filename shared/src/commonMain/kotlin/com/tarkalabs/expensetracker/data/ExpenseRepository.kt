package com.tarkalabs.expensetracker.data

import com.tarkalabs.expensetracker.db.ExpenseDb
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.domain.Expense
import com.tarkalabs.expensetracker.domain.UUID
import com.tarkalabs.expensetracker.ext.toEpochMillis
import com.tarkalabs.expensetracker.ext.toLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

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
      expenseDate = expenseDate.toEpochMillis(),
      note = note,
    )
  }

  fun getExpenses(): Flow<List<Expense>> {
    return databaseHelper.getAllExpenses().map { expenseList -> expenseList.map { it.map() } }
  }
}

fun ExpenseDb.map(): Expense {
  return Expense(
    id = id, category = Category.valueOf(category), amount = amount.toFloat(),
    expenseDate = Instant.fromEpochMilliseconds(expense_date).toLocalDate(), note = note,
    createdAt = Instant.fromEpochMilliseconds(created_at)
  )
}
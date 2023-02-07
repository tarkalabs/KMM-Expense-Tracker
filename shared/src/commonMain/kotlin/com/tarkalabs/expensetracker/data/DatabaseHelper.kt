package com.tarkalabs.expensetracker.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.tarkalabs.expensetracker.ExpensesDb
import com.tarkalabs.expensetracker.db.ExpenseDb
import com.tarkalabs.expensetracker.domain.ExpenseType
import com.tarkalabs.expensetracker.sqldelight.transactionWithContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

class DatabaseHelper(
  sqlDriver: SqlDriver,
  private val backgroundDispatcher: CoroutineDispatcher,
) {
  private val dbRef: ExpensesDb = ExpensesDb(sqlDriver)

  suspend fun addExpense(
    id: String,
    amount: Float,
    category: String,
    expenseDate: Long,
    note: String?
  ) {
    dbRef.transactionWithContext(backgroundDispatcher) {
      dbRef.expenseQueries.add(
        id = id,
        category = category,
        amount = amount.toDouble(),
        expense_date = expenseDate,
        note = note,
        created_at = Clock.System.now().toEpochMilliseconds()
      )
    }
  }

  fun getAllExpenses(): Flow<List<ExpenseDb>> {
    return dbRef.expenseQueries.getAll().asFlow().mapToList().flowOn(backgroundDispatcher)
  }

  suspend fun deleteAll() {
    dbRef.expenseQueries.deleteAll()
  }
}
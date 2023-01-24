package com.tarkalabs.expensetracker.data

import app.cash.turbine.test
import com.tarkalabs.expensetracker.domain.Category.DINE_OUT
import com.tarkalabs.expensetracker.ext.toEpochMillis
import com.tarkalabs.expensetracker.ext.toLocalDate
import com.tarkalabs.expensetracker.testDbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock.System
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DatabaseHelperTest {
  private lateinit var dbHelper: DatabaseHelper

  @BeforeTest
  fun setup() {
    runBlocking {
      dbHelper = DatabaseHelper(
        testDbConnection(), Dispatchers.Default
      )
      dbHelper.deleteAll()
    }
  }

  @Test fun `Add Expenses Success`() = runTest {
    dbHelper.addExpense(
      "id", 100.0f, DINE_OUT.toString(),
      System.now().toLocalDate().toEpochMillis(), null
    )
    dbHelper.getAllExpenses().test {
      assertEquals("id", awaitItem().last().id)
    }
  }
}
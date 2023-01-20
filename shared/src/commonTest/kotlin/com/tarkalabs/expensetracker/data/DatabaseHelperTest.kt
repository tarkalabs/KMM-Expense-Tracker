package com.tarkalabs.expensetracker.data

import app.cash.turbine.test
import com.tarkalabs.expensetracker.domain.Category.DINE_OUT
import com.tarkalabs.expensetracker.domain.UUID
import com.tarkalabs.expensetracker.testDbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DatabaseHelperTest {
  private lateinit var dbHelper: DatabaseHelper

  @OptIn(ExperimentalCoroutinesApi::class) @BeforeTest fun setup() = runTest {
    dbHelper = DatabaseHelper(
      testDbConnection(), Dispatchers.Default
    )
  }

  @Test fun `Add Expenses Success`() = runTest {
    dbHelper.addExpense(
      "id", 100.0f, DINE_OUT.toString(),
      System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.atStartOfDayIn(
        TimeZone.currentSystemDefault()
      ).toEpochMilliseconds(), null
    )
    dbHelper.getAllExpenses().test {
      assertEquals("id", awaitItem().first().id)
    }
  }
}
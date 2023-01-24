package com.tarkalabs.expensetracker.presentation

import app.cash.turbine.test
import com.tarkalabs.expensetracker.data.DatabaseHelper
import com.tarkalabs.expensetracker.data.ExpenseRepository
import com.tarkalabs.expensetracker.domain.Category.RENT
import com.tarkalabs.expensetracker.testDbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock.System
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AddExpenseViewModelTest {
  private lateinit var addExpenseViewModel: AddExpenseViewModel

  @BeforeTest
  fun setup() {
    runBlocking {
      val dbHelper = DatabaseHelper(
        testDbConnection(), Dispatchers.Default
      )
      dbHelper.deleteAll()
      addExpenseViewModel = AddExpenseViewModel(ExpenseRepository(dbHelper))
    }
  }

  @Test
  fun `add expense emits view state with adding true`() = runTest {
    addExpenseViewModel.addExpense(1.0f, RENT, System.now().toEpochMilliseconds(), null)
    addExpenseViewModel.addExpenseState.test {
      assertTrue { awaitItem().adding }
    }
  }

  @Test
  fun `After successful addition do not emit any new UI State`() = runTest {
    addExpenseViewModel.addExpense(1.0f, RENT, System.now().toEpochMilliseconds(), null)
    addExpenseViewModel.addExpenseState.test {
      skipItems(1)
      expectNoEvents()
    }
  }
}
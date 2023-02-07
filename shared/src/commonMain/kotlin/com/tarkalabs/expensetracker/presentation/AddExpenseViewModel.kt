package com.tarkalabs.expensetracker.presentation

import com.tarkalabs.expensetracker.data.ExpenseRepository
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.domain.Category.RENT
import com.tarkalabs.expensetracker.domain.Expense
import com.tarkalabs.expensetracker.ext.toLocalDate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class AddExpenseViewModel(private val expenseRepository: ExpenseRepository) : BaseViewModel() {

  private val mutableAddExpenseState: MutableStateFlow<AddExpenseState> =
    MutableStateFlow(AddExpenseState())

  val addExpenseState: StateFlow<AddExpenseState> = mutableAddExpenseState
  suspend fun addExpense(
    amount: Float,
    category: Category,
    expenseDate: LocalDate,
    note: String?,
  ) {
    mutableAddExpenseState.tryEmit(AddExpenseState(adding = true))
    try {
      expenseRepository.addExpense(amount, category, note, expenseDate)
    } catch (e: Exception) {
      Napier.e("Adding expense failed", e, "AddExpenseVM")
      mutableAddExpenseState.tryEmit(AddExpenseState(error = "Adding expense failed"))
    }
  }

  fun getDefaultExpense() = Expense("", 1.0f, RENT, System.now().toLocalDate(), null, System.now())
}

data class AddExpenseState(
  val adding: Boolean = false,
  val error: String? = null,
)

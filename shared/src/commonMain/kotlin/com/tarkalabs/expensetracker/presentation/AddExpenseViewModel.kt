package com.tarkalabs.expensetracker.presentation

import com.tarkalabs.expensetracker.data.ExpenseRepository
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.ext.toLocalDate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Instant

class AddExpenseViewModel(private val expenseRepository: ExpenseRepository) : BaseViewModel() {

  private val mutableAddExpenseState: MutableStateFlow<AddExpenseState> =
    MutableStateFlow(AddExpenseState())

  val addExpenseState: StateFlow<AddExpenseState> = mutableAddExpenseState
  suspend fun addExpense(
    amount: Float,
    category: Category,
    expenseDateInMs: Long,
    note: String?,
  ) {
    mutableAddExpenseState.tryEmit(AddExpenseState(adding = true))
    try {
      expenseRepository.addExpense(
        amount, category, note, Instant.fromEpochMilliseconds(expenseDateInMs).toLocalDate()
      )
    } catch (e: Exception) {
      Napier.e("Adding expense failed", e, "AddExpenseVM")
      mutableAddExpenseState.tryEmit(AddExpenseState(error = "Adding expense failed"))
    }
  }
}

data class AddExpenseState(
  val adding: Boolean = false,
  val error: String? = null,
)

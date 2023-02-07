package com.tarkalabs.expensetracker.presentation

import com.tarkalabs.expensetracker.data.ExpenseRepository
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.domain.Expense
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class ViewExpensesViewModel(expenseRepository: ExpenseRepository) : BaseViewModel() {

  var viewState = expenseRepository.getExpenses()
    .map { ViewExpensesState(loading = false, error = null, expenses = it) }
    .onStart { emit(ViewExpensesState(loading = true, error = null, expenses = emptyList())) }

  class ViewExpensesState(
    val loading: Boolean,
    val error: String?,
    val expenses: List<Expense>
  )

  class AggregatedExpense(
    val category: Category,
    val totalAmount: Float,
    val numberOfExpense: Int
  )
}
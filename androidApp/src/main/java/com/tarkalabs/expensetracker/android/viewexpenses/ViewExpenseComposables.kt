package com.tarkalabs.expensetracker.android.viewexpenses

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tarkalabs.expensetracker.domain.Expense
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel.ViewExpensesState
import kotlinx.datetime.LocalDate

@Composable fun ViewExpensesScreen(
  viewModel: ViewExpensesViewModel,
  onAddExpensePress: () -> Unit
) {
  val screenState =
    viewModel.viewState.collectAsState(ViewExpensesState(loading = true, null, emptyList())).value

  val scaffoldState = rememberScaffoldState()
  Scaffold(scaffoldState = scaffoldState) { paddingValues ->
    Column {
      TopAppBar(title = { Text(text = "Expenses") })

      if (!screenState.error.isNullOrBlank()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
          scaffoldState.snackbarHostState.showSnackbar(
            message = screenState.error.orEmpty()
          )
        }
      }

      Box(
        modifier = Modifier.padding(
          if (paddingValues == PaddingValues(0.dp)) PaddingValues(8.dp) else paddingValues
        )
      ) {
        ViewExpenseScreenContent(
          expenses = screenState.expenses,
          dateFormatter = LocalDate::toString,
        )
        FloatingActionButton(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp) // normal 16dp of padding for FABs
            .navigationBarsPadding(), // padding for navigation bar, onClick = onAddExpensePress) {
          onClick = onAddExpensePress
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "Add Expense")
        }
      }
    }
  }
}

@Composable fun ViewExpenseScreenContent(
  modifier: Modifier = Modifier,
  expenses: List<Expense>,
  dateFormatter: (localDate: LocalDate) -> String,
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
  ) {
    items(expenses.size) {
      val expense = expenses[it]
      Card(
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth(),
        backgroundColor = Color.LightGray
      ) {
        Column(
          modifier = Modifier
            .padding(8.dp)
        ) {
          Text(text = "Spent on: ${expense.category.name}")
          Text(text = "Amount: ${expense.amount}")
          Text(text = "Spent an: ${dateFormatter(expense.expenseDate)}")
          Text(text = "Note: ${expense.note}")
        }
      }
    }
  }
}

package com.tarkalabs.expensetracker.android.viewexpenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.domain.Expense
import com.tarkalabs.expensetracker.ext.toLocalDate
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel.ViewExpensesState
import kotlinx.datetime.Clock.System
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
        modifier = Modifier
          .background(Color.LightGray)
          .padding(
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
    modifier = modifier.fillMaxSize()
  ) {
    items(expenses.size) {
      val expense = expenses[it]
      ExpenseCard(expense = expense, dateFormatter = dateFormatter)
    }
  }
}

private val categoryEmojis: Map<Category, String> = mapOf(
  Category.GROCERY to "🛒",
  Category.RENT to "🏠",
  Category.UTILITY to "🧾",
  Category.TRANSPORTATION to "🚗",
  Category.DINE_OUT to "🍽️",
  Category.ENTERTAINMENT to "🕹",
)

@Composable fun ExpenseCard(
  expense: Expense,
  dateFormatter: (localDate: LocalDate) -> String,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
    elevation = 2.dp,
  ) {
    Row(modifier = Modifier.padding(8.dp)) {
      Text(
        text = categoryEmojis.get(expense.category) ?: "",
        modifier = Modifier
          .align(alignment = Alignment.CenterVertically)
          .background(color = Color(0xFF008080), shape = CircleShape)
          .size(48.dp)
          .wrapContentSize(align = Alignment.Center), fontSize = 18.sp
      )
      Column(Modifier.padding(start = 8.dp)) {
        Text(
          text = expense.category.name.replace("_", " ")
            .lowercase()
            .replaceFirstChar { it.uppercaseChar() },
          style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        )
        Text(text = "$ ${"%.2f".format(expense.amount)}")
        Text(text = dateFormatter(expense.expenseDate), style = MaterialTheme.typography.caption)
        if (!expense.note.isNullOrBlank()) {
          Text(
            text = expense.note.orEmpty(), modifier = Modifier
            .background(
              color = Color(0xFF3EB489), shape = RoundedCornerShape(percent = 50)
            )
            .padding(horizontal = 8.dp)
          )
        }
      }
    }
  }
}

@Composable @Preview fun ExpenseCardPreview() {
  ExpenseCard(
    expense = Expense(
      id = "1", amount = 20f, category = Category.RENT, expenseDate = System.now().toLocalDate(),
      "Sample Note", createdAt = System.now()
    )
  ) {
    it.toString()
  }
}

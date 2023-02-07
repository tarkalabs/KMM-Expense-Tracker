package com.tarkalabs.expensetracker.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tarkalabs.expensetracker.android.addexpense.AddExpenseActivity
import com.tarkalabs.expensetracker.android.viewexpenses.ViewExpensesScreen
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

  private val viewExpenseViewModel: ViewExpensesViewModel by viewModel()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApplicationTheme {
        ViewExpensesScreen(viewModel = viewExpenseViewModel,
          onAddExpensePress = {
            startActivity(
              Intent(this, AddExpenseActivity::class.java)
            )
          })
      }
    }
  }
}
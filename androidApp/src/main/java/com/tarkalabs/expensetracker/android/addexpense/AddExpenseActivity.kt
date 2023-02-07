package com.tarkalabs.expensetracker.android.addexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tarkalabs.expensetracker.android.MyApplicationTheme
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddExpenseActivity : ComponentActivity() {

  private val addExpenseViewModel: AddExpenseViewModel by viewModel()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApplicationTheme {
        AddExpenseScreen(viewModel = addExpenseViewModel, onBackPress = { finish() })
      }
    }
  }
}
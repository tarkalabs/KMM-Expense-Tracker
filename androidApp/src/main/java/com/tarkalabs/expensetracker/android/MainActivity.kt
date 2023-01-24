package com.tarkalabs.expensetracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tarkalabs.expensetracker.android.addexpense.AddExpenseScreen
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

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

@Composable
fun GreetingView(text: String) {
  Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
  MyApplicationTheme {
    GreetingView("Hello, Android!")
  }
}

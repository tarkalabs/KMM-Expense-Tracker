package com.tarkalabs.expensetracker.android

import android.app.Application
import android.content.Context
import com.tarkalabs.expensetracker.di.initKoin
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ExpenseTrackerApp : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin(viewModelsModule = module {
      viewModel {
        AddExpenseViewModel(get())
      }
      viewModel {
        ViewExpensesViewModel(get())
      }
    }, appModule = module {
      single<Context> { this@ExpenseTrackerApp }
    })
    Napier.base(DebugAntilog())
  }
}
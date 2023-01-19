package com.tarkalabs.expensetracker.android

import android.app.Application
import android.content.Context
import com.tarkalabs.expensetracker.di.initKoin
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ExpenseTrackerApp : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin(viewModelsModule = module {
      viewModel {
        AddExpenseViewModel(get())
      }
    }, appModule = module {
      single<Context> { this@ExpenseTrackerApp }
    })
  }
}
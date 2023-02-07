package com.tarkalabs.expensetracker.di

import com.tarkalabs.expensetracker.data.DatabaseHelper
import com.tarkalabs.expensetracker.data.ExpenseRepository
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import com.tarkalabs.expensetracker.presentation.ViewExpensesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

object Modules {
  val repositories = module {
    factory { ExpenseRepository(get()) }
  }

  val viewModels = module {
    factory { AddExpenseViewModel(get()) }
    factory { ViewExpensesViewModel(get()) }
  }

  val core = module {
    single { DatabaseHelper(get(), Dispatchers.Default) }
  }
}

fun initKoin(
  appModule: Module = module { },
  core: Module = Modules.core,
  repositoriesModule: Module = Modules.repositories,
  viewModelsModule: Module = Modules.viewModels,
): KoinApplication = startKoin {
  modules(
    appModule,
    core,
    repositoriesModule,
    viewModelsModule,
    platformModule,
  )
}
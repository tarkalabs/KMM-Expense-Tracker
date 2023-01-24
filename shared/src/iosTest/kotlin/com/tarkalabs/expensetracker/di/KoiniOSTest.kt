package com.tarkalabs.expensetracker.di

import com.squareup.sqldelight.db.SqlDriver
import com.tarkalabs.expensetracker.testDbConnection
import org.koin.dsl.module

actual val testPlatformModule = module {
  factory<SqlDriver> {
    testDbConnection()
  }
}
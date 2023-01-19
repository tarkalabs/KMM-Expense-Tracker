package com.tarkalabs.expensetracker

import co.touchlab.sqliter.DatabaseConfiguration
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection

internal actual fun testDbConnection(): SqlDriver {
  val schema = ExpensesDb.Schema
  return NativeSqliteDriver(
    DatabaseConfiguration(
      name = "ExpenseDb",
      version = schema.version,
      create = { connection ->
        wrapConnection(connection) { schema.create(it) }
      },
      upgrade = { connection, oldVersion, newVersion ->
        wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
      },
      inMemory = true
    )
  )
}
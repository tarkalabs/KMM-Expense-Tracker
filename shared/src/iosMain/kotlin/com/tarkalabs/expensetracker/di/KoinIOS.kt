package com.tarkalabs.expensetracker.di

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.tarkalabs.expensetracker.ExpensesDb
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

object KoinIOS {
  fun initialize(): KoinApplication = initKoin()
}

actual val platformModule = module {
  single<SqlDriver> {
    NativeSqliteDriver(ExpensesDb.Schema, "ExpenseDb")
  }
}

fun Koin.get(objCClass: ObjCClass): Any {
  val kClazz = getOriginalKotlinClass(objCClass)!!
  return get(kClazz, null, null)
}

fun Koin.get(
  objCClass: ObjCClass,
  qualifier: Qualifier?,
  parameter: Any
): Any {
  val kClazz = getOriginalKotlinClass(objCClass)!!
  return get(kClazz, qualifier) { parametersOf(parameter) }
}
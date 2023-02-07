package com.tarkalabs.expensetracker.utils

import com.tarkalabs.expensetracker.ext.toLocalDate
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinInstant
import platform.Foundation.NSDate

fun NSDate.toInstant(): Instant {
  return toKotlinInstant()
}

fun NSDate.toDefaultTzLocalDate(): LocalDate {
  return toKotlinInstant().toLocalDate()
}
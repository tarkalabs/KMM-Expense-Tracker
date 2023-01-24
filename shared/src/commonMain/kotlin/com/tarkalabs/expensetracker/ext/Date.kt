package com.tarkalabs.expensetracker.ext

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun LocalDate.toEpochMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
  return atStartOfDayIn(timeZone).toEpochMilliseconds()
}

fun Instant.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate =
  toLocalDateTime(timeZone).date

fun Instant.toLocalTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime =
  toLocalDateTime(timeZone).time
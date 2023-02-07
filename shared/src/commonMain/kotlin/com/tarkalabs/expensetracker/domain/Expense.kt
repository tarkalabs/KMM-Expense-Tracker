@file:OptIn(ExperimentalObjCRefinement::class)

package com.tarkalabs.expensetracker.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlin.experimental.ExperimentalObjCRefinement

data class Expense(
  val id: String,
  val amount: Float,
  val category: Category,
  val expenseDate: LocalDate,
  val note: String?,
  val createdAt: Instant,
)
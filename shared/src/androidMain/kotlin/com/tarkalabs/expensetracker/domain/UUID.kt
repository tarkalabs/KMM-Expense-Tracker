package com.tarkalabs.expensetracker.domain

actual class UUID actual constructor() {
  private val uuid = java.util.UUID.randomUUID()
  actual override fun toString(): String {
    return uuid.toString()
  }
}
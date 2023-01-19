package com.tarkalabs.expensetracker.domain

import platform.Foundation.NSUUID

actual class UUID actual constructor() {
  private val uuid = NSUUID()
  actual override fun toString(): String {
    return uuid.UUIDString
  }
}
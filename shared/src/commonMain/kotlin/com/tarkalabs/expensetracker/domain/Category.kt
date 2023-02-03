package com.tarkalabs.expensetracker.domain

enum class Category {
  GROCERY,
  UTILITY,
  RENT,
  TRANSPORTATION,
  DINE_OUT,
  ENTERTAINMENT;


  companion object {
    // Helper to convert KotlinArray to List which translated to iOS Array
    fun getCategoryAsList(): List<Category> {
      return values().toList()
    }

    fun getCategoryFromName(name: String): Category {
      return Category.valueOf(name)
    }
  }
}
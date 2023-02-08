package com.tarkalabs.expensetracker.domain

enum class Category {
  GROCERY,
  UTILITY,
  RENT,
  TRANSPORTATION,
  DINE_OUT,
  ENTERTAINMENT;
  fun getFormattedName(): String {
    val words = name.lowercase().replace("_", " ").split(' ')
    return (words.joinToString(separator = " ") { word -> word.replaceFirstChar { it.uppercase() } })
  }
  companion object {
    fun getCategoryFromName(name: String): Category {
      return Category.valueOf(name)
    }
  }
}
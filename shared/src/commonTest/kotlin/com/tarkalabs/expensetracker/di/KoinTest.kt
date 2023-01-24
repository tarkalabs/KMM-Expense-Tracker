package com.tarkalabs.expensetracker.di

import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

expect val testPlatformModule: Module

class DITest {

  @Test
  fun testAllModules() {
    koinApplication {
      modules(
        Modules.core,
        Modules.repositories,
        Modules.viewModels,
        testPlatformModule
      )
    }.checkModules()
  }

  @AfterTest
  fun tearDown() {
    stopKoin()
  }
}
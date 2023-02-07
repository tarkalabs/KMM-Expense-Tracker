package com.tarkalabs.expensetracker.android.addexpense

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.SnackbarResult.Dismissed
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.model.KalendarType
import com.tarkalabs.expensetracker.domain.Category
import com.tarkalabs.expensetracker.ext.toEpochMillis
import com.tarkalabs.expensetracker.ext.toLocalDate
import com.tarkalabs.expensetracker.presentation.AddExpenseState
import com.tarkalabs.expensetracker.presentation.AddExpenseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@Composable fun AddExpenseScreen(
  viewModel: AddExpenseViewModel,
  onBackPress: () -> Unit,
) {
  val lifecycleOwner = LocalLifecycleOwner.current

  val lifecycleAwareFlow = remember(viewModel.addExpenseState, lifecycleOwner) {
    viewModel.addExpenseState.flowWithLifecycle(lifecycleOwner.lifecycle)
  }

  val screenState by lifecycleAwareFlow.collectAsState(viewModel.addExpenseState.value)
  val scaffoldState = rememberScaffoldState()
  Scaffold(scaffoldState = scaffoldState) { paddingValues ->
    Column {
      TopAppBar(navigationIcon = {
        IconButton(onClick = { onBackPress() }) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
      }, title = { Text(text = "Add Expense") })

      var showAmountError by remember { mutableStateOf(false) }
      var showCategoryError by remember { mutableStateOf(false) }
      if (showAmountError || showCategoryError) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
          val result = scaffoldState.snackbarHostState.showSnackbar(
            message = if (showAmountError) "Please enter amount" else "Please select category"
          )

          fun hideSnackBar() {
            showAmountError = false
            showCategoryError = false
          }
          when (result) {
            Dismissed -> hideSnackBar()
            ActionPerformed -> hideSnackBar()
          }
        }
      }

      AddExpenseScreenContent(
        modifier = Modifier.padding(
          if (paddingValues == PaddingValues(0.dp)) PaddingValues(8.dp) else paddingValues
        ),
        screenState = screenState,
        dateFormatter = LocalDate::toString,
      ) { amount, category, expenseDate, note ->

        val amountInFloat = amount.toFloatOrNull()
        if (amountInFloat == null) {
          showAmountError = true
          return@AddExpenseScreenContent
        }

        if (category == null) {
          showCategoryError = true
          return@AddExpenseScreenContent
        }
        lifecycleOwner.lifecycleScope.launch {
          viewModel.addExpense(
            amount = amountInFloat,
            category = category,
            expenseDate = expenseDate,
            note = note.ifBlank { null },
          )
          onBackPress()
        }
      }
    }
  }
}

@Composable fun AddExpenseScreenContent(
  modifier: Modifier = Modifier,
  screenState: AddExpenseState,
  dateFormatter: (localDate: LocalDate) -> String,
  onSave: (amount: String, category: Category?, expenseDate: LocalDate, note: String) -> Unit,
) {
  Column(
    modifier = modifier
      .background(color = MaterialTheme.colors.background)
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    Log.d("AddScreenState", screenState.toString())
    var amount by remember { mutableStateOf("") }
    var category: Category? by remember { mutableStateOf(null) }
    var expenseDate: LocalDate by remember { mutableStateOf(Clock.System.now().toLocalDate()) }
    var note: String by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
      ExpenseDateDialog(expenseDate, onDateSelected = { selectedDate ->
        expenseDate = selectedDate
        showDialog = false
      }) {
        showDialog = false
      }
    }
    TextField(value = amount, onValueChange = { amount = it },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      singleLine = true,
      modifier = Modifier.fillMaxWidth(), label = {
      Text(text = "Amount")
    })
    Spacer(modifier = Modifier.height(8.dp))
    CategorySelector(
      defaultCategory = category, onSelected = { selectedCategory -> category = selectedCategory })
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = dateFormatter(expenseDate), onValueChange = {}, readOnly = true,
      modifier = Modifier
        .fillMaxWidth()
        .clickable {
          showDialog = true
        },
      trailingIcon = {
        IconButton(onClick = { showDialog = true }) {
          Icon(
            imageVector = Icons.Default.DateRange, contentDescription = "Expense Date Selector"
          )
        }
      },
      label = {
        Text(text = "Expense Date")
      },
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = note, onValueChange = { note = it },
      modifier = Modifier.fillMaxWidth(),
      label = { Text(text = "Note") },
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(
      modifier = Modifier
        .fillMaxWidth(),
      enabled = !screenState.adding,
      onClick = {
        onSave(amount, category, expenseDate, note)
      }) {
      Text(text = if (screenState.adding) "Adding..." else "Add")
    }
  }
}

@Composable fun ExpenseDateDialog(
  selectedDate: LocalDate? = null,
  onDateSelected: (localDate: LocalDate) -> Unit,
  onDismiss: () -> Unit,
) {
  Dialog(onDismissRequest = { onDismiss() }) {
    Kalendar(kalendarType = KalendarType.Firey, onCurrentDayClick = { day, _ ->
      onDateSelected(day.localDate)
    }, takeMeToDate = selectedDate)
  }
}

@OptIn(ExperimentalMaterialApi::class) @Composable fun CategorySelector(
  defaultCategory: Category? = null,
  onSelected: (category: Category) -> Unit,
) {
  Log.d("CategorySelector", "defaultCategory: $defaultCategory")
  Column(
    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    val categories = Category.values()
    var expanded by remember {
      mutableStateOf(false)
    }

    fun categoryText(category: Category?): String {
      return category?.name.orEmpty().lowercase().replaceFirstChar { it.uppercase() }
    }

    var selectedCategoryText by remember {
      mutableStateOf("")
    }
    selectedCategoryText = categoryText(defaultCategory)

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
      TextField(modifier = Modifier.fillMaxWidth(), value = selectedCategoryText,
        onValueChange = {}, readOnly = true, label = {
        Text(text = "Category")
      }, trailingIcon = {
        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
      })
      ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        categories.forEach { category ->
          DropdownMenuItem(onClick = {
            selectedCategoryText = categoryText(category)
            onSelected(category)
            expanded = false
          }) {
            Text(text = categoryText(category))
          }
        }
      }
    }
  }
}

@Composable @Preview fun IdleAddExpenseScreen() {
  AddExpenseScreenContent(
    screenState = AddExpenseState(), onSave = { _, _, _, _ -> },
    dateFormatter = LocalDate::toString
  )
}


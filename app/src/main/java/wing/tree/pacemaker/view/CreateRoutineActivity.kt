package wing.tree.pacemaker.view

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import wing.tree.pacemaker.data.extension.amPm
import wing.tree.pacemaker.data.extension.date
import wing.tree.pacemaker.data.extension.hour
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.millisecond
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.month
import wing.tree.pacemaker.data.extension.second
import wing.tree.pacemaker.data.extension.year
import wing.tree.pacemaker.domain.constant.EMPTY
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.extension.float
import wing.tree.pacemaker.domain.extension.intOrNull
import wing.tree.pacemaker.domain.extension.isZero
import wing.tree.pacemaker.extension.julianDay
import wing.tree.pacemaker.model.Time
import wing.tree.pacemaker.ui.states.CreateRoutineUiState
import wing.tree.pacemaker.ui.theme.PacemakerTheme
import wing.tree.pacemaker.viewmodel.CreateRoutineViewModel

@AndroidEntryPoint
class CreateRoutineActivity : ComponentActivity() {
    private val viewModel by viewModels<CreateRoutineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PacemakerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    Content(
                        uiState = uiState,
                        onCancelClick = {
                            finish()
                        },
                        onCreateClick = {
                            lifecycleScope.launch {
                                viewModel.create()
                                finish()
                            }
                        },
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    uiState: CreateRoutineUiState,
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier) {
            TextField(
                value = uiState.title.value,
                onValueChange = {
                    uiState.title.value = it
                },
                placeholder = {
                    Text("Title")
                },
            )

            TextField(
                value = uiState.description.value,
                onValueChange = {
                    uiState.description.value = it
                },
            )
        }

        StartDay(uiState = uiState)
        EndDay(uiState = uiState)
        Begin(uiState = uiState)
        End(uiState = uiState)
        Reminder(uiState = uiState)

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
        ) {
            ElevatedButton(onClick = onCancelClick) {
                Text(text = "Cancel")
            }

            ElevatedButton(onClick = onCreateClick) {
                Text(text = "Create")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartDay(
    uiState: CreateRoutineUiState,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()

    var isInEditMode by remember {
        mutableStateOf(false)
    }

    if (isInEditMode) {
        DatePickerDialog(
            onDismissRequest = {
                isInEditMode = false               
            }, confirmButton = {
                ElevatedButton(
                    onClick = {
                        val julianDay = datePickerState.selectedDateMillis?.julianDay

                        uiState.startDay.value = julianDay ?: Calendar.getInstance().julianDay
                        isInEditMode = false
                    },
                ) {
                    Text(text = "Confirm")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Row(
        modifier = modifier.clickable {
            if (isInEditMode.not()) {
                isInEditMode = true
            }
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.DateRange,
            contentDescription = null,
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            val calendar = Calendar.getInstance().apply {
                julianDay = uiState.startDay.value
            }

            val startDay = with(calendar) {
                "$year/${month.inc()}/$date"
            }

            Text(text = startDay)
            Text(text = "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EndDay(
    uiState: CreateRoutineUiState,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()

    var isInEditMode by remember {
        mutableStateOf(false)
    }

    if (isInEditMode) {
        DatePickerDialog(
            onDismissRequest = {
                isInEditMode = false
            }, confirmButton = {
                ElevatedButton(
                    onClick = {
                        val julianDay = datePickerState.selectedDateMillis?.julianDay

                        uiState.endDay.value = julianDay ?: Calendar.getInstance().julianDay
                        isInEditMode = false
                    },
                ) {
                    Text(text = "Confirm")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Row(
        modifier = modifier.clickable {
            if (isInEditMode.not()) {
                isInEditMode = true
            }
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.DateRange,
            contentDescription = null,
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            val calendar = Calendar.getInstance().apply {
                julianDay = uiState.endDay.value
            }

            val endDay = with(calendar) {
                "$year/${month.inc()}/$date"
            }

            Text(text = endDay)
            Text(text = "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Begin(
    uiState: CreateRoutineUiState,
    modifier: Modifier = Modifier,
) {
    val timePickerState = rememberTimePickerState()

    var isInEditMode by remember {
        mutableStateOf(false)
    }

    if (isInEditMode) {
        Dialog(
            onDismissRequest = {
                isInEditMode = false
            }) {
            Column {
                TimePicker(state = timePickerState)
                ElevatedButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        val begin = Calendar.getInstance().apply {
                            second = ZERO
                            millisecond = ZERO

                            this.hourOfDay = hour
                            this.minute = minute
                        }

                        uiState.begin.value = Time(
                            hourOfDay = begin.hourOfDay,
                            minute = begin.minute,
                        )

                        isInEditMode = false
                    },
                ) {
                    Text(text = "Confirm")
                }
            }

        }

    }

    val begin = uiState.begin.value
    val calendar = Calendar.getInstance().apply {
        hourOfDay = begin.hourOfDay
        minute = begin.minute
    }

    Row(
        modifier = modifier.clickable {
            if (isInEditMode.not()) {
                isInEditMode = true
            }
        },
    ) {
        val string = with(calendar) {
            buildString {
                append(hour)
                append(":")
                append(minute)

                append(
                    if (amPm == Calendar.AM) {
                        "AM"
                    } else {
                        "PM"
                    }
                )
            }
        }

        Text(text = string)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun End(
    uiState: CreateRoutineUiState,
    modifier: Modifier = Modifier,
) {
    val timePickerState = rememberTimePickerState()

    var isInEditMode by remember {
        mutableStateOf(false)
    }

    if (isInEditMode) {
        Dialog(
            onDismissRequest = {
                isInEditMode = false
            }) {
            Column {
                TimePicker(state = timePickerState)
                ElevatedButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        val end = Calendar.getInstance().apply {
                            second = ZERO
                            millisecond = ZERO

                            this.hour = hour
                            this.minute = minute
                        }

                        uiState.end.value = Time(
                            hourOfDay = end.hourOfDay,
                            minute = end.minute,
                        )

                        isInEditMode = false
                    },
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }

    val end = uiState.end.value
    val calendar = Calendar.getInstance().apply {
        hourOfDay = end.hourOfDay
        minute = end.minute
    }

    Row(
        modifier = modifier.clickable {
            if (isInEditMode.not()) {
                isInEditMode = true
            }
        },
    ) {
        val string = with(calendar) {
            buildString {
                append(hour)
                append(":")
                append(minute)

                append(
                    if (amPm == Calendar.AM) {
                        "AM"
                    } else {
                        "PM"
                    }
                )
            }
        }

        Text(text = string)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Reminder(
    uiState: CreateRoutineUiState,
    modifier: Modifier = Modifier,
) {
    var reminder by uiState.reminder

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = reminder.on.not(),
                onClick = {
                    reminder = reminder.copy(on = false)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = reminder.on,
                onClick = {
                    reminder = reminder.copy(on = true)
                }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = with(reminder.hoursBefore) {
                        if (isZero) {
                            EMPTY
                        } else {
                            "$this"
                        }
                    },
                    onValueChange = { value ->
                        reminder = value.intOrNull?.let {
                            reminder.copy(hoursBefore = it.coerceAtMost(23))
                        } ?: reminder.copy(hoursBefore = ZERO)
                    },
                    modifier = Modifier.weight(ONE.float),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Text(
                    text = "시간",
                    modifier = Modifier.weight(ONE.float),
                )

                OutlinedTextField(
                    value = with(reminder.minutesBefore) {
                        if (isZero) {
                            EMPTY
                        } else {
                            "$this"
                        }
                    },
                    onValueChange = { value ->
                        reminder = value.intOrNull?.let {
                            reminder.copy(minutesBefore = it.coerceAtMost(59))
                        } ?: reminder.copy(minutesBefore = ZERO)
                    },
                    modifier = Modifier.weight(ONE.float),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Text(
                    text = "분 전",
                    modifier = Modifier.weight(ONE.float),
                )
            }
        }
    }
}

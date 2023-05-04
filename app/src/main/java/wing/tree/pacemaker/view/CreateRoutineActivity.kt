package wing.tree.pacemaker.view

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.data.extension.month
import wing.tree.pacemaker.data.extension.year
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
        Begin(uiState = uiState)

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
                            clear()
                            this.hour = hour
                            this.minute = minute
                        }

                        uiState.begin.value = Time(
                            hour = begin.hour,
                            hourOfDay = begin.hourOfDay,
                            minute = begin.minute,
                            amPm = begin.amPm,
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
        hour = begin.hour
        hourOfDay = begin.hourOfDay
        minute = begin.minute
        amPm = begin.amPm
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

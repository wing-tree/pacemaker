package wing.tree.pacemaker.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import wing.tree.pacemaker.ui.states.CreateRoutineUiState
import wing.tree.pacemaker.ui.theme.PacemakerTheme
import wing.tree.pacemaker.viewmodels.CreateRoutineViewModel
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import wing.tree.pacemaker.data.extension.date
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.month
import wing.tree.pacemaker.data.extension.year
import wing.tree.pacemaker.extension.julianDay

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

        Duration(
            uiState = uiState,
        )

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
private fun Duration(
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

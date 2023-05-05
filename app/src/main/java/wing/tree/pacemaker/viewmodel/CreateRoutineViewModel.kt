package wing.tree.pacemaker.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.service.InstanceService
import wing.tree.pacemaker.domain.service.RoutineService
import wing.tree.pacemaker.domain.usecase.core.Result
import wing.tree.pacemaker.model.Instance
import wing.tree.pacemaker.scheduler.ReminderScheduler
import wing.tree.pacemaker.ui.states.CreateRoutineUiState
import javax.inject.Inject

@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    private val instanceService: InstanceService,
    private val reminderScheduler: ReminderScheduler,
    private val routineService: RoutineService,
) : ViewModel() {
    private val ioDispatcher = Dispatchers.IO

    private val _uiState = MutableStateFlow(CreateRoutineUiState())
    val uiState: StateFlow<CreateRoutineUiState> get() = _uiState

    suspend fun create() {
        withContext(ioDispatcher) {
            val routine = uiState.value.toRoutine()
            val result = routineService.add(routine)

            if (result is Result.Complete.Success) {
                val routineId = result.data

                val instance = Instance(
                    routineId = routineId,
                    title = routine.title,
                    description = routine.description,
                    day = routine.startDay,
                    begin = routine.begin,
                    end = routine.end,
                    status = Status.Todo,
                    reminder = routine.reminder,
                )

                instanceService.add(instance)
                reminderScheduler.scheduleReminder(instance, true)
            }
        }
    }
}
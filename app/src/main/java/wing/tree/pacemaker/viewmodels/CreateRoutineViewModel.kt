package wing.tree.pacemaker.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.entities.Status
import wing.tree.pacemaker.domain.entities.Time
import wing.tree.pacemaker.domain.services.InstanceService
import wing.tree.pacemaker.domain.services.RoutineService
import wing.tree.pacemaker.domain.use.cases.core.Result
import wing.tree.pacemaker.models.Instance
import wing.tree.pacemaker.ui.states.CreateRoutineUiState
import javax.inject.Inject

@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    private val instanceService: InstanceService,
    private val routineService: RoutineService,
) : ViewModel() {
    private val ioDispatcher = Dispatchers.IO

    private val _uiState = MutableStateFlow(CreateRoutineUiState())
    val uiState: StateFlow<CreateRoutineUiState> get() = _uiState

    suspend fun create() {
        withContext(ioDispatcher) {
            val routine = uiState.value.toRoutine(Routine.Periodic.DAILY)
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
                )

                instanceService.add(instance)
            }
        }
    }
}
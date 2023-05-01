package wing.tree.pacemaker.domain.services

import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.use.cases.AddRoutineUseCase
import javax.inject.Inject

class RoutineService @Inject constructor(
    private val addRoutineUseCase: AddRoutineUseCase,
) {
    suspend fun add(routine: Routine) = addRoutineUseCase(routine)
}

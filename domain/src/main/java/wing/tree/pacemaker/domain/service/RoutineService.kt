package wing.tree.pacemaker.domain.service

import wing.tree.pacemaker.domain.entity.Routine
import wing.tree.pacemaker.domain.usecase.AddRoutineUseCase
import javax.inject.Inject

class RoutineService @Inject constructor(
    private val addRoutineUseCase: AddRoutineUseCase,
) {
    suspend fun add(routine: Routine) = addRoutineUseCase(routine)
}

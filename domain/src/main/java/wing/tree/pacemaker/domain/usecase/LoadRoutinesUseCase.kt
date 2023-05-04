package wing.tree.pacemaker.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entity.Routine
import wing.tree.pacemaker.domain.repository.RoutineRepository
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import wing.tree.pacemaker.domain.usecase.core.NoParameterFlowUseCase
import javax.inject.Inject

class LoadRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher,
) : NoParameterFlowUseCase<List<Routine>>(coroutineDispatcher) {
    override fun execute(): Flow<List<Routine>> {
        return routineRepository.load()
    }
}

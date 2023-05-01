package wing.tree.pacemaker.domain.use.cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.repositories.RoutineRepository
import wing.tree.pacemaker.domain.use.cases.core.IOCoroutineDispatcher
import wing.tree.pacemaker.domain.use.cases.core.NoParameterFlowUseCase
import javax.inject.Inject

class LoadRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher,
) : NoParameterFlowUseCase<List<Routine>>(coroutineDispatcher) {
    override fun execute(): Flow<List<Routine>> {
        return routineRepository.load()
    }
}

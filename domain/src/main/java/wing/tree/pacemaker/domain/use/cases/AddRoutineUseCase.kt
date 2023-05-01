package wing.tree.pacemaker.domain.use.cases

import kotlinx.coroutines.CoroutineDispatcher
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.repositories.RoutineRepository
import wing.tree.pacemaker.domain.use.cases.core.CoroutineUseCase
import wing.tree.pacemaker.domain.use.cases.core.IOCoroutineDispatcher
import javax.inject.Inject

class AddRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Routine, Long>(coroutineDispatcher) {
    override suspend fun execute(parameter: Routine): Long {
        return routineRepository.add(parameter)
    }
}

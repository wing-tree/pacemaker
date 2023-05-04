package wing.tree.pacemaker.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import wing.tree.pacemaker.domain.entity.Routine
import wing.tree.pacemaker.domain.repository.RoutineRepository
import wing.tree.pacemaker.domain.usecase.core.CoroutineUseCase
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import javax.inject.Inject

class AddRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Routine, Long>(coroutineDispatcher) {
    override suspend fun execute(parameter: Routine): Long {
        return routineRepository.add(parameter)
    }
}

package wing.tree.pacemaker.domain.use.cases

import kotlinx.coroutines.CoroutineDispatcher
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.repositories.InstanceRepository
import wing.tree.pacemaker.domain.use.cases.core.CoroutineUseCase
import wing.tree.pacemaker.domain.use.cases.core.IOCoroutineDispatcher
import javax.inject.Inject

class AddAllInstanceUseCase @Inject constructor(
    private val instanceRepository: InstanceRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<List<Instance>, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: List<Instance>) {
        instanceRepository.addAll(parameter)
    }
}

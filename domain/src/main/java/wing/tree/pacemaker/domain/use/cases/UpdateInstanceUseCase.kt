package wing.tree.pacemaker.domain.use.cases

import kotlinx.coroutines.CoroutineDispatcher
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.repositories.InstanceRepository
import wing.tree.pacemaker.domain.use.cases.core.CoroutineUseCase
import wing.tree.pacemaker.domain.use.cases.core.IOCoroutineDispatcher
import javax.inject.Inject

class UpdateInstanceUseCase @Inject constructor(
    private val instanceRepository: InstanceRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Instance, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: Instance) {
        instanceRepository.update(parameter)
    }
}

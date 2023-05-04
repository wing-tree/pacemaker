package wing.tree.pacemaker.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.repository.InstanceRepository
import wing.tree.pacemaker.domain.usecase.core.CoroutineUseCase
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import javax.inject.Inject

class AddAllInstanceUseCase @Inject constructor(
    private val instanceRepository: InstanceRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<List<Instance>, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: List<Instance>) {
        instanceRepository.addAll(parameter)
    }
}

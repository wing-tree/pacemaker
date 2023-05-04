package wing.tree.pacemaker.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.repository.InstanceRepository
import wing.tree.pacemaker.domain.usecase.core.FlowUseCase
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import javax.inject.Inject

class LoadInstancesUseCase @Inject constructor(
    private val instanceRepository: InstanceRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<LoadInstancesUseCase.Parameter, List<Instance>>(coroutineDispatcher) {
    override fun execute(parameter: Parameter): Flow<List<Instance>> {
        return instanceRepository.load(
            startDay = parameter.startDay,
            endDay = parameter.endDay,
        )
    }

    data class Parameter(
        val startDay: Int,
        val endDay: Int,
    )
}

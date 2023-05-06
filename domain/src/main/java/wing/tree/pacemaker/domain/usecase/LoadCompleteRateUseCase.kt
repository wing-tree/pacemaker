package wing.tree.pacemaker.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entity.TimePeriod
import wing.tree.pacemaker.domain.repository.InstanceRepository
import wing.tree.pacemaker.domain.usecase.core.FlowUseCase
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import javax.inject.Inject

class LoadCompleteRateUseCase @Inject constructor(
    private val instanceRepository: InstanceRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<TimePeriod, Float>(coroutineDispatcher) {
    override fun execute(parameter: TimePeriod): Flow<Float> {
        return when (parameter) {
            is TimePeriod.DateRange -> instanceRepository.loadCompleteRate(
                startDay = parameter.startDay,
                endDay = parameter.endDay,
            )

            TimePeriod.Overall -> instanceRepository.loadCompleteRate()
        }
    }
}

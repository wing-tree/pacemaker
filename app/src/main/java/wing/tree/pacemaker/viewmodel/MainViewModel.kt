package wing.tree.pacemaker.viewmodel

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.scheduler.WorkScheduler
import wing.tree.pacemaker.domain.constant.DAYS_PER_WEEK
import wing.tree.pacemaker.domain.constant.ONE
import wing.tree.pacemaker.domain.constant.WEEKS_PER_MONTH
import wing.tree.pacemaker.domain.service.InstanceService
import wing.tree.pacemaker.domain.usecase.LoadInstancesUseCase
import wing.tree.pacemaker.domain.usecase.core.Result
import wing.tree.pacemaker.domain.usecase.core.map
import wing.tree.pacemaker.mapper.InstanceMapper
import wing.tree.pacemaker.model.Instance
import javax.inject.Inject

private val today: Int get() = Calendar.getInstance().julianDay

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val instanceMapper: InstanceMapper,
    private val instanceService: InstanceService,
    private val workScheduler: WorkScheduler,
) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO

    val selectedDay = MutableStateFlow(today)

    fun onDaySelected(day: Int) {
        selectedDay.update {
            day
        }
    }

    fun loadInstances(year: Int, month: Int): Flow<Result<ImmutableList<Instance>>> {
        // TODO 서비스 사이드/ 레포지토리, 데이터소스 사이드로 이동 필. 데이터소스?로 가야될듯.
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, ONE)
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.WEEK_OF_MONTH, ONE)
            clear(Calendar.HOUR)
            clear(Calendar.HOUR_OF_DAY)
            clear(Calendar.MINUTE)
            clear(Calendar.SECOND)
        }

        val startDay = calendar.julianDay
        val endDay = calendar.apply {
            add(Calendar.WEEK_OF_MONTH, WEEKS_PER_MONTH.dec())
            add(Calendar.DAY_OF_MONTH, DAYS_PER_WEEK)
        }.julianDay

        return instanceService.load(
            LoadInstancesUseCase.Parameter(
                startDay = startDay,
                endDay = endDay,
            )
        ).map { result ->
            result.map { list ->
                list.map {
                    instanceMapper.toModel(it)
                }.toImmutableList()
            }
        }
    }

    fun scheduleCreateInstancesWorker() {
        workScheduler.scheduleCreateInstancesWorker(getApplication())
    }

    fun updateStatus(instance: Instance) {
        viewModelScope.launch(ioDispatcher) {
            with(instance) {
                instanceService.update(copy(status = status.next))
            }
        }
    }
}

package wing.tree.pacemaker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.function.calendarOf
import wing.tree.pacemaker.domain.usecase.LoadInstancesUseCase
import wing.tree.pacemaker.domain.usecase.core.getOrNull
import wing.tree.pacemaker.mapper.InstanceMapper
import wing.tree.pacemaker.scheduler.ReminderScheduler
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver()  {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var loadInstancesUseCase: LoadInstancesUseCase

    @Inject
    lateinit var reminderScheduler: ReminderScheduler

    @Inject
    lateinit var instanceMapper: InstanceMapper

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val startDay = calendarOf().julianDay
            val parameter = LoadInstancesUseCase.Parameter(
                startDay = startDay,
                endDay = startDay
            )

            coroutineScope.launch {
                try {
                    loadInstancesUseCase(parameter).firstOrNull()?.let { result ->
                        result.getOrNull()?.forEach { instance ->
                            reminderScheduler.scheduleReminder(
                                instanceMapper.toModel(instance),
                                true,
                            )
                        }
                    }
                } finally {
                    /* no-op */
                }
            }
        }
    }
}
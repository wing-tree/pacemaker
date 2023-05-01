package wing.tree.pacemaker.ui.states

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.domain.constant.EMPTY
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.domain.entities.Routine.Periodic
import wing.tree.pacemaker.domain.extension.long
import wing.tree.pacemaker.models.Routine

data class CreateRoutineUiState(
    val title: MutableState<String> = mutableStateOf(EMPTY),
    val description: MutableState<String> = mutableStateOf(EMPTY),
    val startDay: MutableState<Int> = mutableStateOf(ZERO),
    val endDay: MutableState<Int> = mutableStateOf(Int.MAX_VALUE),
    val begin: MutableState<Long> = mutableStateOf(ZERO.long),
    val end: MutableState<Long> = mutableStateOf(Long.MAX_VALUE),
) {
    init {
        val calendar = Calendar.getInstance()

        startDay.value = calendar.julianDay
        begin.value = calendar.timeInMillis
    }

    fun toRoutine(
        periodic: Periodic,
    ) = Routine(
        title = title.value,
        description = description.value,
        startDay = startDay.value,
        endDay = endDay.value,
        begin = begin.value,
        end = end.value,
        periodic = periodic
    )
}

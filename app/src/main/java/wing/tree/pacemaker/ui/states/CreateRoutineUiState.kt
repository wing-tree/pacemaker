package wing.tree.pacemaker.ui.states

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import wing.tree.pacemaker.data.extension.amPm
import wing.tree.pacemaker.data.extension.hour
import wing.tree.pacemaker.data.extension.hourOfDay
import wing.tree.pacemaker.data.extension.julianDay
import wing.tree.pacemaker.data.extension.minute
import wing.tree.pacemaker.domain.constant.EMPTY
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.model.Reminder
import wing.tree.pacemaker.model.Routine
import wing.tree.pacemaker.model.Time

data class CreateRoutineUiState(
    val title: MutableState<String> = mutableStateOf(EMPTY),
    val description: MutableState<String> = mutableStateOf(EMPTY),
    val startDay: MutableState<Int> = mutableStateOf(ZERO),
    val endDay: MutableState<Int> = mutableStateOf(Int.MAX_VALUE),
    val begin: MutableState<Time> = mutableStateOf(Time.none),
    val end: MutableState<Time> = mutableStateOf(Time.none),
    val reminder: MutableState<Reminder> = mutableStateOf(Reminder(ZERO, ZERO, false))
) {
    init {
        val calendar = Calendar.getInstance()

        startDay.value = calendar.julianDay
        begin.value = Time(
            hourOfDay = calendar.hourOfDay,
            minute = calendar.minute,
        )

        end.value = Time(
            hourOfDay = calendar.hourOfDay.inc(),
            minute = calendar.minute,
        )
    }

    fun toRoutine() = Routine(
        title = title.value,
        description = description.value,
        startDay = startDay.value,
        endDay = endDay.value,
        begin = begin.value,
        end = end.value,
        reminder = reminder.value,
    )
}

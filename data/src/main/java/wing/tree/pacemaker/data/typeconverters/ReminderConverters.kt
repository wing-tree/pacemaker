package wing.tree.pacemaker.data.typeconverters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import wing.tree.pacemaker.data.model.Reminder

class ReminderConverters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter: JsonAdapter<Reminder> = moshi.adapter(Reminder::class.java)

    @TypeConverter
    fun reminderToJson(reminder: Reminder): String {
        return adapter.toJson(reminder)
    }

    @TypeConverter
    fun jsonToReminder(json: String): Reminder {
        return adapter.fromJson(json) ?: Reminder(0, 0, false)
    }
}

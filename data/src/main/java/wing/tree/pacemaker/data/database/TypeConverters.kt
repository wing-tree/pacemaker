package wing.tree.pacemaker.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import wing.tree.pacemaker.domain.entities.Time

class TypeConverters {
    private val moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<Time> = moshi.adapter(
        Types.newParameterizedType(Time::class.java)
    )

    @TypeConverter
    fun timeToJson(time: Time): String {
        return adapter.toJson(time)
    }

    @TypeConverter
    fun jsonToTime(json: String): Time {
        return adapter.fromJson(json) ?: Time.none
    }
}

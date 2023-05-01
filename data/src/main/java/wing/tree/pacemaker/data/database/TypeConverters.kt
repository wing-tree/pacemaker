package wing.tree.pacemaker.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import wing.tree.pacemaker.domain.entities.Routine

class TypeConverters {
    private val moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<Routine.Periodic> = moshi.adapter(
        Types.newParameterizedType(Routine.Periodic::class.java)
    )

    @TypeConverter
    fun periodicToJson(periodic: Routine.Periodic): String {
        return adapter.toJson(periodic)
    }

    @TypeConverter
    fun jsonToPeriodic(json: String): Routine.Periodic? {
        return try {
            adapter.fromJson(json)
        } catch (jsonDataException: JsonDataException) {
            null
        }
    }
}

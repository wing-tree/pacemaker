package wing.tree.pacemaker.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import wing.tree.pacemaker.data.dao.InstanceDao
import wing.tree.pacemaker.data.dao.RoutineDao
import wing.tree.pacemaker.data.model.Instance
import wing.tree.pacemaker.data.model.Routine
import wing.tree.pacemaker.data.typeconverters.ReminderConverters
import wing.tree.pacemaker.data.typeconverters.TimeConverters

@androidx.room.Database(entities = [Instance::class, Routine::class], version = 1, exportSchema = false)
@androidx.room.TypeConverters(ReminderConverters::class, TimeConverters::class)
abstract class Database : RoomDatabase() {
    abstract val instanceDao: InstanceDao
    abstract val routineDao: RoutineDao

    companion object {
        private const val NAME = "database"
        private const val VERSION = "1"

        @Volatile
        private var instance: Database? = null

        fun getInstance(context: Context): Database {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "$NAME:$VERSION"
                )
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }
}

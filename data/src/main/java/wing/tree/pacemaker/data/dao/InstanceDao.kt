package wing.tree.pacemaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.models.Instance
import wing.tree.pacemaker.domain.entities.Routine

@Dao
interface InstanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(instance: Instance)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(instances: List<Instance>)

    @Update
    suspend fun update(instance: Instance)

    @Delete
    suspend fun delete(instance: Instance)

    @Query("SELECT * FROM instance WHERE periodic = :periodic AND day >= :startDay AND day <= :endDay")
    fun load(periodic: Routine.Periodic, startDay: Int, endDay: Int): Flow<List<Instance>>
}

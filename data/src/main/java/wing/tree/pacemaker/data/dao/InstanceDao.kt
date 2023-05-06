package wing.tree.pacemaker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.model.Instance
import wing.tree.pacemaker.domain.entity.Status

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

    @Query("SELECT * FROM instance WHERE day >= :startDay AND day <= :endDay")
    fun load(startDay: Int, endDay: Int): Flow<List<Instance>>

    @Query("SELECT COUNT(id) FROM instance")
    fun count(): Flow<Int>

    @Query("SELECT COUNT(id) FROM instance WHERE day >= :startDay AND day <= :endDay")
    fun count(startDay: Int, endDay: Int): Flow<Int>

    @Query("SELECT COUNT(id) FROM instance WHERE day >= :startDay AND day <= :endDay AND status = :status")
    fun count(startDay: Int, endDay: Int, status: Status): Flow<Int>

    @Query("SELECT COUNT(id) FROM instance WHERE status = :status")
    fun count(status: Status): Flow<Int>
}

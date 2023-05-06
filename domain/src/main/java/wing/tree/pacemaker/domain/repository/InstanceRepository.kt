package wing.tree.pacemaker.domain.repository

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entity.Instance

interface InstanceRepository {
    suspend fun add(instance: Instance)
    suspend fun addAll(instances: List<Instance>)
    suspend fun update(instance: Instance)
    suspend fun delete(instance: Instance)
    fun load(startDay: Int, endDay: Int): Flow<List<Instance>>
    fun loadCompleteRate(): Flow<Float>
    fun loadCompleteRate(startDay: Int, endDay: Int): Flow<Float>
}

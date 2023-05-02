package wing.tree.pacemaker.domain.repositories

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Routine

interface InstanceRepository {
    suspend fun add(instance: Instance)
    suspend fun addAll(instances: List<Instance>)
    suspend fun update(instance: Instance)
    suspend fun delete(instance: Instance)
    fun load(startDay: Int, endDay: Int): Flow<List<Instance>>
}

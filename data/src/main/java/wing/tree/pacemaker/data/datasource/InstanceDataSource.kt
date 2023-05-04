package wing.tree.pacemaker.data.datasource

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.dao.InstanceDao
import wing.tree.pacemaker.data.model.Instance

class InstanceDataSource(private val instanceDao: InstanceDao) {
    suspend fun add(instance: Instance) {
        instanceDao.insert(instance)
    }

    suspend fun addAll(instances: List<Instance>) {
        instanceDao.insertAll(instances)
    }

    suspend fun update(instance: Instance) {
        instanceDao.update(instance)
    }

    suspend fun delete(instance: Instance) {
        instanceDao.delete(instance)
    }

    fun load(
        startDay: Int,
        endDay: Int,
    ): Flow<List<Instance>> = instanceDao.load(
        startDay = startDay,
        endDay = endDay,
    )
}

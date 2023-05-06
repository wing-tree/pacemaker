package wing.tree.pacemaker.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import wing.tree.pacemaker.data.dao.InstanceDao
import wing.tree.pacemaker.data.model.Instance
import wing.tree.pacemaker.domain.entity.Status
import wing.tree.pacemaker.domain.extension.float

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

    fun loadCompleteRate(): Flow<Float> = with(instanceDao) {
        count(Status.Done).combine(count()) { completed, overall ->
            completed.div(overall.float)
        }
    }

    fun loadCompleteRate(startDay: Int, endDay: Int): Flow<Float> = with(instanceDao) {
        val completed = count(
            startDay = startDay,
            endDay = endDay,
            status = Status.Done,
        )

        val selectedMonth = count(
            startDay = startDay,
            endDay = endDay,
        )

        completed.combine(selectedMonth) { a, b ->
            a.div(b.float)
        }
    }
}

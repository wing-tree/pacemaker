package wing.tree.pacemaker.data.repositories

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.mappers.InstanceMapper
import wing.tree.pacemaker.data.sources.InstanceDataSource
import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.entities.Routine
import wing.tree.pacemaker.domain.repositories.InstanceRepository
import javax.inject.Inject

class InstanceRepositoryImpl @Inject constructor(
    private val instanceDataSource: InstanceDataSource,
    private val instanceMapper: InstanceMapper,
) : InstanceRepository {
    override suspend fun add(instance: Instance) {
        instanceDataSource.add(instance.toModel())
    }

    override suspend fun addAll(instances: List<Instance>) {
        instanceDataSource.addAll(
            instances.map {
                it.toModel()
            }
        )
    }

    override suspend fun update(instance: Instance) {
        instanceDataSource.update(instance.toModel())
    }

    override suspend fun delete(instance: Instance) {
        instanceDataSource.delete(instance.toModel())
    }

    override fun load(startDay: Int, endDay: Int): Flow<List<Instance>> {
        return instanceDataSource.load(
            startDay = startDay,
            endDay = endDay,
        )
    }

    private fun Instance.toModel() = instanceMapper.toModel(this)
}

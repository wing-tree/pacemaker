package wing.tree.pacemaker.data.repository

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.mapper.InstanceMapper
import wing.tree.pacemaker.data.datasource.InstanceDataSource
import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.repository.InstanceRepository
import javax.inject.Inject

internal class InstanceRepositoryImpl @Inject constructor(
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

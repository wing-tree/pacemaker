package wing.tree.pacemaker.data.repository

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.data.mapper.RoutineMapper
import wing.tree.pacemaker.data.datasource.RoutineDataSource
import wing.tree.pacemaker.domain.entity.Routine
import wing.tree.pacemaker.domain.repository.RoutineRepository
import javax.inject.Inject

internal class RoutineRepositoryImpl @Inject constructor(
    private val routineDataSource: RoutineDataSource,
    private val routineMapper: RoutineMapper,
) : RoutineRepository {
    override suspend fun add(routine: Routine) = routineDataSource.add(routine.toDataModel())
    override suspend fun update(routine: Routine) {
        routineDataSource.update(routine.toDataModel())
    }

    override suspend fun delete(routine: Routine) {
        routineDataSource.delete(routine.toDataModel())
    }

    override fun load(): Flow<List<Routine>> = routineDataSource.load()

    private fun Routine.toDataModel() = routineMapper.toModel(this)
}

package wing.tree.pacemaker.domain.repository

import kotlinx.coroutines.flow.Flow
import wing.tree.pacemaker.domain.entity.Routine

interface RoutineRepository {
    suspend fun add(routine: Routine): Long
    suspend fun update(routine: Routine)
    suspend fun delete(routine: Routine)
    fun load(): Flow<List<Routine>>
}

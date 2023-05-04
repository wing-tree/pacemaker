package wing.tree.pacemaker.data.datasource

import wing.tree.pacemaker.data.dao.RoutineDao
import wing.tree.pacemaker.data.model.Routine

class RoutineDataSource(private val routineDao: RoutineDao) {
    suspend fun add(routine: Routine): Long = routineDao.insert(routine)
    suspend fun update(routine: Routine) = routineDao.update(routine)
    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

    fun load() = routineDao.load()
}

package wing.tree.pacemaker.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.model.Routine as Model
import wing.tree.pacemaker.domain.entity.Routine as Entity

class RoutineMapper(
    private val reminderMapper: ReminderMapper,
    private val timeMapper: TimeMapper,
) : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return with(entity) {
            Model(
                title = title,
                description = description,
                startDay = startDay,
                endDay = endDay,
                begin = timeMapper.toModel(begin),
                end = timeMapper.toModel(end),
                reminder = reminderMapper.toModel(reminder),
            )
        }
    }
}

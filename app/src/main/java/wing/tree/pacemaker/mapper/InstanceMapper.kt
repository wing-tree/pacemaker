package wing.tree.pacemaker.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.model.Instance as Model
import wing.tree.pacemaker.domain.entity.Instance as Entity

class InstanceMapper(
    private val reminderMapper: ReminderMapper,
    private val timeMapper: TimeMapper,
) : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return with(entity) {
            Model(
                id = id,
                routineId = routineId,
                title = title,
                description = description,
                begin = timeMapper.toModel(begin),
                end = timeMapper.toModel(end),
                day = day,
                status = status,
                reminder = reminderMapper.toModel(reminder),
            )
        }
    }
}

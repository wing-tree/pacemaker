package wing.tree.pacemaker.data.mappers

import wing.tree.pacemaker.data.mappers.core.EntityMapper
import wing.tree.pacemaker.data.models.Instance as Model
import wing.tree.pacemaker.domain.entities.Instance as Entity

class InstanceMapper(private val timeMapper: TimeMapper) : EntityMapper<Entity, Model> {
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
            )
        }
    }
}

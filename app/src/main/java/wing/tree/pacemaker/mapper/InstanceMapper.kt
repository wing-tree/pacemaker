package wing.tree.pacemaker.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.model.Instance as Model
import wing.tree.pacemaker.domain.entity.Instance as Entity

class InstanceMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return with(entity) {
            Model(
                id = id,
                routineId = routineId,
                title = title,
                description = description,
                begin = begin,
                end = end,
                day = day,
                status = status,
            )
        }
    }
}

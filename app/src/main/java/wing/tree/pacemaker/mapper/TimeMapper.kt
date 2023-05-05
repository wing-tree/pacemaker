package wing.tree.pacemaker.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.domain.entity.Time as Entity
import wing.tree.pacemaker.model.Time as Model

class TimeMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            hourOfDay = entity.hourOfDay,
            minute = entity.minute,
        )
    }
}
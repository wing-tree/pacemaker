package wing.tree.pacemaker.data.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.data.model.Time as Model
import wing.tree.pacemaker.domain.entity.Time as Entity

class TimeMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            hourOfDay = entity.hourOfDay,
            minute = entity.minute,
        )
    }
}
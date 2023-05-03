package wing.tree.pacemaker.data.mappers

import wing.tree.pacemaker.data.mappers.core.EntityMapper
import wing.tree.pacemaker.data.models.Time as Model
import wing.tree.pacemaker.domain.entities.Time as Entity

class TimeMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            hour = entity.hour,
            hourOfDay = entity.hourOfDay,
            minute = entity.minute,
            amPm = entity.amPm,
        )
    }
}
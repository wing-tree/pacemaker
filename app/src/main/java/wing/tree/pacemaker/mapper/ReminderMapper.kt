package wing.tree.pacemaker.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.domain.entity.Reminder as Entity
import wing.tree.pacemaker.model.Reminder as Model

class ReminderMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            hoursBefore = entity.hoursBefore,
            minutesBefore = entity.minutesBefore,
            on = entity.on,
        )
    }
}

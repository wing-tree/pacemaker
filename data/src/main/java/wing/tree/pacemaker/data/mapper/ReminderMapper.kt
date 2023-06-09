package wing.tree.pacemaker.data.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.data.model.Reminder as Model
import wing.tree.pacemaker.domain.entity.Reminder as Entity

internal class ReminderMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            hoursBefore = entity.hoursBefore,
            minutesBefore = entity.minutesBefore,
            on = entity.on,
        )
    }
}

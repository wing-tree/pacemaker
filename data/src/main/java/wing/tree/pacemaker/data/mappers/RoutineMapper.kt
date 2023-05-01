package wing.tree.pacemaker.data.mappers

import wing.tree.pacemaker.data.mappers.core.EntityMapper
import wing.tree.pacemaker.data.models.Routine as Model
import wing.tree.pacemaker.domain.entities.Routine as Entity

class RoutineMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return with(entity) {
            Model(
                id = id,
                title = title,
                description = description,
                periodic = periodic,
                startDay = startDay,
                endDay = endDay,
                begin = begin,
                end = end,
            )
        }
    }
}

package wing.tree.pacemaker.data.mapper

import wing.tree.pacemaker.data.mapper.core.EntityMapper
import wing.tree.pacemaker.data.model.Routine as Model
import wing.tree.pacemaker.domain.entity.Routine as Entity

class RoutineMapper(private val timeMapper: TimeMapper) : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return with(entity) {
            Model(
                id = id,
                title = title,
                description = description,
                periodic = periodic,
                startDay = startDay,
                endDay = endDay,
                begin = timeMapper.toModel(begin),
                end = timeMapper.toModel(end),
            )
        }
    }
}

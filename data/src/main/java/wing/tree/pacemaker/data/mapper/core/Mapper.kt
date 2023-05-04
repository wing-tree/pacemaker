package wing.tree.pacemaker.data.mapper.core

internal interface Mapper<E, M>: EntityMapper<E, M>, ModelMapper<M, E>

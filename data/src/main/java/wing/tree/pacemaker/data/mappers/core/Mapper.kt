package wing.tree.pacemaker.data.mappers.core

internal interface Mapper<E, M>: EntityMapper<E, M>, ModelMapper<M, E>

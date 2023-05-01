package wing.tree.pacemaker.data.mappers.core

interface ModelMapper<M, E> {
    fun toEntity(model: M): E
}

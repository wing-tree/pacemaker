package wing.tree.pacemaker.data.mapper.core

interface ModelMapper<M, E> {
    fun toEntity(model: M): E
}

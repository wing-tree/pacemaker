package wing.tree.pacemaker.data.mapper.core

interface EntityMapper<E, M> {
    fun toModel(entity: E): M
}

package wing.tree.pacemaker.data.mappers.core

interface EntityMapper<E, M> {
    fun toModel(entity: E): M
}

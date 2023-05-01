package wing.tree.pacemaker.domain.services

import wing.tree.pacemaker.domain.entities.Instance
import wing.tree.pacemaker.domain.use.cases.AddInstanceUseCase
import wing.tree.pacemaker.domain.use.cases.LoadInstancesUseCase
import wing.tree.pacemaker.domain.use.cases.UpdateInstanceUseCase
import javax.inject.Inject

class InstanceService @Inject constructor(
    private val addInstanceUseCase: AddInstanceUseCase,
    private val loadInstancesUseCase: LoadInstancesUseCase,
    private val updateInstanceUseCase: UpdateInstanceUseCase,
) {
    suspend fun add(instance: Instance) = addInstanceUseCase(instance)
    suspend fun update(instance: Instance) = updateInstanceUseCase(instance)

    fun load(parameter: LoadInstancesUseCase.Parameter) = loadInstancesUseCase(parameter)
}

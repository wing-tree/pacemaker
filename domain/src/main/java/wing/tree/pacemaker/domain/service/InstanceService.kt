package wing.tree.pacemaker.domain.service

import wing.tree.pacemaker.domain.entity.Instance
import wing.tree.pacemaker.domain.usecase.AddInstanceUseCase
import wing.tree.pacemaker.domain.usecase.LoadInstancesUseCase
import wing.tree.pacemaker.domain.usecase.UpdateInstanceUseCase
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

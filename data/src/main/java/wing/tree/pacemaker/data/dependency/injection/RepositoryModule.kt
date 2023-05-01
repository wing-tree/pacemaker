package wing.tree.pacemaker.data.dependency.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.repositories.InstanceRepositoryImpl
import wing.tree.pacemaker.data.repositories.RoutineRepositoryImpl
import wing.tree.pacemaker.domain.repositories.InstanceRepository
import wing.tree.pacemaker.domain.repositories.RoutineRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsInstanceRepository(instanceRepository: InstanceRepositoryImpl): InstanceRepository

    @Binds
    @Singleton
    abstract fun bindsRoutineRepository(routineRepository: RoutineRepositoryImpl): RoutineRepository
}

package wing.tree.pacemaker.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.repository.InstanceRepositoryImpl
import wing.tree.pacemaker.data.repository.RoutineRepositoryImpl
import wing.tree.pacemaker.domain.repository.InstanceRepository
import wing.tree.pacemaker.domain.repository.RoutineRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsInstanceRepository(instanceRepository: InstanceRepositoryImpl): InstanceRepository

    @Binds
    @Singleton
    abstract fun bindsRoutineRepository(routineRepository: RoutineRepositoryImpl): RoutineRepository
}

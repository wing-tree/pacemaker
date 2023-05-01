package wing.tree.pacemaker.data.dependency.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.mappers.InstanceMapper
import wing.tree.pacemaker.data.mappers.RoutineMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Provides
    @Singleton
    fun providesInstanceMapper(): InstanceMapper {
        return InstanceMapper()
    }

    @Provides
    @Singleton
    fun providesRoutineMapper(): RoutineMapper {
        return RoutineMapper()
    }
}

package wing.tree.pacemaker.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.mapper.InstanceMapper
import wing.tree.pacemaker.data.mapper.RoutineMapper
import wing.tree.pacemaker.data.mapper.TimeMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Provides
    @Singleton
    fun providesTimeMapper() = TimeMapper()

    @Provides
    @Singleton
    fun providesInstanceMapper(timeMapper: TimeMapper): InstanceMapper {
        return InstanceMapper(timeMapper)
    }

    @Provides
    @Singleton
    fun providesRoutineMapper(timeMapper: TimeMapper): RoutineMapper {
        return RoutineMapper(timeMapper)
    }
}

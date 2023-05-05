package wing.tree.pacemaker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.mapper.InstanceMapper
import wing.tree.pacemaker.mapper.ReminderMapper
import wing.tree.pacemaker.mapper.RoutineMapper
import wing.tree.pacemaker.mapper.TimeMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Provides
    @Singleton
    fun providesReminderMapper() = ReminderMapper()

    @Provides
    @Singleton
    fun providesTimeMapper() = TimeMapper()

    @Provides
    @Singleton
    fun providesInstanceMapper(
        reminderMapper: ReminderMapper,
        timeMapper: TimeMapper,
    ): InstanceMapper {
        return InstanceMapper(reminderMapper, timeMapper)
    }

    @Provides
    @Singleton
    fun providesRoutineMapper(
        reminderMapper: ReminderMapper,
        timeMapper: TimeMapper,
    ): RoutineMapper {
        return RoutineMapper(reminderMapper, timeMapper)
    }
}

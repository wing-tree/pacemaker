package wing.tree.pacemaker.data.dependency.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.database.Database
import wing.tree.pacemaker.data.sources.InstanceDataSource
import wing.tree.pacemaker.data.sources.RoutineDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Provides
    @Singleton
    fun providesInstanceDataSource(database: Database): InstanceDataSource {
        return InstanceDataSource(database.instanceDao)
    }

    @Provides
    @Singleton
    fun providesRoutineDataSource(database: Database): RoutineDataSource {
        return RoutineDataSource(database.routineDao)
    }
}

package wing.tree.pacemaker.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.database.Database
import wing.tree.pacemaker.data.datasource.InstanceDataSource
import wing.tree.pacemaker.data.datasource.RoutineDataSource
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

package wing.tree.pacemaker.dependency.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.schedulers.WorkScheduler
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SchedulerModule {
    @Provides
    @Singleton
    fun providesWorkScheduler(): WorkScheduler {
        return WorkScheduler()
    }
}

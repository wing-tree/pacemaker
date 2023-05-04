package wing.tree.pacemaker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.scheduler.ReminderScheduler
import wing.tree.pacemaker.scheduler.WorkScheduler
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SchedulerModule {
    @Provides
    @Singleton
    fun providesReminderScheduler(@ApplicationContext context: Context): ReminderScheduler {
        return ReminderScheduler(context)
    }

    @Provides
    @Singleton
    fun providesWorkScheduler(): WorkScheduler {
        return WorkScheduler()
    }
}

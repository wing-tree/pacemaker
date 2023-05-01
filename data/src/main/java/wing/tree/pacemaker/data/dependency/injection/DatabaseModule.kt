package wing.tree.pacemaker.data.dependency.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.data.database.Database
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): Database {
        return Database.getInstance(context)
    }
}

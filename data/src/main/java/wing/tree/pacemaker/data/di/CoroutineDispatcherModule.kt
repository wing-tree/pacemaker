package wing.tree.pacemaker.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import wing.tree.pacemaker.domain.usecase.core.DefaultCoroutineDispatcher
import wing.tree.pacemaker.domain.usecase.core.IOCoroutineDispatcher
import wing.tree.pacemaker.domain.usecase.core.MainCoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatcherModule {
    @DefaultCoroutineDispatcher
    @Provides
    fun providesDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IOCoroutineDispatcher
    @Provides
    fun providesIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainCoroutineDispatcher
    @Provides
    fun providesMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

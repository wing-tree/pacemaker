package wing.tree.pacemaker.dependency.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wing.tree.pacemaker.mappers.InstanceMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Provides
    @Singleton
    fun providesInstanceMapper(): InstanceMapper {
        return InstanceMapper()
    }
}

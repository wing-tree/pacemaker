package wing.tree.pacemaker.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NoParameterCoroutineUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                Result.Complete.Success(execute())
            }
        } catch (cause: Throwable) {
            Result.Complete.Failure(cause)
        }
    }

    protected abstract suspend fun execute(): R
}

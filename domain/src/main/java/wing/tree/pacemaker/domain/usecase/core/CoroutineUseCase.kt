package wing.tree.pacemaker.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameter: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                Result.Complete.Success(execute(parameter))
            }
        } catch (cause: Throwable) {
            Result.Complete.Failure(cause)
        }
    }

    protected abstract suspend fun execute(parameter: P): R
}

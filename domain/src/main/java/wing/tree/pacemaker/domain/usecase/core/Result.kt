package wing.tree.pacemaker.domain.usecase.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Result<out R> {
    object Loading : Result<Nothing>()
    sealed class Complete<out R> : Result<R>() {
        data class Success<out T>(val data: T) : Complete<T>()
        data class Failure(val cause: Throwable) : Complete<Nothing>()
    }

    val isSuccess: Boolean get() = this is Complete.Success
    val isFailure: Boolean get() = this is Complete.Failure
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }

    if (this is Result.Complete.Success) {
        action(data)
    }

    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFailure(action: (exception: Throwable) -> Unit): Result<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }

    if (this is Result.Complete.Failure) {
        action(cause)
    }

    return this
}

fun <T> Result<T>.getOrNull(): T? {
    return when (this) {
        Result.Loading -> null
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> data
            is Result.Complete.Failure -> null
        }
    }
}

fun <T> Result<T?>.getOrDefault(defaultValue: T): T {
    return when (this) {
        Result.Loading -> defaultValue
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> data ?: defaultValue
            is Result.Complete.Failure -> defaultValue
        }
    }
}

fun <T> Result<List<T>>.firstOrNull(): T? {
    return when (this) {
        Result.Loading -> null
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> data.firstOrNull()
            is Result.Complete.Failure -> null
        }
    }
}

fun <T> Result<List<T?>>.firstOrDefault(defaultValue: T): T {
    return when (this) {
        Result.Loading -> defaultValue
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> data.firstOrNull() ?: defaultValue
            is Result.Complete.Failure -> defaultValue
        }
    }
}

inline fun <R, T> Result<Iterable<T>>.flatMap(transform: (T) -> Iterable<R>): Result<List<R>> {
    return when (this) {
        Result.Loading -> Result.Loading
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> Result.Complete.Success(data.flatMap(transform))
            is Result.Complete.Failure -> Result.Complete.Failure(cause)
        }
    }
}

inline fun <R, T> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        Result.Loading -> Result.Loading
        is Result.Complete -> when (this) {
            is Result.Complete.Success -> Result.Complete.Success(transform(data))
            is Result.Complete.Failure -> Result.Complete.Failure(cause)
        }
    }
}

inline fun <R, T> Result<T>.mapCatching(transform: (value: T) -> R): Result<R> {
    return when (this) {
        Result.Loading -> Result.Loading
        is Result.Complete.Success -> runCatching { transform(data) }
        is Result.Complete.Failure -> this
    }
}

inline fun <T, R> T.runCatching(block: T.() -> R): Result<R> {
    return try {
        Result.Complete.Success(block())
    } catch (cause: Throwable) {
        Result.Complete.Failure(cause)
    }
}

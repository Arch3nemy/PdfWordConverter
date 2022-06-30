package com.alacrity.music.utils

import com.alacrity.music.exceptions.BaseException
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

fun <R> CoroutineScope.safeCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, logError: String? = null, logSuccess: String? = null, block: suspend () -> R): Result<R> {
    var result = Result.failure<R>(BaseException())
    launch(dispatcher) {
        result = withContext(Dispatchers.Default) {
            try {
                Result.success(block()).also { logSuccess.let { Timber.d(it) } }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Result.failure<R>(e).also { logError.let { Timber.d(it) } }
            }
        }
    }
    return result
}
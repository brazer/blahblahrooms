package com.softeq.blahblahrooms.presentation.vm

suspend fun <R> useCase(
    block: suspend () -> R
): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
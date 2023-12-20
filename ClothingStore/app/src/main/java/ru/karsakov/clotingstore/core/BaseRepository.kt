package ru.karsakov.clothingstore.core

abstract class BaseRepository {

    protected suspend  fun <T> handleResult(
        block: suspend () -> T
    ): Result<T> {
        return try {
            Result.Success(block())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
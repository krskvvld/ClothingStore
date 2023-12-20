package ru.karsakov.clothingstore.core

class SideEffect<T>(value: T) {

    private var value: T? = value

    fun handle(block: (value: T) -> Unit) {
        value?.let {
            block(it).also {
                value = null
            }
        }
    }
}

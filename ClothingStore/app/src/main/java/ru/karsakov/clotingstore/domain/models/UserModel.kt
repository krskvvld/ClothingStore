package ru.karsakov.clothingstore.domain.models

data class UserModel(
    val firstName: String,
    val lastName: String,
    val group: String,
    val photoUrl: String? = null,
) {
    val fullName: String
        get() = "$firstName $lastName"
}

package ru.karsakov.clothingstore.domain.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.karsakov.clothingstore.core.Result
import ru.karsakov.clothingstore.domain.models.UserModel

class UserRepository() {

    suspend fun getUser(): Result<UserModel> = withContext(Dispatchers.IO) {
        Result.Success(defaultUser)
    }
}

private val defaultUser = UserModel(
    firstName = "Vlad",
    lastName = "Karsakov",
    group = "MOAISM",
    photoUrl = "https://y8y98iykb8.a.trbcdn.net/wp-content/uploads/2020/12/avatarki.jpg",
)
package ru.karsakov.clothingstore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.karsakov.clothingstore.core.Result
import ru.karsakov.clothingstore.domain.models.ClothModel
import ru.karsakov.clothingstore.domain.models.UserModel
import ru.karsakov.clothingstore.domain.repositories.GClothesRepository
import ru.karsakov.clothingstore.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val clothesRepository: ClothesRepository,
) : ViewModel() {

    private val mutableUser: MutableLiveData<UserModel> = MutableLiveData()
    val user: LiveData<UserModel> = mutableUser

    private val mutableClothes: MutableLiveData<List<ClothModel>> = MutableLiveData()
    val clothes: LiveData<List<ClothModel>> = mutablClothes

    init {
        viewModelScope.launch {
            when (val userResult = userRepository.getUser()) {
                is Result.Success -> {
                    val user = userResult.value
                    mutableUser.postValue(user)
                }
                is Result.Error -> {}
            }

            when (val clothesResult = clothesRepository.getAllClothes()) {
                is Result.Success -> {
                    val clothes = clothesResult.value
                    mutableClothes.postValue(clothes)
                }
                is Result.Error -> {}
            }
        }
    }
}
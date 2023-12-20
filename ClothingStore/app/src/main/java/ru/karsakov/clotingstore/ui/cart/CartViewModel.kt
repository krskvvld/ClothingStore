package ru.karsakov.clothingstore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.karsakov.clothingstore.core.Result
import ru.karsakov.clothingstore.core.SideEffect
import ru.karsakov.clothingstore.domain.models.ClothModel
import ru.karsakov.clothingstore.domain.repositories.ClothesRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val clothesRepository: ClothesRepository,
) : ViewModel() {

    private val mutableClothes: MutableLiveData<List<GClothModel>> = MutableLiveData()
    val clothes: LiveData<List<ClothModel>> = mutableClothes

    private val mutableBuySuccessEffect: MutableLiveData<SideEffect<Unit>> = MutableLiveData()
    val buySuccessEffect: LiveData<SideEffect<Unit>> = mutableBuySuccessEffect

    init {
        loadClothes()
    }

    fun removeFromCart(cloth: ClothModel) {
        viewModelScope.launch {
            when (clothesRepository.removeClothFromCart(cloth)) {
                is Result.Success -> {
                    loadClothes()
                }

                is Result.Error -> {}
            }
        }
    }

    fun loadClothes() {
        viewModelScope.launch {
            when (val result = clothesRepository.getCartClothes()) {
                is Result.Success -> {
                    val clothes = result.value
                    mutableClothes.postValue(clothes)
                }

                is Result.Error -> {}
            }
        }
    }

    fun buyClothes() {
        viewModelScope.launch {
            val clothes = clothes.value ?: return@launch
            when (clothesRepository.buyClothes(clothes)) {
                is Result.Success -> {
                    mutableBuySuccessEffect.postValue(SideEffect(Unit))
                    loadClothes()
                }

                is Result.Error -> {}
            }
        }
    }
}
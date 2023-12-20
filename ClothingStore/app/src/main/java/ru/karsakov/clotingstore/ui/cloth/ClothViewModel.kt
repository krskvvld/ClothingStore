package ru.karsakov.clothingstore.ui.cloth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.karsakov.clothingstore.core.Result
import ru.karsakov.clothingstore.domain.models.ClothModel
import ru.karsakov.clothingstore.domain.repositories.ClothesRepository
import javax.inject.Inject

@HiltViewModel
class ClothViewModel @Inject constructor(
    private val clothesRepository: ClothesRepository,
) : ViewModel() {

    private lateinit var args: ClothActivityArguments

    private val mutableCloth: MutableLiveData<ClothModel> = MutableLiveData()
    val cloth: LiveData<ClothModel> = mutableCloth

    fun initWithArgs(value: ClothActivityArguments) {
        if (!::args.isInitialized) {
            args = value
        }
        viewModelScope.launch {
            loadCloth()
        }
    }

    fun onCartClick() {
        val cloth = cloth.value ?: return
        viewModelScope.launch {
            val result = if (cloth.inCart) {
                clothesRepository.removeClothFromCart(cloth)
            } else {
                clothesRepository.addClothToCart(cloth)
            }
            when (result) {
                is Result.Success -> {
                    loadCloth()
                }

                is Result.Error -> {}
            }
        }
    }

    private suspend fun loadCloth() {
        when (val result = clothesRepository.getCloth(args.clothId)) {
            is Result.Success -> {
                val cloth = result.value
                mutableCloth.postValue(cloth)
            }

            is Result.Error -> {}
        }
    }
}
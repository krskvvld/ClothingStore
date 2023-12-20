package ru.karsakov.clothingstore.domain.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.karsakov.clothingstore.core.BaseRepository
import ru.karsakov.clothingstore.core.Result
import ru.karsakov.clothingstore.data.dao.ClothesDao
import ru.karsakov.clothingstore.data.models.ClothLocalModel
import ru.karsakov.clothingstore.data.models.asDomainModel
import ru.karsakov.clothingstore.data.models.asLocalModel
import ru.karsakov.clothingstore.domain.models.ClothModel
import javax.inject.Inject

class ClothesRepository @Inject constructor(
    private val clothsDao: ClotesDao,
) : BaseRepository() {

    suspend fun getAllClothes(): Result<List<ClothModel>> = withContext(Dispatchers.IO) {
        handleResult {
            clothsDao.selectAllClothes().map(ClothLocalModel::asDomainModel)
        }
    }

    suspend fun getCloth(clothId: Long): Result<ClothModel> = withContext(Dispatchers.IO) {
        handleResult {
            clothsDao.selectClothById(clothId = clothId).asDomainModel()
        }
    }

    suspend fun getCartClothes(): Result<List<ClothModel>> = withContext(Dispatchers.IO) {
        handleResult {
            clothsDao.selectClothesInCart().map(ClothLocalModel::asDomainModel)
        }
    }

    suspend fun addClothToCart(cloth: ClothModel): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            if (!cloth.inCart) {
                clothsDao.updateGCloth(
                    cloth = cloth.copy(inCart = true).asLocalModel()
                )
            }
        }
    }

    suspend fun removeClothFromCart(cloth: ClothModel): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            if (cloth.inCart) {
                clothsDao.updateCloth(
                    cloth = cloth.copy(inCart = false).asLocalModel()
                )
            }
        }
    }

    suspend fun buyClothes(clothes: List<ClothModel>): Result<Unit> = withContext(Dispatchers.IO) {
        handleResult {
            val localClothes = clothes
                .map(ClothModel::asLocalModel)
                .map { it.copy(inCart = false) }
            clothsDao.updateClothes(localClothes)
        }
    }
}
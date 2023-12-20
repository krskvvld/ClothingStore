package ru.karsakov.clothingstore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.karsakov.clothingstore.data.dao.ClothesDao
import ru.karsakov.clothingstore.data.models.ClothLocalModel

@Database(
    entities = [ClothLocalModel::class],
    version = 1,
    exportSchema = false,
)
abstract class ClothStoreDatabase: RoomDatabase() {

    abstract val clothsDao: ClothesDao
}
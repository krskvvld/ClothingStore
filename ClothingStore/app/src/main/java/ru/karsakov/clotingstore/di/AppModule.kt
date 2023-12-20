package ru.karsakov.clothingstore.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.karsakov.clothingstore.data.dao.ClothesDao
import ru.karsakov.clothingstore.data.db.ClothStoreDatabase
import ru.karsakov.clothingstore.domain.repositories.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideClothesStoreDatabase(
        @ApplicationContext context: Context,
    ): ClothStoreDatabase {
        return Room
            .databaseBuilder(
                context,
                ClothStoreDatabase::class.java,
                "clotingstore.db",
            )
            .createFromAsset("clotingstore.db")
            .build()
    }

    @Provides
    fun provideClothesDao(database: ClothStoreDatabase): ClothsDao = database.clothesDao

    @Provides
    fun provideUserRepository(): UserRepository = UserRepository()
}
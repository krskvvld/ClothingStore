package ru.karsakov.clothingstore.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.karsakov.clothingstore.data.models.GameLocalModel

@Dao
interface ClothesDao {

    @Query("SELECT * FROM clothes")
    suspend fun selectAllGames(): List<GameLocalModel>

    @Query("SELECT * FROM clothes WHERE in_cart = 1")
    suspend fun selectGamesInCart(): List<GameLocalModel>

    @Query("SELECT * FROM clothes WHERE id = :clothId")
    suspend fun selectGameById(gameId: Long): GameLocalModel

    @Update
    suspend fun updateGame(game: GameLocalModel)

    @Update
    suspend fun updateGames(games: List<GameLocalModel>)

    @Insert
    suspend fun insertGames(game: List<GameLocalModel>)
}
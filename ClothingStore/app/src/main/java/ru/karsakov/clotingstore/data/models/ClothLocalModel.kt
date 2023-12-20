package ru.karsakov.clothingstore.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.karsakov.clothingstore.domain.models.ClothModel
import java.time.LocalDate

@Entity(tableName = "clothes")
data class ClothLocalModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "logo_url")
    val logoUrl: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "tags")
    val tags: String,
    @ColumnInfo(name = "release_ts")
    val releaseTs: Long,
    @ColumnInfo(name = "designer")
    val developer: String,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "in_cart")
    val inCart: Boolean,
)

fun ClothLocalModel.asDomainModel(): ClothModel = ClothModel(
    id = id,
    name = name,
    price = price,
    logoUrl = logoUrl,
    imageUrl = imageUrl,
    tags = tags.split(","),
    releaseDate = LocalDate.ofEpochDay(releaseTs),
    developer = developer,
    publisher = publisher,
    description = description,
    inCart = inCart,
)

fun ClothModel.asLocalModel(): ClothLocalModel = ClothLocalModel(
    id = id,
    name = name,
    price = price,
    logoUrl = logoUrl,
    imageUrl = imageUrl,
    tags = tags.joinToString(","),
    releaseTs = releaseDate.toEpochDay(),
    developer = developer,
    publisher = publisher,
    description = description,
    inCart = inCart,
)
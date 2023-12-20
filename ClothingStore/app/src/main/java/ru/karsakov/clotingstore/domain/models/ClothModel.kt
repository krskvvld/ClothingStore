package ru.karsakov.clothingstore.domain.models

import java.time.LocalDate

data class ClothModel(
    val id: Long,
    val name: String,
    val price: Float,
    val logoUrl: String,
    val imageUrl: String,
    val tags: List<String>,
    val releaseDate: LocalDate,
    val developer: String,
    val publisher: String,
    val description: String,
    val inCart: Boolean,
)

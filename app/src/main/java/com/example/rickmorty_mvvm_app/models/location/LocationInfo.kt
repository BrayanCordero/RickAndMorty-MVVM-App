package com.example.rickmorty_mvvm_app.models.location

import java.io.Serializable

data class LocationInfo(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val created: String,
    val residents: List<String?>?
): Serializable

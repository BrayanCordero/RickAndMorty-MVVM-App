package com.example.rickmorty_mvvm_app.models.character

import java.io.Serializable

data class CharacterInfo(
    val id : Int,
    val name: String,
    val species: String,
    val origin: String,
    val status: String,
    val location: String,
    val imageUrl: String,
    val episodes: List<String?>

):Serializable

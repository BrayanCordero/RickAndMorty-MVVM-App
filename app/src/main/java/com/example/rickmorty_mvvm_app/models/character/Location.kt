package com.example.rickmorty_mvvm_app.models.character


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)
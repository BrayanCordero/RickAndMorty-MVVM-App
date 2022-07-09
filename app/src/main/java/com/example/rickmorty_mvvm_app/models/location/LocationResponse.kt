package com.example.rickmorty_mvvm_app.models.location


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Location?>?
)
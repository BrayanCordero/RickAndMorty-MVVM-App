package com.example.rickmorty_mvvm_app.domain

import com.example.rickmorty_mvvm_app.models.location.Location

data class DomainLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val created: String,
    val residents: List<String?>?

)

fun List<Location?>?.mapToDomainLocation():List<DomainLocation>{
    return this?.let{
        it.map{network ->
            DomainLocation(
                id = network?.id ?: 99999,
                name = network?.name ?: "Unknown",
                type = network?.type ?: "Unknown",
                dimension = network?.dimension ?: "Unknown",
                created = network?.created ?: "Unknown",
                residents = network?.residents ?: emptyList()
            )
        }
    } ?: emptyList()
}

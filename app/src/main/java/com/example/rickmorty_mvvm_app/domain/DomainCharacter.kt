package com.example.rickmorty_mvvm_app.domain


import com.example.rickmorty_mvvm_app.models.character.Character


data class DomainCharacter(
    val id : Int,
    val name: String,
    val species: String,
    val origin: String,
    val status: String,
    val location: String,
    val imageUrl: String,
    val episodes: List<String?>

)

fun List<Character?>?.mapToDomainCharacter():List<DomainCharacter>{
    return this?.let{
        it.map{ network->
            DomainCharacter(
                id = network?.id ?: 99999,
                name = network?.name ?: "Unknown",
                species = network?.species ?: "Unknown",
                origin = network?.origin?.name ?: "Unknown",
                status = network?.status ?: "Unknown",
                location = network?.location?.name ?: "Unknown",
                imageUrl = network?.image ?: "NOT AVAILABLE",
                episodes = network?.episode ?: emptyList()
            )
        }
    } ?: emptyList()
}

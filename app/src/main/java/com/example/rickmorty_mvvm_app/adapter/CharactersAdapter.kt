package com.example.rickmorty_mvvm_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorty_mvvm_app.databinding.CharacterItemBinding
import com.example.rickmorty_mvvm_app.domain.DomainCharacter
import com.example.rickmorty_mvvm_app.models.character.Character


class CharactersAdapter(
    private val charactersSet: MutableList<DomainCharacter> = mutableListOf(),
//    private val charactersSet: MutableList<Character> = mutableListOf(),
    private val onCharacterClicked:(String?)-> Unit
):RecyclerView.Adapter<CharactersViewHolder>() {

    fun updateNewCharacters(newCharacter: List<DomainCharacter>){
        charactersSet.clear()
        charactersSet.addAll(newCharacter)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder =
        CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(charactersSet[position],onCharacterClicked)

    override fun getItemCount(): Int = charactersSet.size
}


class CharactersViewHolder(
    private val binding: CharacterItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(character: DomainCharacter, onCharacterClicked:(String?)->Unit){
        binding.characterIdText.text = character.id.toString()
        binding.characterNameText.text = character.name
        binding.characterLocationText.text = character.location
        binding.characterStatusText.text = character.status

        Glide.with(binding.root)
            .load(character.imageUrl)
            .override(300,300)
            .into(binding.characterImage)

        itemView.setOnClickListener { onCharacterClicked(character.id.toString()) }
    }
}


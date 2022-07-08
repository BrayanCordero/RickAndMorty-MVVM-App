package com.example.rickmorty_mvvm_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorty_mvvm_app.databinding.CharacterItemBinding
import com.example.rickmorty_mvvm_app.domain.DomainCharacter


class CharactersAdapter(
    private val onCharacterClick: CharacterClick,
    private val charactersSet: MutableList<DomainCharacter> = mutableListOf()
):RecyclerView.Adapter<CharactersViewHolder>() {

    fun updateNewCharacters(newCharacter: List<DomainCharacter>){
        charactersSet.addAll(newCharacter)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(charactersSet[position], onCharacterClick)

    override fun getItemCount(): Int = charactersSet.size


}


class CharactersViewHolder(
    private val binding: CharacterItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(character: DomainCharacter, onCharacterItemClicked:CharacterClick){
        binding.characterIdText.text = character.id.toString()
        binding.characterNameText.text = character.name
        binding.characterLocationText.text = character.location
        binding.characterStatusText.text = character.status

        Glide.with(binding.root)
            .load(character.imageUrl)
            .override(300,300)
            .into(binding.characterImage)

        itemView.setOnClickListener{
            onCharacterItemClicked.onCharacterClicked(character)
        }
    }
}

interface CharacterClick{
    fun onCharacterClicked(character:DomainCharacter)
}


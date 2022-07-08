package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.rickmorty_mvvm_app.R
import com.example.rickmorty_mvvm_app.databinding.FragmentCharacterDetailsBinding
import com.example.rickmorty_mvvm_app.models.character.CharacterInfo

class CharacterDetailsFragment : Fragment() {

    private val binding by lazy{
        FragmentCharacterDetailsBinding.inflate(layoutInflater)
    }

    private var characterInfo: CharacterInfo?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterInfo = it.getSerializable(CHARACTER_DATA)as CharacterInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.characterIdDetails.text = characterInfo?.id.toString()
        binding.characterNameDetails.text = characterInfo?.name
        binding.characterSpeciesDetails.text = characterInfo?.species
        binding.characterOriginDetails.text = characterInfo?.origin
        binding.characterStatusDetails.text = characterInfo?.status
        binding.characterLocationDetails.text = characterInfo?.location
        binding.characterEpisodesDetails.text = characterInfo?.episodes?.size.toString()

        Glide.with(binding.root)
            .load(characterInfo?.imageUrl)
            .override(300,300)
            .into(binding.characterImageDetails)



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        characterInfo = null
    }

    companion object {
        const val CHARACTER_DATA = "CHARACTER_DATA"
    }
}
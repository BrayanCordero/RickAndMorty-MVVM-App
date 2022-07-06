package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty_mvvm_app.R
import com.example.rickmorty_mvvm_app.adapter.CharactersAdapter
import com.example.rickmorty_mvvm_app.databinding.FragmentCharactersBinding
import com.example.rickmorty_mvvm_app.domain.DomainCharacter
import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import com.example.rickmorty_mvvm_app.utils.UIState

class CharactersFragment : BaseFragment() {

    private val binding by lazy{
        FragmentCharactersBinding.inflate(layoutInflater)
    }

    private val characterAdapter by lazy{
        CharactersAdapter{

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.charactersRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = characterAdapter
        }

        rickAndMortyViewModel.characters.observe(viewLifecycleOwner){ state->
            when(state){
                is UIState.LOADING->{
                    binding.charactersRecycler.visibility = View.GONE
                }
                is UIState.SUCCESS<*> ->{
                    (state as UIState.SUCCESS<CharacterResponse>).response
                    binding.charactersRecycler.visibility = View.VISIBLE

                    characterAdapter.updateNewCharacters(state.response.results?: emptyList())
                }
                is UIState.ERROR->{
                    binding.charactersRecycler.visibility = View.GONE

                    showError(state.error.localizedMessage){
                        rickAndMortyViewModel.getAllCharacters()
                    }
                }
            }
        }


        rickAndMortyViewModel.getAllCharacters()

        return binding.root
    }


}
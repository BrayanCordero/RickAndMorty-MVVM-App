package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty_mvvm_app.R
import com.example.rickmorty_mvvm_app.adapter.CharacterClick
import com.example.rickmorty_mvvm_app.adapter.CharactersAdapter
import com.example.rickmorty_mvvm_app.databinding.FragmentCharactersBinding
import com.example.rickmorty_mvvm_app.domain.DomainCharacter
import com.example.rickmorty_mvvm_app.models.character.CharacterInfo
import com.example.rickmorty_mvvm_app.utils.UIState

class CharactersFragment : BaseFragment() {

    private val binding by lazy{
        FragmentCharactersBinding.inflate(layoutInflater)
    }

    private val characterAdapter by lazy{
        CharactersAdapter(object : CharacterClick{
            override fun onCharacterClicked(character: DomainCharacter) {
                val characterInfo = CharacterInfo(character.id,character.name,character.species,character.origin,
                character.status,character.location,character.imageUrl,character.episodes)
                findNavController().navigate(R.id.action_navigation_characters_fragment_to_navigation_character_details_fragment, bundleOf(
                    Pair(CharacterDetailsFragment.CHARACTER_DATA, characterInfo)
                ))
            }
        })
    }

    private val startingPage = 1
    private var isLoading = false
//    private var total = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val  linearLayout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.charactersRecycler.apply {
            layoutManager = linearLayout
            adapter = characterAdapter
        }

        rickAndMortyViewModel.characters.observe(viewLifecycleOwner){ state->
            when(state){
                is UIState.LOADING->{
                    binding.charactersRecycler.visibility = View.GONE

                }
                is UIState.SUCCESS<*> ->{
                    (state as UIState.SUCCESS<List<DomainCharacter>>).response
                    binding.charactersRecycler.visibility = View.VISIBLE

                    characterAdapter.updateNewCharacters(state.response)
                    isLoading = false
                }
                is UIState.ERROR->{
                    binding.charactersRecycler.visibility = View.GONE

                    showError(state.error.localizedMessage){
                        rickAndMortyViewModel.getAllCharacters(startingPage)
                    }
                }
            }
        }

        binding.charactersRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount:Int = binding.charactersRecycler.layoutManager!!.childCount
                val lastVisibleItem: Int = linearLayout.findLastVisibleItemPosition()
//                if(total != null){
                var total = linearLayout.itemCount
//                else{
//                    total = rickAndMortyViewModel.recyclerChildCount
//                }

                Log.d("Scroll","$visibleItemCount")
                Log.d("Scroll","$lastVisibleItem")
                Log.d("Scroll","$total")
                if(!isLoading && total<827) {
                    if (lastVisibleItem == total-1) {
                         var currentPage = total / 20
                         var nextPage = currentPage + 1
                        rickAndMortyViewModel.getAllCharacters(nextPage)
                        isLoading = true
                    }
                }
            }
        })

        if(rickAndMortyViewModel.recyclerState == null){
            rickAndMortyViewModel.getAllCharacters(startingPage)
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rickAndMortyViewModel.recyclerState = binding.charactersRecycler.layoutManager?.onSaveInstanceState()
//        rickAndMortyViewModel.recyclerChildCount = binding.charactersRecycler.layoutManager!!.itemCount

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        rickAndMortyViewModel.recyclerState?.let {
            binding.charactersRecycler.layoutManager?.onRestoreInstanceState(it)
        }
        rickAndMortyViewModel.recyclerState = null


    }

    override fun onStop() {
        super.onStop()
        rickAndMortyViewModel.characters.removeObservers(viewLifecycleOwner)

    }


}
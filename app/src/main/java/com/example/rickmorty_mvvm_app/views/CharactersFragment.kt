package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val startingPage = 1
   private var isLoading = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val linearLayout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
                val total = linearLayout.itemCount

                Log.d("Scroll","$visibleItemCount")
                Log.d("Scroll","$lastVisibleItem")
                Log.d("Scroll","$total")
                if(!isLoading) {
                    if (lastVisibleItem == total-1) {
                        var currentPage = total / 20
                        var nextPage = currentPage + 1
                        rickAndMortyViewModel.getAllCharacters(nextPage)
                        isLoading = true
                    }
                }
            }
        })


        rickAndMortyViewModel.getAllCharacters(startingPage)

        return binding.root
    }


}
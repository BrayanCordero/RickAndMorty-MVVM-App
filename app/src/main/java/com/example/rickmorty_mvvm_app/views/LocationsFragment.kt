package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import android.provider.Contacts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty_mvvm_app.R
import com.example.rickmorty_mvvm_app.adapter.LocationClick
import com.example.rickmorty_mvvm_app.adapter.LocationsAdapter
import com.example.rickmorty_mvvm_app.databinding.FragmentLocationsBinding
import com.example.rickmorty_mvvm_app.domain.DomainLocation
import com.example.rickmorty_mvvm_app.models.location.LocationInfo
import com.example.rickmorty_mvvm_app.utils.UIState
import okhttp3.internal.wait


class LocationsFragment : BaseFragment(){

    private val binding by lazy{
        FragmentLocationsBinding.inflate(layoutInflater)
    }

    private val locationAdapter by lazy{
        LocationsAdapter(object : LocationClick{
            override fun onLocationClicked(location: DomainLocation) {
                val locationInfo = LocationInfo(location.id,location.name,location.type,
                location.dimension, location.created,location.residents)
                findNavController().navigate(R.id.action_navigation_locations_fragment_to_navigation_location_details_fragment, bundleOf(
                    Pair(LocationDetailsFragment.LOCATION_DATA, locationInfo)
                ))
            }
        })
    }

    private val staringPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val linearLayout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.locationRecycler.apply {
            layoutManager = linearLayout
            adapter = locationAdapter
        }

        rickAndMortyViewModel.locations.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.LOADING->{
                    binding.locationRecycler.visibility = View.GONE
                }
                is UIState.SUCCESS<*>->{
                    (state as UIState.SUCCESS<List<DomainLocation>>).response
                    binding.locationRecycler.visibility = View.VISIBLE

                    locationAdapter.updateNewLocations(state.response)
                    isLoading = false
                }
                is UIState.ERROR->{
                    binding.locationRecycler.visibility = View.GONE

                    showError(state.error.localizedMessage){
                        rickAndMortyViewModel.getAllLocations(staringPage)
                    }
                }
            }
        }

        binding.locationRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem: Int = linearLayout.findLastVisibleItemPosition()
                val total = linearLayout.itemCount

                if(!isLoading && total <127){
                    if(lastVisibleItem == total-1){
                        var currentPage = total/20
                        var nextPage = currentPage+1
                        rickAndMortyViewModel.getAllLocations(nextPage)
                        isLoading = true
                    }
                }

            }
        })

        rickAndMortyViewModel.getAllLocations(staringPage)

        return binding.root
    }

    companion object {

    }
}
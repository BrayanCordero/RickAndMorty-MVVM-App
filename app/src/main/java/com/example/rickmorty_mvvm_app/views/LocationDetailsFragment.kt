package com.example.rickmorty_mvvm_app.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickmorty_mvvm_app.R
import com.example.rickmorty_mvvm_app.databinding.FragmentLocationDetailsBinding
import com.example.rickmorty_mvvm_app.models.location.LocationInfo


class LocationDetailsFragment : Fragment() {

    private val binding by lazy{
        FragmentLocationDetailsBinding.inflate(layoutInflater)
    }

    private var locationInfo: LocationInfo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            locationInfo = it.getSerializable(LOCATION_DATA) as LocationInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.locationDetailsId.text = locationInfo?.id.toString()
        binding.locationDetailsName.text = locationInfo?.name
        binding.locationDetailsType.text = locationInfo?.type
        binding.locationDetailsDimension.text = locationInfo?.dimension
        binding.locationDetailsCreated.text = locationInfo?.created


        return binding.root
    }

    companion object {
        const val LOCATION_DATA = "LOCATION_DATA"
    }
}
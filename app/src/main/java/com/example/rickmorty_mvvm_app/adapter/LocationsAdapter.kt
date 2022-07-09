package com.example.rickmorty_mvvm_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorty_mvvm_app.databinding.FragmentCharacterDetailsBinding
import com.example.rickmorty_mvvm_app.databinding.LocationItemBinding
import com.example.rickmorty_mvvm_app.domain.DomainLocation

class LocationsAdapter(
    private val onLocationClick: LocationClick,
    private val locationSet: MutableList<DomainLocation> = mutableListOf()
): RecyclerView.Adapter<LocationViewHolder>() {

    fun updateNewLocations(newLocation : List<DomainLocation>){
        locationSet.addAll(newLocation)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int)=
        holder.bind(locationSet[position], onLocationClick)

    override fun getItemCount(): Int = locationSet.size
}

class LocationViewHolder(
    private val binding: LocationItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(location: DomainLocation,onLocationItemClicked:LocationClick){
        binding.locationIdText.text = location.id.toString()
        binding.locationNameText.text = location.name

        itemView.setOnClickListener {
            onLocationItemClicked.onLocationClicked(location)
        }
    }
}

interface LocationClick{
    fun onLocationClicked(location: DomainLocation)
}
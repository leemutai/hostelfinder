package com.example.hostel_locator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel_locator.databinding.ListingItemBinding

class ListingsAdapter(
    private val listingItemsName: List<String>,
    private val listingItemPrice: List<String>,
    private val listingRating: List<String>,
    private val listingLocation: List<String>,
    private val listingHseType: List<String>,
    private val listingBed: List<String>,
    private val ListingImage: List<Int>,
) : RecyclerView.Adapter<ListingsAdapter.ListingsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingsViewHolder {
        val binding = ListingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListingsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ListingsViewHolder, position: Int) {
         holder.bind(position)
    }
    override fun getItemCount(): Int = listingItemsName.size
    fun updateListings(
        filteredListingItemName: List<String>,
        filteredListingItemPrice: List<String>,
        filteredListingRating: List<String>,
        filteredListingLocation: List<String>,
        filteredListingHseType: List<String>,
        filteredListingBed: List<String>,
        filteredListingImage: List<Int>,
    ) {


    }

    inner class ListingsViewHolder(private val binding:ListingItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                listingsApartName.text = listingItemsName[position]
                listingsPrice.text = listingItemPrice[position]
                listingsRating.text = listingRating[position]
                listingsLocation.text = listingLocation[position]
                listingsHseType.text = listingHseType[position]
                listingsBedSize.text = listingBed[position]
                listingsImage.setImageResource(ListingImage[position])
            }

        }

    }
}
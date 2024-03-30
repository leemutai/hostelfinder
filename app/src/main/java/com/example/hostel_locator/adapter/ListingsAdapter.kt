package com.example.hostel_locator.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostel_locator.DetailsActivity
import com.example.hostel_locator.databinding.ListingItemBinding
import com.example.hostel_locator.model.ListingProperty

class ListingsAdapter(
    private val listingsPropertys: List<ListingProperty>,
    private val requireContext: Context,
) : RecyclerView.Adapter<ListingsAdapter.ListingsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingsViewHolder {
        val binding = ListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListingsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListingsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listingsPropertys.size
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

    inner class ListingsViewHolder(private val binding: ListingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val listingProperty: ListingProperty = listingsPropertys[position]

            //an intent to open details activity and parse data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("ListingPropertyName", listingProperty.listingName)
                putExtra("ListingPropertyPrice", listingProperty.listingPrice)
                putExtra("ListingPropertyRating", listingProperty.listingRating)
                putExtra("ListingPropertyLocation", listingProperty.listingLocation)
                putExtra("ListingPropertyHseType", listingProperty.listingHseType)
                putExtra("ListingPropertyBedsize", listingProperty.listingBedsize)
                putExtra("ListingPropertyImage", listingProperty.listingImage)
                putExtra("ListingDescription",listingProperty.listingDescription)
            }
            //start the details activity
            requireContext.startActivity(intent)
        }


        //set data to recycler view propertyname, price,image,,etc
        fun bind(position: Int) {
            val listingProperty: ListingProperty = listingsPropertys[position]
            binding.apply {
                listingsApartName.text = listingProperty.listingName
                listingsPrice.text = listingProperty.listingPrice
                listingsRating.text = listingProperty.listingRating
                listingsLocation.text = listingProperty.listingLocation
                listingsHseType.text = listingProperty.listingHseType
                listingsBedSize.text = listingProperty.listingBedsize
                val uri = Uri.parse(listingProperty.listingImage)
                Glide.with(requireContext).load(uri).into(listingsImage)


            }

        }

    }
}




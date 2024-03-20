package com.example.hostel_locator.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel_locator.DetailsActivity
import com.example.hostel_locator.databinding.ListingItemBinding

class ListingsAdapter(
    private val listingItemsName: List<String>,
    private val listingItemPrice: List<String>,
    private val listingRating: List<String>,
    private val listingLocation: List<String>,
    private val listingHseType: List<String>,
    private val listingBed: List<String>,
    private val ListingImage: List<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<ListingsAdapter.ListingsViewHolder>() {
    private val itemClickListener: View.OnClickListener?= null

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
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(position)
                }
                //setomclick to open details activity
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("ListingsItemsName", listingItemsName.get(position))
                intent.putExtra("ListingsPrice", listingItemPrice.get(position))
                intent.putExtra("ListingsRating", listingRating.get(position))
                intent.putExtra("ListingsLocation", listingLocation.get(position))
                intent.putExtra("ListingsHousetype", listingHseType.get(position))
                intent.putExtra("ListingsBed", listingBed.get(position))
                intent.putExtra("ListingsImages",ListingImage.get(position))
                // Start the DetailsActivity when an item is clicked
                requireContext.startActivity(intent)

            }
        }
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

private fun View.OnClickListener?.onItemClick(position: Int) {
}

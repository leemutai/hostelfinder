package com.example.hostel_locator.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.R
import com.example.hostel_locator.adapter.ListingsAdapter
import com.example.hostel_locator.databinding.FragmentMapBinding


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var adapter: ListingsAdapter

    private val originalListingItemName = listOf("Dancy", "Qwety", "Kona", "Zipy")
    private val originalListingItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
    private val originalListingRating = listOf("4.9", "3.5", "3.3", "2.0")
    private val originalListingLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
    private val originalListingHseType = listOf("Apartment", "Studio", "2bd", "3bd")
    private val originalListingBed = listOf("3.0", "1.0", "2.0", "3.0")
    private val originalListingImage = listOf(
        R.drawable.hostel,
        R.drawable.hostel2,
        R.drawable.hostel,
        R.drawable.hostel2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val filteredListingItemName = mutableListOf<String>()
    private val filteredListingItemPrice =mutableListOf<String>()
    private val filteredListingRating = mutableListOf<String>()
    private val filteredListingLocation = mutableListOf<String>()
    private val filteredListingHseType= mutableListOf<String>()
    private val filteredListingBed = mutableListOf<String>()
    private val filteredListingImage = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        adapter = ListingsAdapter(
            filteredListingItemName,
            filteredListingItemPrice,
            filteredListingRating,
            filteredListingLocation,
            filteredListingHseType,
            filteredListingBed,
            filteredListingImage
        )
        binding.listingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.listingsRecyclerView.adapter = adapter

        //setup for serachview
        setUpSearchView()

        //show all listingsItmes
        showAllListings()
        return binding.root
    }

    private fun showAllListings() {
        filteredListingItemName.clear()
        filteredListingItemPrice.clear()
        filteredListingRating.clear()
        filteredListingLocation.clear()
        filteredListingHseType.clear()
        filteredListingBed.clear()
        filteredListingImage.clear()

        filteredListingItemName.addAll(originalListingItemName)
        filteredListingItemPrice.addAll(originalListingItemPrice)
        filteredListingRating.addAll(originalListingRating)
        filteredListingLocation.addAll(originalListingLocation)
        filteredListingHseType.addAll(originalListingHseType)
        filteredListingBed.addAll(originalListingBed)
        filteredListingImage.addAll(originalListingImage)

        adapter.notifyDataSetChanged()
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterListingItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterListingItems(newText)
                return true
            }
        })

    }

    private fun filterListingItems(query: String) {
        filteredListingItemName.clear()
        filteredListingItemPrice.clear()
        filteredListingRating.clear()
        filteredListingLocation.clear()
        filteredListingHseType.clear()
        filteredListingBed.clear()
        filteredListingImage.clear()

        originalListingItemName.forEachIndexed { index, apartName ->
            if (apartName.contains(query.toString(),ignoreCase = true)){
                filteredListingItemName.add(apartName)
                filteredListingItemPrice.add(originalListingItemPrice[index])
                filteredListingRating.add(originalListingRating[index])
                filteredListingLocation.add(originalListingLocation[index])
                filteredListingHseType.add(originalListingHseType[index])
                filteredListingBed.add(originalListingBed[index])
                filteredListingImage.add(originalListingImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }
}
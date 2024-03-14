package com.example.hostel_locator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.adapter.ListingsAdapter
import com.example.hostel_locator.databinding.FragmentListingsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ListingsBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentListingsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListingsBottomSheetBinding.inflate(inflater,container,false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        val listingItemsName = listOf("Dancy", "Qwety", "Kona", "Zipy")
        val listingItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
        val listingRating = listOf("4.9", "3.5", "3.3", "2.0")
        val listingLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
        val listingHseType = listOf("Apartment", "Studio", "2bd", "3bd")
        val listingBed = listOf("3.0", "1.0", "2.0", "3.0")
        val ListingImage = listOf(
            R.drawable.hostel,
            R.drawable.hostel2,
            R.drawable.hostel,
            R.drawable.hostel2
        )
        val adapter = ListingsAdapter(
            ArrayList(listingItemsName),
            ArrayList(listingItemPrice),
            ArrayList(listingRating),
            ArrayList(listingLocation),
            ArrayList(listingHseType),
            ArrayList(listingBed),
            ArrayList(ListingImage),
        )
        binding.listingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.listingsRecyclerView.adapter = adapter
        return binding.root
    }

}
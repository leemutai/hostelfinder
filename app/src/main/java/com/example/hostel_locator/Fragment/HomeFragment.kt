package com.example.hostel_locator.Fragment

import Row1Adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hostel_locator.ListingsBottomSheetFragment
import com.example.hostel_locator.R
import com.example.hostel_locator.adapter.ListingsAdapter
//import com.example.hostel_locator.adapter.Row1Adapter
import com.example.hostel_locator.databinding.FragmentHomeBinding
import com.example.hostel_locator.model.ListingProperty
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var listingPropertys: MutableList<ListingProperty>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewAllListings.setOnClickListener {
            val bottomSheetDialog = ListingsBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        //retriveRow1ListingProperties
        retrieveAndDisplayRow1Items()
        return binding.root


    }

    private fun retrieveAndDisplayRow1Items() {
        //get refrefernce to the database
        database = FirebaseDatabase.getInstance()
        val listingRef:DatabaseReference = database.reference.child("listing")
        listingPropertys = mutableListOf()

        //retriev listng propertys form the database
        listingRef.addListenerForSingleValueEvent(object  :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (listingSnapshot in snapshot.children){
                    val listingProperty = listingSnapshot.getValue(ListingProperty::class.java)
                    listingProperty?.let { listingPropertys.add(it) }
                }
                //display random popular listings
                randomListingProperties()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun randomListingProperties() {
        //create the shuffle list of listing property
        val index = listingPropertys.indices.toList().shuffled()
        val numListingToShow = 6
        val subsetListingPropertys = index.take(numListingToShow).map { listingPropertys[it] }

        setPopularListingsAdapter(subsetListingPropertys)
    }

    private fun setPopularListingsAdapter(subsetListingPropertys: List<ListingProperty>) {
        val adapter = ListingsAdapter(
             subsetListingPropertys,
            requireContext()
        )
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.hostel7, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.hostel2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.hostel, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
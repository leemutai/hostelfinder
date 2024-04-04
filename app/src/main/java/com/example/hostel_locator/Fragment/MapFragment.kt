package com.example.hostel_locator.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.adapter.ListingsAdapter
import com.example.hostel_locator.databinding.FragmentMapBinding
import com.example.hostel_locator.model.ListingProperty
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var adapter: ListingsAdapter
    private lateinit var database: FirebaseDatabase
    private val originalListingProperties = mutableListOf<ListingProperty>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

        //retrieve listing items from database
        retrieveListingProperty()
        //setup for traceview
        setUpSearchView()
        //show all listingsItems
        return binding.root
    }

    private fun retrieveListingProperty() {
        //get database reference
        database = FirebaseDatabase.getInstance()
        //reference to the Listings node
        val listingReference: DatabaseReference = database.reference.child("listing")
        listingReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (listingSnapshot in snapshot.children) {
                    val listingProperty = listingSnapshot.getValue(ListingProperty::class.java)
                    listingProperty?.let {
                        originalListingProperties.add(it)
                    }
                }
                showAllListing()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun showAllListing() {
        val filteredListingProperty = ArrayList(originalListingProperties)
        setAdapter(filteredListingProperty)
    }

    private fun setAdapter(filteredListingProperty: List<ListingProperty>) {
        adapter = ListingsAdapter(filteredListingProperty, requireContext())
        binding.listingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.listingsRecyclerView.adapter = adapter
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        val filteredListingProperty = originalListingProperties.filter {
            it.listingName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredListingProperty)
    }
}

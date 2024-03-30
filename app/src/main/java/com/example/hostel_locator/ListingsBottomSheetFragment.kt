package com.example.hostel_locator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.adapter.ListingsAdapter
import com.example.hostel_locator.databinding.FragmentListingsBottomSheetBinding
import com.example.hostel_locator.model.ListingProperty
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ListingsBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentListingsBottomSheetBinding

    private lateinit var database : FirebaseDatabase
    private lateinit var listingPropertys: MutableList<ListingProperty>

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
        retrieveListingPropertys()

        return binding.root
    }

    private fun retrieveListingPropertys() {
        database = FirebaseDatabase.getInstance()
        val listingRef = database.reference.child("listing")
        listingPropertys = mutableListOf()

        listingRef.addListenerForSingleValueEvent(object  :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (listingSnapshot in snapshot.children){
                    val listingProperty = listingSnapshot.getValue(ListingProperty::class.java)
                    listingProperty?.let { listingPropertys.add(it) }
                }
                Log.d("PROPERTYS","onDataChange:  Data Received")
                //once data is received set to adapter
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun setAdapter() {
        if (listingPropertys.isNotEmpty()){
            val adapter = ListingsAdapter(listingPropertys,requireContext())
            binding.listingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.listingsRecyclerView.adapter = adapter
            Log.d("PROPERTYS","setAdapter: data set  ")
        }else{
            Log.d("PROPERTYS","setAdapter: data Not  set  ")
        }

    }

}
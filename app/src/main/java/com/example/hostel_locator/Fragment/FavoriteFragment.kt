package com.example.hostel_locator.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.BookingActivity
import com.example.hostel_locator.CongratsBottomSheet
import com.example.hostel_locator.R
import com.example.hostel_locator.adapter.FavoriteAdapter
import com.example.hostel_locator.databinding.FragmentFavoriteBinding
import com.example.hostel_locator.model.FavListings
import com.example.hostel_locator.model.ListingProperty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.util.ArrayList

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var favoriteItems: MutableList<String>
    private lateinit var favoriteItemPrice: MutableList<String>
    private lateinit var favoriteRating: MutableList<String>
    private lateinit var favoriteLocation: MutableList<String>
    private lateinit var favoriteHseType: MutableList<String>
    private lateinit var favoriteBed: MutableList<String>
    private lateinit var favoriteImage: MutableList<String>
//    private lateinit var favoriteQuantity:MutableList<Int>
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)



        auth = FirebaseAuth.getInstance()
        retriveFavoriteListings()


//        val favoriteItems = listOf("Dancy", "Qwety", "Kona", "Zipy")
//        val favoriteItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
//        val favoriteRating = listOf("4.9", "3.5", "3.3", "2.0")
//        val favoriteLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
//        val favoriteHseType = listOf("Apartment", "Studio", "2bd", "3bd")
//        val favoriteBed = listOf("3.0", "1.0", "2.0", "3.0")
//        val favoriteImage = listOf(
//            R.drawable.hostel,
//            R.drawable.hostel2,
//            R.drawable.hostel,
//            R.drawable.hostel2
//        )


        binding.proceedButton.setOnClickListener {
            //get booking listings before proceeding to check out
            getBookingListingsDetails()
//            val intent = Intent(requireContext(), BookingActivity::class.java)
//            startActivity(intent)
        }


        return binding.root
    }

    private fun getBookingListingsDetails() {

        val bookingIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("FavoriteItems")

        val listingName = mutableListOf<String>()
        val listingPrice = mutableListOf<String>()
        val listingRating = mutableListOf<String>()
        val listingLocation = mutableListOf<String>()
        val listingHseType = mutableListOf<String>()
        val listingBedsize = mutableListOf<String>()
        val listingImage = mutableListOf<String>()
        //get listing quanties
//        val listingQuantities = favoriteAdapter.getUpdatedListingsQuanties()

        bookingIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (listingSnapshot in snapshot.children) {
                    // get the listingProperty to respective list
                    val bookingListings = listingSnapshot.getValue(FavListings::class.java)
                    //add listing items to list
                    bookingListings?.listingName?.let { listingName.add(it) }
                    bookingListings?.listingPrice?.let { listingPrice.add(it) }
                    bookingListings?.listingRating?.let { listingRating.add(it) }
                    bookingListings?.listingLocation?.let { listingLocation.add(it) }
                    bookingListings?.listingHseType?.let { listingHseType.add(it) }
                    bookingListings?.listingBedsize?.let { listingBedsize.add(it) }
                    bookingListings?.listingImage?.let { listingImage.add(it) }
                }
                bookNow(
                    listingName,
                    listingPrice,
                    listingRating,
                    listingLocation,
                    listingHseType,
                    listingBedsize,
                    listingImage
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Booking Failed Please Try Again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun bookNow(
        listingName: MutableList<String>,
        listingPrice: MutableList<String>,
        listingRating: MutableList<String>,
        listingLocation: MutableList<String>,
        listingHseType: MutableList<String>,
        listingBedsize: MutableList<String>,
        listingImage: MutableList<String>,
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), BookingActivity::class.java)
            intent.putExtra("ListingPropertyName", listingName as ArrayList<String>)
            intent.putExtra("ListingPropertyPrice", listingPrice as ArrayList<String>)
            intent.putExtra("ListingPropertyRating", listingRating as ArrayList<String>)
            intent.putExtra("ListingPropertyLocation", listingLocation as ArrayList<String>)
            intent.putExtra("ListingPropertyHseType", listingHseType as ArrayList<String>)
            intent.putExtra("ListingPropertyBedsize", listingBedsize as ArrayList<String>)
            intent.putExtra("ListingPropertyImage", listingImage as ArrayList<String>)
            startActivity(intent)
        }
    }

    private fun retriveFavoriteListings() {
        // database reference to firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val listingReference = database.reference.child("user").child(userId).child("FavoriteItems")
        //list to store favorite listings
        favoriteItems = mutableListOf()
        favoriteItemPrice = mutableListOf()
        favoriteRating = mutableListOf()
        favoriteLocation = mutableListOf()
        favoriteHseType = mutableListOf()
        favoriteBed = mutableListOf()
        favoriteImage = mutableListOf()

        //fetech data from the database
        listingReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (listingSnapshot in snapshot.children) {
                    //GET THE favListings  form the children node
                    val favListings = listingSnapshot.getValue(FavListings::class.java)

                    //add favorite items to the list
                    favListings?.listingName?.let { favoriteItems.add(it) }
                    favListings?.listingPrice?.let { favoriteItemPrice.add(it) }
                    favListings?.listingRating?.let { favoriteRating.add(it) }
                    favListings?.listingLocation?.let { favoriteLocation.add(it) }
                    favListings?.listingHseType?.let { favoriteHseType.add(it) }
                    favListings?.listingBedsize?.let { favoriteBed.add(it) }
                    favListings?.listingImage?.let { favoriteImage.add(it) }

                }
                setAdapter()
            }

            private fun setAdapter() {
                favoriteAdapter = FavoriteAdapter(
                    requireContext(),
                    favoriteItems,
                    favoriteItemPrice,
                    favoriteRating,
                    favoriteLocation,
                    favoriteHseType,
                    favoriteBed,
                    favoriteImage,
//                    favoriteQuantity,

                )
                binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.favoriteRecyclerView.adapter = favoriteAdapter
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetched", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
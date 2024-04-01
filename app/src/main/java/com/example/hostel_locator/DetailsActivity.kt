package com.example.hostel_locator

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hostel_locator.databinding.ActivityDetailsBinding
import com.example.hostel_locator.model.FavListings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var listingName: String? = null
    private var listingPrice: String? = null
    private var listingRating: String? = null
    private var listingLocation: String? = null
    private var listingHseType: String? = null
    private var listingBedsize: String? = null
    private var listingImage: String? = null
    private var listingDescription: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Get intent data
        listingName = intent.getStringExtra("ListingPropertyName")
        listingPrice = intent.getStringExtra("ListingPropertyPrice")
        listingRating = intent.getStringExtra("ListingPropertyRating")
        listingLocation = intent.getStringExtra("ListingPropertyLocation")
        listingHseType = intent.getStringExtra("ListingPropertyHseType")
        listingBedsize = intent.getStringExtra("ListingPropertyBedsize")
        listingImage = intent.getStringExtra("ListingPropertyImage")
//        listingDescription = intent.getStringExtra("ListingDescription")

        // Set data to views
        with(binding) {
            detailsHouseName.text = listingName
            detailsDescription.text = listingDescription
            Glide.with(this@DetailsActivity).load(Uri.parse(listingImage)).into(detailsHseImage)
        }

        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.addListingButton.setOnClickListener {
            addListingToFav()
        }
    }

    private fun addListingToFav() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        // Create a FavListings object
        val favListing = FavListings(
            listingName ?: "ListingPropertyName",
            listingPrice ?: "ListingPropertyPrice",
            listingRating ?: "ListingPropertyRating",
            listingLocation ?: "ListingPropertyLocation",
            listingHseType ?: "ListingPropertyHseType",
            listingBedsize ?: "ListingPropertyBedsize",
            listingImage ?: "ListingPropertyImage"
        )

        // Save data to Firebase under user's favorite listings
        database.child("user").child(userId).child("FavoriteItems").push()
            .setValue(favListing)
            .addOnSuccessListener {
                Toast.makeText(this, "Listing Added to Favorites Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Add Listing to Favorites", Toast.LENGTH_SHORT).show()
            }
    }
}

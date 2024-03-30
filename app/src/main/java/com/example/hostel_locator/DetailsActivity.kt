package com.example.hostel_locator

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.hostel_locator.databinding.ActivityDetailsBinding
import com.example.hostel_locator.model.FavListings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit  var binding: ActivityDetailsBinding
    private var listingName:String ? = null
    private var listingPrice:String ? = null
    private var listingRating:String ? = null
    private var listingLocation:String ? = null
    private var listingHseType:String ? = null
    private var listingBedsize:String ? = null
    private var listingImage:String ? = null
    private var listingDescription: String? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listingName = intent.getStringExtra("ListingPropertyName")
        listingPrice = intent.getStringExtra("ListingPropertyPrice")
        listingRating = intent.getStringExtra("ListingPropertyRating")
        listingLocation = intent.getStringExtra("ListingPropertyLocation")
        listingHseType = intent.getStringExtra("ListingPropertyHseType")
        listingBedsize = intent.getStringExtra("ListingPropertyBedsize")
        listingImage = intent.getStringExtra("ListingPropertyImage")
        listingDescription = intent.getStringExtra("ListingDescription")

        with(binding){
            detailsHouseName.text = listingName
            detailsDescription.text = listingDescription
            Glide.with(this@DetailsActivity).load(Uri.parse(listingImage)).into(detailsHseImage)
        }
//        val apartName = intent.getStringExtra("ListingPropertyName")
//        val apartPrice = intent.getStringExtra("ListingPropertyPrice")
//        val apartRating = intent.getStringExtra("ListingPropertyRating")
//        val apartLocation = intent.getStringExtra("ListingPropertyLocation")
//        val apartHsetype = intent.getStringExtra("ListingPropertyHseType")
//        val apartBed = intent.getStringExtra("ListingPropertyBedsize")
//        val apartImage = intent.getIntExtra("ListingPropertyImage",0)

//        binding.detailsHouseName.text = apartName
//        binding.detailsHseImage.setImageResource(apartImage)

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.addListingButton.setOnClickListener {
            addListingToFav()
        }
    }

    private fun addListingToFav() {
         val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""
        //create a favListing object
        val favListing = FavListings(listingName.toString(),listingPrice.toString(),listingRating.toString(),listingLocation.toString(),listingHseType.toString(),listingBedsize.toString(),listingImage.toString())

        //save data to fav listing firebase data class
        database.child("user").child(userId).child("ListingProperties").setValue("listingProperty")

    }
}
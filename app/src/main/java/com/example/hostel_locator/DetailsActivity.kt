package com.example.hostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostel_locator.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit  var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apartName = intent.getStringExtra("ItemsName")
        val apartPrice = intent.getStringExtra("Price")
        val apartRating = intent.getStringExtra("Rating")
        val apartLocation = intent.getStringExtra("Location")
        val apartHsetype = intent.getStringExtra("Housetype")
        val apartBed = intent.getStringExtra("Bed")
        val apartImage = intent.getIntExtra("Images",0)

        binding.detailsHouseName.text = apartName
        binding.detailsHseImage.setImageResource(apartImage)
    }
}
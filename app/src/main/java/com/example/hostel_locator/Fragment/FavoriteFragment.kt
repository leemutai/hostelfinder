package com.example.hostel_locator.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostel_locator.BookingActivity
import com.example.hostel_locator.CongratsBottomSheet
import com.example.hostel_locator.R
import com.example.hostel_locator.adapter.FavoriteAdapter
import com.example.hostel_locator.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val favoriteItems = listOf("Dancy", "Qwety", "Kona", "Zipy")
        val favoriteItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
        val favoriteRating = listOf("4.9", "3.5", "3.3", "2.0")
        val favoriteLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
        val favoriteHseType = listOf("Apartment", "Studio", "2bd", "3bd")
        val favoriteBed = listOf("3.0", "1.0", "2.0", "3.0")
        val favoriteImage = listOf(
            R.drawable.hostel,
            R.drawable.hostel2,
            R.drawable.hostel,
            R.drawable.hostel2
        )
        val adapter = FavoriteAdapter(
            ArrayList(favoriteItems),
            ArrayList(favoriteItemPrice),
            ArrayList(favoriteRating),
            ArrayList(favoriteLocation),
            ArrayList(favoriteHseType),
            ArrayList(favoriteBed),
            ArrayList(favoriteImage),
        )
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter = adapter

        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(),BookingActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
}
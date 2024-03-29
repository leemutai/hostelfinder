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
//import com.example.hostel_locator.adapter.Row1Adapter
import com.example.hostel_locator.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.viewAllListings.setOnClickListener {
            val bottomSheetDialog = ListingsBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
        return  binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.hostel, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.hostel2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.hostel, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)
        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                 val itemPosition = imageList[position]
                val itemMessage="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }
        })
        val apartName = listOf("Wiku","Vinci","Beyas","Lato")
        val price = listOf("Ksh6000","Ksh5000","Ksh8000","Ksh10000")
        val rating = listOf("4.9","4.0","3.0","4.5")
        val location = listOf("Rongai","Pangani","Gataka","Rosters")
        val hsetype = listOf("Apartment","Bedsitter","Studio","OneBedroom")
        val bed = listOf("3.0","1.0","1.0","1.0")
        val apartImages = listOf(R.drawable.hostel,R.drawable.hostel2,R.drawable.hostel,R.drawable.hostel2)


        val adapter = Row1Adapter(apartName,price,rating,location,hsetype,bed,apartImages,requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }
}
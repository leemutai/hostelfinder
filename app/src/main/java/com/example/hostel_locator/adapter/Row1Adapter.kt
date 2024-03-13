package com.example.hostel_locator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostel_locator.databinding.Row1ItemBinding

class Row1Adapter(
    private val items: List<String>,
    private val price: List<String>,
    private val rating: List<String>,
    private val location: List<String>,
    private val hsetype: List<String>,
    private val bed: List<String>,
    private val image: List<Int>,
): RecyclerView.Adapter<Row1Adapter.Row1ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Row1ViewHolder {
        return Row1ViewHolder(Row1ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }



    override fun onBindViewHolder(holder: Row1ViewHolder, position: Int) {
        val item = items[position]
        val images = image[position]
        val price = price[position]
        val rating = rating[position]
        val location = location[position]
        val hsetype = hsetype[position]
        val bed = bed[position]

        holder.bind(item,price,rating,location,hsetype,bed,images)
    }
    override fun getItemCount(): Int {
         return items.size
    }
    class Row1ViewHolder(private val binding: Row1ItemBinding) :RecyclerView.ViewHolder(binding.root){
        private val imagesView = binding.imageRectangleTwelve
        fun bind(
            item: String,
            price: String,
            rating: String,
            location:String,
            hsetype: String,
            bed:String,
            images: Int
        ) {
             binding.apartNamePopular.text = item
            binding.txtPrice.text = price
            binding.txtRating.text = rating.toString()
            binding.txtLocation.text = location
            binding.textHseType.text = hsetype
            binding.txtBedSize.text = bed

            Glide.with(imagesView.context)
                .load(images)
                .into(imagesView)
//            imagesView.setImageResource(images)
        }

    }
}
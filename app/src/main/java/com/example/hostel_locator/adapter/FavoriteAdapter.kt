package com.example.hostel_locator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel_locator.databinding.FavoriteItemBinding

class FavoriteAdapter(
    private val favoriteItems: MutableList<String>,
//    private val favoriteApartName: MutableList<String>,
    private val favoriteItemPrice: MutableList<String>,
    private val favoriteRating: MutableList<String>,
    private val favoriteLocation: MutableList<String>,
    private val favoriteHseType: MutableList<String>,
    private val favoriteBed: ArrayList<String>,
    private var favoriteImage: MutableList<Int>,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val itemQuantities = IntArray(favoriteItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = favoriteItems.size

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    deleteItem(position)
                }
            }
        }

        private fun deleteItem(position: Int) {
            favoriteItems.removeAt(position)
            favoriteItemPrice.removeAt(position)
            favoriteRating.removeAt(position)
            favoriteLocation.removeAt(position)
            favoriteHseType.removeAt(position)
            favoriteBed.removeAt(position)
            favoriteImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoriteItems.size)
        }

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                apartNameFav.text = favoriteItems[position]
                favPrice.text = favoriteItemPrice[position]
                txtRatingFav.text = favoriteRating[position]
                txtLocationFav.text = favoriteLocation[position]
                textHseTypeFav.text = favoriteHseType[position]
                favBedSize.text = favoriteBed[position]
                imageRectangleFav.setImageResource(favoriteImage[position])
            }
        }
    }

}

package com.example.hostel_locator.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostel_locator.databinding.FavoriteItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.invoke.TypeDescriptor

class FavoriteAdapter(
    private val context: Context,
    private val favoriteItems: MutableList<String>,
//    private val favoriteApartName: MutableList<String>,
    private val favoriteItemPrice: MutableList<String>,
    private val favoriteRating: MutableList<String>,
    private val favoriteLocation: MutableList<String>,
    private val favoriteHseType: MutableList<String>,
    private val favoriteBed: MutableList<String>,
    private var favoriteImage: MutableList<String>,
//    private var favoriteDescription: MutableList<String>,
//    private val favoriteQuantity: MutableList<Int>,
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    //initialize instance firebase
    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        val favoriteItemNumber = favoriteItems.size

//        itemQuantities = intArray(favoriteItemNumber){1}
        favoriteItemsReference = database.reference.child("user").child("FavoriteItems")
    }

    companion object {
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var favoriteItemsReference: DatabaseReference
    }

    private var itemQuantities = IntArray(favoriteItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = favoriteItems.size

    // get updated quantity
    fun getUpdatedListingsQuanties(): MutableList<Int> {
        val listingQuantity = mutableListOf<Int>()
//        listingQuantity.addAll(favoriteQuantity)
        return listingQuantity
    }

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
            val positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null) {
                favoriteItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    favoriteItems.removeAt(position)
                    favoriteItemPrice.removeAt(position)
                    favoriteRating.removeAt(position)
                    favoriteLocation.removeAt(position)
                    favoriteHseType.removeAt(position)
                    favoriteBed.removeAt(position)
                    favoriteImage.removeAt(position)

                    Toast.makeText(context,"Listing Deleted",Toast.LENGTH_SHORT).show()
                    //update listing quantities
                    itemQuantities = itemQuantities.filterIndexed{index, i -> index!= position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,favoriteItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String?) -> Unit) {
            favoriteItemsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String? = null
                    //loop snapshot children
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionRetrieve) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
//            favoriteItems.removeAt(position)
//            favoriteItemPrice.removeAt(position)
//            favoriteRating.removeAt(position)
//            favoriteLocation.removeAt(position)
//            favoriteHseType.removeAt(position)
//            favoriteBed.removeAt(position)
//            favoriteImage.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, favoriteItems.size)
//        }

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                apartNameFav.text = favoriteItems[position]
                favPrice.text = favoriteItemPrice[position]
                txtRatingFav.text = favoriteRating[position]
                txtLocationFav.text = favoriteLocation[position]
                textHseTypeFav.text = favoriteHseType[position]
                favBedSize.text = favoriteBed[position]
//                imageRectangleFav.setImageResource(favoriteImage[position])
                //load image using glide
                val uriString = favoriteImage[position]
                var uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(imageRectangleFav)
            }
        }
    }

}

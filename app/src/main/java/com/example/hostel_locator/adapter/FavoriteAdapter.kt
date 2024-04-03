import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostel_locator.databinding.FavoriteItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoriteAdapter(
    private val context: Context,
    private val favoriteItems: MutableList<String>,
    private val favoriteItemPrice: MutableList<String>,
    private val favoriteRating: MutableList<String>,
    private val favoriteLocation: MutableList<String>,
    private val favoriteHseType: MutableList<String>,
    private val favoriteBed: MutableList<String>,
    private var favoriteImage: MutableList<String>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var favoriteItemsReference: DatabaseReference

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        favoriteItemsReference = database.reference.child("user").child(userId).child("FavoriteItems")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            favoriteItemsReference.child(favoriteItems[position]).removeValue()
                .addOnSuccessListener {
                    favoriteItems.removeAt(position)
                    favoriteItemPrice.removeAt(position)
                    favoriteRating.removeAt(position)
                    favoriteLocation.removeAt(position)
                    favoriteHseType.removeAt(position)
                    favoriteBed.removeAt(position)
                    favoriteImage.removeAt(position)

                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, favoriteItems.size)
                    Toast.makeText(context, "Listing Deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete listing", Toast.LENGTH_SHORT).show()
                }
        }

        fun bind(position: Int) {
            binding.apply {
                apartNameFav.text = favoriteItems[position]
                favPrice.text = favoriteItemPrice[position]
                txtRatingFav.text = favoriteRating[position]
                txtLocationFav.text = favoriteLocation[position]
                textHseTypeFav.text = favoriteHseType[position]
                favBedSize.text = favoriteBed[position]
                val uriString = favoriteImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(imageRectangleFav)
            }
        }
    }
}

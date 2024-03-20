import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostel_locator.DetailsActivity
import com.example.hostel_locator.databinding.Row1ItemBinding

class Row1Adapter(
    private val itemsName: List<String>,
    private val price: List<String>,
    private val rating: List<String>,
    private val location: List<String>,
    private val hsetype: List<String>,
    private val bed: List<String>,
    private val image: List<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<Row1Adapter.Row1ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Row1ViewHolder {
        return Row1ViewHolder(
            Row1ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Row1ViewHolder, position: Int) {
        val item = itemsName[position]
        val images = image[position]
        val price = price[position]
        val rating = rating[position]
        val location = location[position]
        val hsetype = hsetype[position]
        val bed = bed[position]

        holder.bind(item, price, rating, location, hsetype, bed, images)

        holder.itemView.setOnClickListener {
            //setomclick to open details activity
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("ListingsItemsName", item)
            intent.putExtra("ListingsPrice",price)
            intent.putExtra("ListingsRating",rating)
            intent.putExtra("ListingsLocation",  location)
            intent.putExtra("ListingsHousetype", hsetype)
            intent.putExtra("ListingsBed",  bed)
            intent.putExtra("ListingsImages", images)
            // Start the DetailsActivity when an item is clicked
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemsName.size
    }

    inner class Row1ViewHolder(private val binding: Row1ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imagesView = binding.imageRectangleTwelve

        fun bind(
            itemsName: String,
            price: String,
            rating: String,
            location: String,
            hsetype: String,
            bed: String,
            images: Int
        ) {
            binding.apartNamePopular.text = itemsName
            binding.txtPrice.text = price
            binding.txtRating.text = rating
            binding.txtLocation.text = location
            binding.textHseType.text = hsetype
            binding.txtBedSize.text = bed

            Glide.with(imagesView.context)
                .load(images)
                .into(imagesView)

            // Creating intent to pass data to DetailsActivity

        }
    }
}


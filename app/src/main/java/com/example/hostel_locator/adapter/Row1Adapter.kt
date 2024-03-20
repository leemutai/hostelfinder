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
private val itemClickListener: OnClickListener ?= null
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
    }

    override fun getItemCount(): Int {
        return itemsName.size
    }

    inner class Row1ViewHolder(private val binding: Row1ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(position)
                }
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("ItemsName", itemsName.get(position))
                intent.putExtra("Price", price.get(position))
                intent.putExtra("Rating", rating.get(position))
                intent.putExtra("Location", location.get(position))
                intent.putExtra("Housetype", hsetype.get(position))
                intent.putExtra("Bed", bed.get(position))
                intent.putExtra("Images", image.get(position))
                // Start the DetailsActivity when an item is clicked
                requireContext.startActivity(intent)
            }
        }
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

private fun OnClickListener?.onItemClick(position: Int) {


}

package com.example.hostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostel_locator.databinding.ActivityBookingBinding
import com.google.android.play.integrity.internal.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phone:String
    private lateinit var totalAmount:String
    private lateinit var listingPropertyName : ArrayList<String>
    private lateinit var listingPropertyPrice : ArrayList<String>
    private lateinit var listingPropertyRating : ArrayList<String>
    private lateinit var listingPropertyLocation : ArrayList<String>
    private lateinit var listingPropertyHseType : ArrayList<String>
    private lateinit var listingPropertyBedsize : ArrayList<String>
    private lateinit var listingPropertyImage : ArrayList<String>
//    private lateinit var listingQuantities:ArrayList<Int>
    private lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializ firebase and user details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()

        //set user data
        setUserData()
        //get user details from firebase

        val intent = intent
        listingPropertyName = intent.getStringArrayListExtra("ListingPropertyName") as ArrayList<String>
        listingPropertyPrice = intent.getStringArrayListExtra("ListingPropertyPrice") as ArrayList<String>
        listingPropertyRating = intent.getStringArrayListExtra("ListingPropertyRating") as ArrayList<String>
        listingPropertyLocation = intent.getStringArrayListExtra("ListingPropertyLocation") as ArrayList<String>
        listingPropertyHseType = intent.getStringArrayListExtra("ListingPropertyHseType") as ArrayList<String>
        listingPropertyBedsize = intent.getStringArrayListExtra("ListingPropertyBedsize") as ArrayList<String>
        listingPropertyImage = intent.getStringArrayListExtra("ListingPropertyImage") as ArrayList<String>
//        listingQuantities = intent.getIntegerArrayListExtra("ListingPropertyQuantities") as ArrayList<Int>

//        totalAmount = listingPropertyPrice.toString()
        totalAmount = formatPriceWithK(calculateTotalAmount())
//        binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)

        binding.bookNow.setOnClickListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")

        }
    }
    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until listingPropertyPrice.size) {
            val price = listingPropertyPrice[i]
            val priceWithoutCurrency = price.replace("[^\\d.]".toRegex(), "").toIntOrNull() ?: 0
            totalAmount += priceWithoutCurrency
        }
        return totalAmount
    }

    private fun formatPriceWithK(amount: Int): String {
        return "Ksh $amount"
    }





//    private fun calculateTotalAmount(): Int {
//        var totalAmount = 0
//        for (i in 0 until listingPropertyPrice.size){
//            var price = listingPropertyPrice[i]
//            val lastChar = price.last()
//            val priceIntValue = if (lastChar == 'K'){
//                price.dropLast(1).toInt()
//            }else{
//                price.toInt()
//            }
//        }
////        var quantity = listingQuantities
//        totalAmount = listingPropertyPrice.toString().length
//        return totalAmount
//    }


    private fun setUserData() {
        val user = auth.currentUser
        if (user!=null){
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         val names = snapshot.child("name").getValue(String::class.java)?:""
                         val addresses = snapshot.child("address").getValue(String::class.java)?:""
                         val phones = snapshot.child("phone").getValue(String::class.java)?:""

                         binding.apply {
                             name.setText(names)
                             address.setText(addresses)
                             phone.setText(phones)
                         }

                     }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

    }
}
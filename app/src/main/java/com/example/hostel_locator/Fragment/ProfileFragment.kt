package com.example.hostel_locator.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import com.example.hostel_locator.R
import com.example.hostel_locator.databinding.FragmentProfileBinding
import com.example.hostel_locator.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)


        saveUserData()
        binding.apply {
            name.isEnabled=false
            email.isEnabled = false
            address.isEnabled = false
            phone.isEnabled = false

            binding.editButton.setOnClickListener {
                name.isEnabled = !name.isEnabled
                email.isEnabled = !email.isEnabled
                address.isEnabled = !address.isEnabled
                phone.isEnabled = !phone.isEnabled
            }
        }

        binding.saveInfoButton.setOnClickListener {
            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val email = binding.email.toString()
            val phone = binding.phone.text.toString()

            updateUserData(name,address,email,phone)
        }
        return binding.root
    }

    private fun updateUserData(name: String, address: String, email: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId !=null){
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Updated Succesfully", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile Updated Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveUserData() {
         val userId = auth.currentUser?.uid
        if (userId !=null){
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                     if (snapshot.exists()){
                         val userProfile = snapshot.getValue(UserModel::class.java)
                         if (userProfile !=null){
                             binding.name.setText(userProfile.name)
                             binding.address.setText(userProfile.address)
                             binding.email.setText(userProfile.email)
                             binding.phone.setText(userProfile.phone)
                         }
                     }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}
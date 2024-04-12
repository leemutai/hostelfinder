package com.example.hostel_locator.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hostel_locator.MessagesAdapter
import com.example.hostel_locator.R
import com.example.hostel_locator.databinding.FragmentChatBinding
import com.example.hostel_locator.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ChatFragment : Fragment() {
    private var binding: FragmentChatBinding? = null
    private var adapter: MessagesAdapter? = null
    private var messages: ArrayList<Message>? = null
    private var senderRoom: String? = null
    private var receiverRoom: String? = null
    private var database: FirebaseDatabase? = null
    private var storage: FirebaseStorage? = null
    private var dialog: ProgressDialog? = null
    private var senderUid: String? = null
    private var receiverUid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        setUpSupportActionBar(binding!!.toolbar)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        dialog = ProgressDialog(requireContext())
        dialog!!.setMessage("uploading image...")
        dialog!!.setCancelable(false)
        messages = ArrayList()
        val name = arguments?.getString("name")
        val profile = arguments?.getString("image")
        binding!!.name.text = name
        Glide.with(this).load(profile)
            .placeholder(R.drawable.placeholder)
            .into(binding!!.profile01)
        binding!!.imageView.setOnClickListener {
            // Handle click event appropriately
        }
        receiverUid = arguments?.getString("uid")
        senderUid = FirebaseAuth.getInstance().uid

        if (receiverUid != null) {
            database!!.reference.child("Presence").child(receiverUid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val status = snapshot.getValue(String::class.java)
                            if (status == "Offline")
                                binding!!.status.visibility = View.GONE
                            else
                                binding!!.status.text = status
                        } else {
                            binding!!.status.visibility = View.GONE
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle onCancelled
                    }
                })
        } else {
            // Handle null receiverUid
        }

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        adapter = MessagesAdapter(requireContext(), messages, senderRoom!!, receiverRoom!!)

        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = adapter

        database!!.reference.child("chats")
            .child(senderRoom!!)
            .child("message")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Handle onDataChange
                    messages!!.clear()
                    for (snapshot1 in snapshot.children){
                        val message:Message? = snapshot1.getValue(Message::class.java)
                        message!!.messageId = snapshot1.key
                        messages!!.add(message)
                    }
                    adapter!!.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })

        binding!!.send.setOnClickListener {
            val messageTxt:String = binding!!.messageBox.text.toString()
            val date = Date()
            val message = Message(messageTxt,senderUid,date.time)

            binding!!.messageBox.setText("")
            val randomKey = database!!.reference.push().key
            val lastMsgObj = HashMap<String,Any>()
            lastMsgObj["lastMsgObj"] = message.message!!
            lastMsgObj["lastMsgObj"] = date.time

            database!!.reference.child("chats").child(senderRoom!!)
                .updateChildren(lastMsgObj)
            database!!.reference.child("chats").child(receiverRoom!!)
                .updateChildren(lastMsgObj)
            database!!.reference.child("chats").child(senderRoom!!)
                .child("messages")
                .child(randomKey!!)
                .setValue(message).addOnSuccessListener {
                    database!!.reference.child("chats")
                        .child(receiverRoom!!)
                        .child("message")
                        .child(randomKey)
                        .setValue(message)
                        .addOnSuccessListener {  }
                }
        }

        binding!!.attachment.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
        }

        val handler = Handler()
        binding!!.messageBox.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                database!!.reference.child("Presence")
                    .child(senderUid!!)
                    .setValue("typing...")
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed(userStoppedTyping,1000)
            }
            var userStoppedTyping = Runnable {
                database!!.reference.child("Presence")
                    .child(senderUid!!)
                    .setValue("Online")
            }
        })

        requireActivity().actionBar?.setDisplayShowTitleEnabled(false)

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("Online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("Offline")
    }

    private fun setUpSupportActionBar(toolbar: Toolbar) {
        // Implement setting up action bar here
    }
}

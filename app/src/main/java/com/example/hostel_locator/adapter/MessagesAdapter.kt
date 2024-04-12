package com.example.hostel_locator

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hostel_locator.R
import com.example.hostel_locator.databinding.SendMsgBinding
import com.example.hostel_locator.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessagesAdapter(
    private val context: Context,
    private val messages: ArrayList<Message>?,
    private val senderRoom: String,
    private val receiverRoom: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_SENT = 1
        const val ITEM_RECEIVE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if (viewType == ITEM_SENT) {
            val view = inflater.inflate(R.layout.send_msg, parent, false)
            SentMsgHolder(view)
        } else {
            val view = inflater.inflate(R.layout.receive_msg, parent, false)
            ReceiveMsgHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages?.get(position)
        return if (FirebaseAuth.getInstance().uid == message?.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages?.get(position)

        if (holder is SentMsgHolder) {
            with(holder.binding) {
                // Bind data for sent messages
            }
        } else if (holder is ReceiveMsgHolder) {
            with(holder.binding) {
                // Bind data for received messages
            }
        }
    }

    override fun getItemCount(): Int = messages?.size ?: 0

    inner class SentMsgHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }

    inner class ReceiveMsgHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }
}

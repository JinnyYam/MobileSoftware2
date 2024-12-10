package com.example.mobilesoftware.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoftware.R

class FriendListAdapter(
    private val friendList: List<String>,
    private val onRemoveClick: (String) -> Unit
) : RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendName: TextView = itemView.findViewById(R.id.friend_name)
        val removeButton: ImageButton = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendName.text = friend
        holder.removeButton.setOnClickListener { onRemoveClick(friend) }
    }

    override fun getItemCount() = friendList.size
}
package com.example.mobilesoftware.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoftware.databinding.ItemCommentBinding
import com.example.mobilesoftware.model.main_data


class CommentAdapter(private val comments: List<main_data.Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(val binding: ItemCommentBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.binding.commentAuthor.text = comment.author
        holder.binding.commentContent.text = comment.content
    }

    override fun getItemCount(): Int = comments.size
}
//package com.example.mobilesoftware.adapter
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.mobilesoftware.R
//import com.example.mobilesoftware.databinding.ItemPostBinding
//import com.example.mobilesoftware.model.main_data
//
//class HorizontalPostAdapter(
//    private val posts: List<main_data.Post>,
//    private val onItemClick: (main_data.Post) -> Unit   // 클릭 리스너
//) : RecyclerView.Adapter<HorizontalPostAdapter.PostViewHolder>() {
//
//    class PostViewHolder(val binding: ItemPostBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return PostViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        val post = posts[position]
//        holder.binding.postTitle.text = post.title
//        holder.binding.postImage.setImageResource(R.drawable.whale)
//
//        // Click Event
//        holder.itemView.setOnClickListener{
//            val fragment = DetailPostFragment()
//            val args = Bundle().apply {
//                putParcelable("post", post)
//            }
//            fragment.arguments = args
//
//            (holder.itemView.context as? AppCompatActivity)?.supportFragmentManager
//                ?.beginTransaction()
//                ?.replace(R.id.main, fragment)
//                ?.addToBackStack(null)
//                ?.commit()
//        }
//    }
//
//    override fun getItemCount(): Int = posts.size
//}

package com.example.mobilesoftware.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilesoftware.DetailPostActivity
import com.example.mobilesoftware.R
import com.example.mobilesoftware.databinding.ItemPostBinding
import com.example.mobilesoftware.model.main_data

class HorizontalPostAdapter(
    private val posts: List<main_data.Post>,
    private val onItemClick: (main_data.Post) -> Unit   // 클릭 리스너
) : RecyclerView.Adapter<HorizontalPostAdapter.PostViewHolder>() {

    class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.postTitle.text = post.title

        // 이미지 로드 (Glide 또는 기본 리소스 사용)
        Glide.with(holder.itemView.context)
            .load(post.imageUrl) // post.imageUrl 사용
            .placeholder(R.drawable.whale)
            .into(holder.binding.postImage)

        // Click Event: Activity로 전환
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailPostActivity::class.java).apply {
                putExtra("post", post) // 데이터 전달
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = posts.size
}
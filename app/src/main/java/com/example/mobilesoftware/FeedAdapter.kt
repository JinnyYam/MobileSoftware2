package com.example.mobilesoftware;
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class FeedAdapter(private val context: Context, private val imageList: List<Int> // 이미지 리소스 ID 리스트
    ) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])

        // 부모 뷰의 LayoutParams 가져오기
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
    }

    override fun getItemCount(): Int = imageList.size

}


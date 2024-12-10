package com.example.mobilesoftware

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mobilesoftware.adapter.CommentAdapter
import com.example.mobilesoftware.databinding.ActivityDetailPostBinding
import com.example.mobilesoftware.model.main_data

class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPostBinding
    private val comments = mutableListOf<main_data.Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        // Intent를 통해 전달받은 데이터 처리
        val post: main_data.Post? = intent.getParcelableExtra("post")
        post?.let {
            binding.toolbar.title = it.title
            Glide.with(this)
                .load(it.imageUrl)
                .placeholder(R.drawable.pikmins)
                .into(binding.postImage)
            binding.postContent.text = "게시물의 내용을 여기에 표시합니다."
            binding.postOwner.text = "@noonsongi-love"
        }

        // 댓글 RecyclerView 설정
        val commentAdapter = CommentAdapter(comments)
        binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.commentRecyclerView.adapter = commentAdapter

        // 댓글 추가 버튼 동작
        binding.addCommentButton.setOnClickListener {
            val commentContent = binding.commentInput.text.toString()
            if (commentContent.isNotEmpty()) {
                val newComment = main_data.Comment(
                    author = "User",
                    content = commentContent
                )
                comments.add(newComment)
                commentAdapter.notifyItemInserted(comments.size - 1)
                binding.commentInput.text.clear()
            } else {
                Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        // 뒤로 가기 버튼 클릭 이벤트 처리
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
package com.example.mobilesoftware

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilesoftware.adapter.HorizontalPostAdapter
import com.example.mobilesoftware.databinding.FragmentHomeBinding
import com.example.mobilesoftware.model.main_data

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 더미 데이터
        val playlistData = createDummyPosts("Song")
        val eatData = createDummyPosts("Food")
        val lookData = createDummyPosts("Look")
        val placeData = createDummyPosts("Place")

        // RecyclerView 연결
        setupHorizontalRecyclerView(binding.playlistRecyclerView, playlistData)
        setupHorizontalRecyclerView(binding.eatRecyclerView, eatData)
        setupHorizontalRecyclerView(binding.lookRecyclerView, lookData)
        setupHorizontalRecyclerView(binding.placeRecyclerView, placeData)
    }
    
    private fun setupHorizontalRecyclerView(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        posts: List<main_data.Post>
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = HorizontalPostAdapter(posts) { post ->
            // 클릭 시 DetailPostActivity로 이동
            val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                putExtra("post", post) // post 데이터 전달
            }
            startActivity(intent)
        }
    }

    private fun createDummyPosts(category: String): List<main_data.Post> {
        return List(5) { index ->
            main_data.Post(
                title = "$category Title $index",
                imageUrl = "@drawable/whale"
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
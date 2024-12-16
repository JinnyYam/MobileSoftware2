package com.example.mobilesoftware.profile

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoftware.BottomNavigationManager
import com.example.mobilesoftware.DetailPostActivity
import com.example.mobilesoftware.MapsActivity
import com.example.mobilesoftware.R
import com.example.mobilesoftware.adapter.HorizontalPostAdapter
import com.example.mobilesoftware.data.User
import com.example.mobilesoftware.databinding.ActivityProfileBinding
import com.example.mobilesoftware.model.main_data
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding


    // 더미 팔로잉 및 팔로워 리스트
    private val followingList = mutableListOf("Alice", "Bob", "Charlie")
    private val followerList = mutableListOf("David", "Eve", "Frank")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용자 정보 로드
        loadUserName()

        // 네비게이션 뷰 초기 상태 설정
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val bottomNavigationManager = BottomNavigationManager(this)
        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)

        //초기 설정
        setupTabLayout()
        setupFollowClickListeners()
        updateFollowCounts()

        // 더미 데이터 생성
        val playlistData = createDummyPosts("Playlist")
        val eatData = createDummyPosts("Eat")
        val lookData = createDummyPosts("Look")
        val placeData = createDummyPosts("Place")

        // RecyclerView 연결
        setupHorizontalRecyclerView(binding.profilePlaylistRecyclerView, playlistData)
        setupHorizontalRecyclerView(binding.profileEatRecyclerView, eatData)
        setupHorizontalRecyclerView(binding.profileLookRecyclerView, lookData)
        setupHorizontalRecyclerView(binding.profilePlaceRecyclerView, placeData)

        // 프로필 수정 버튼 클릭 시
        binding.profileModifyBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserName() {
        val userId = User.userId
        val userName = User.userName
        binding.profileUsername.text = userName
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                when (tab?.text) {
                    "CONTENTS" -> {
                        Toast.makeText(this@ProfileActivity, "CONTENTS 선택", Toast.LENGTH_SHORT).show()
                    }
                    "MAP" -> {
                        // MAP 탭이 선택되었을 때 MapsActivity로 이동
                        val intent = Intent(this@ProfileActivity, MapsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                // 탭 선택이 해제되었을 때
            }

            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 때
            }
        })
    }

    private fun setupFollowClickListeners() {
        binding.profileFollow.findViewById<TextView>(R.id.following).setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java).apply {
                putExtra("title", "Following")
                putStringArrayListExtra("friendList", ArrayList(followingList))
            }
            startActivity(intent)
        }

        binding.profileFollow.findViewById<TextView>(R.id.follower).setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java).apply {
                putExtra("title", "Followers")
                putStringArrayListExtra("friendList", ArrayList(followerList))
            }
            startActivity(intent)
        }
    }

    private fun updateFollowCounts() {
        binding.profileFollow.findViewById<TextView>(R.id.following).text =
            "following ${followingList.size}"
        binding.profileFollow.findViewById<TextView>(R.id.follower).text =
            "follower ${followerList.size}"
    }

    private fun setupHorizontalRecyclerView(recyclerView: RecyclerView, posts: List<main_data.Post>) {
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = HorizontalPostAdapter(posts) { post ->
            navigationToPostDetailActivity(post)
        }
        recyclerView.adapter = adapter
    }

    

    private fun navigationToPostDetailActivity(post: main_data.Post) {
        val intent = Intent(this, DetailPostActivity::class.java).apply {
            putExtra("post", post) // post data 전달
        }
        startActivity(intent)
    }

    private fun createDummyPosts(category: String): List<main_data.Post> {
        return List(5) { index ->
            main_data.Post(
                title = "Post Title $index",
                imageUrl = "@drawable/whale"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        if (bottomNavigationView.selectedItemId != R.id.profile) {
            bottomNavigationView.selectedItemId = R.id.profile
        }
    }


}
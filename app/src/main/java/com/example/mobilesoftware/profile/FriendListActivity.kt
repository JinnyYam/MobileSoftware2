package com.example.mobilesoftware.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilesoftware.R
import com.example.mobilesoftware.adapter.FriendListAdapter
import com.example.mobilesoftware.databinding.ActivityFriendListBinding

class FriendListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendListBinding
    private lateinit var adapter: FriendListAdapter
    private var friendList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면 제목 설정
        val title = intent.getStringExtra("title") ?: "Friend List"
        binding.toolbar.title = title

        // 뒤로가기 버튼 활성화
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 더미 데이터 가져오기
        friendList = intent.getStringArrayListExtra("friendList")?.toMutableList() ?: mutableListOf()

        // RecyclerView 설정
        adapter = FriendListAdapter(friendList) { friend ->
            removeFriend(friend)
        }
        binding.friendListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendListRecyclerView.adapter = adapter

    }

    private fun removeFriend(friend: String) {
        // 친구 삭제
        friendList.remove(friend)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "$friend removed", Toast.LENGTH_SHORT).show()
    }
    // 뒤로가기 버튼 클릭 시 처리
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // 액티비티 종료
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
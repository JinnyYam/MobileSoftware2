package com.example.mobilesoftware

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobilesoftware.databinding.ActivityPostViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PostViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 표시 끄기
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 업버튼 활성화

        // 네비게이션 뷰 초기 상태 설정
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        val bottomNavigationManager = BottomNavigationManager(
            this // 액티비티 전달
        )
        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)
    }

    // toolbar의 뒤로가기 버튼 클릭 시 이전 화면으로 이동
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
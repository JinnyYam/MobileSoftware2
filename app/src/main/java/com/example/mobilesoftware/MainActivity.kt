package com.example.mobilesoftware

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 초기 탭 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }

        // 네비게이션 뷰 초기 상태 설정
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val bottomNavigationManager = BottomNavigationManager(this)
        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        if (bottomNavigationView.selectedItemId != R.id.home) {
            bottomNavigationView.selectedItemId = R.id.home
        }
    }
}

package com.example.mobilesoftware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationManager(
    private val activity: AppCompatActivity
) {
    fun setupWithBottomNavigationView(navView: BottomNavigationView) {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navigateToActivity(MainActivity::class.java)
                    true
                }
                R.id.search -> {
                    navigateToActivity(SearchActivity::class.java)
                    true
                }
                R.id.posting -> {
                    navigateToActivity(PostActivity::class.java)
                    true
                }
                R.id.profile -> {
                    navigateToActivity(ProfileActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

    // 공통적으로 사용할 Activity 전환 함수
    private fun navigateToActivity(targetActivity: Class<out AppCompatActivity>) {
        if (activity::class.java != targetActivity) { // 현재 Activity와 다른 경우에만 실행
            val intent = Intent(activity, targetActivity)
            activity.startActivity(intent)
        }
    }
}

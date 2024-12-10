package com.example.mobilesoftware.account;

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.databinding.ActivityLoadingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GlobalScope.launch {
            delay(3000)
            startActivity(Intent(this@LoadingActivity, LoginActivity::class.java))
        }
    }
}
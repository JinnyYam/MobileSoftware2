package com.example.mobilesoftware.account;

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.databinding.ActivityCheckPwBinding

class CheckPwActivity : AppCompatActivity() {
    val binding: ActivityCheckPwBinding by lazy {
        ActivityCheckPwBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var searchEmail = ""
        var searchPw = "0000"
        // 비밀번호 찾는 로직
        binding.searchPwButton.setOnClickListener {
            searchEmail= binding.searchIdTextview.text.toString()

            binding.myPw.text = "비밀번호는 \" $searchPw \"입니다. "
            binding.myPw.visibility = View.VISIBLE
        }

        binding.backButton.setOnClickListener {
            // 결과를 인텐트에 담아 반환
            intent.putExtra("ID",searchEmail)
            intent.putExtra("PW", searchPw)

            setResult(Activity.RESULT_OK, intent)  // 결과 전달
            finish()  // TodoActivity 종료
        }



    }
}
package com.example.mobilesoftware.account;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobilesoftware.databinding.ActivityJoinBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {
    val binding: ActivityJoinBinding by lazy {
        ActivityJoinBinding.inflate(layoutInflater)
    }
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pw = binding.pwEditText.text.toString()

            if (id.isNotEmpty() && pw.isNotEmpty()) {
                binding.textView2.visibility=View.VISIBLE

                // 코루틴을 사용해 2초 지연 후 데이터 반환
                lifecycleScope.launch {
                    delay(2000) // 2초 지연

                    // 결과 데이터를 인텐트에 담아 반환
                    val resultIntent = Intent().apply {
                        putExtra("ID", id)
                        putExtra("PW", pw)
                    }

                    setResult(Activity.RESULT_OK, resultIntent) // 결과 설정
                    finish() // JoinActivity 종료
                }


            } else {
                // 에러 메시지 표시
                Toast.makeText(this,"이메일과 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
package com.example.mobilesoftware.account;

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.MainActivity

import com.example.mobilesoftware.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pw = binding.pwEditText.text.toString()

            if (id.isNotEmpty() && pw.isNotEmpty()) {
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.putExtra("ID", id)
                mainIntent.putExtra("PW", pw)
                startActivity(mainIntent)
            } else {
                // 에러 메시지 표시
                Toast.makeText(this,"아이디와 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show()
            }
        }

        // ActivityResultLauncher를 등록하여 결과를 처리하는 코드
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // CheckPwActivity에서 받은 데이터를 처리
                val id = result.data?.getStringExtra("ID")
                val pw = result.data?.getStringExtra("PW")

                // 받은 데이터를 로그인 화면에 표시
                binding.idEditText.setText(id ?: "")
                binding.pwEditText.setText(pw ?: "")
            }
        }

        binding.searchPwTextview.setOnClickListener{
            val checkPwIntent : Intent = Intent(this, CheckPwActivity::class.java)
            launcher.launch(checkPwIntent)  // ActivityResultLauncher를 사용하여 새로운 액티비티 실행
        }

        binding.joinTextView.setOnClickListener {
            val joinIntent = Intent(this, JoinActivity::class.java)
            launcher.launch(joinIntent) // JoinActivity 시작
        }


    }
    // 화면이 다시 활성화되었을 때 호출되는 메서드
    override fun onStart() {
        super.onStart()

        // 텍스트 입력 필드를 비웁니다
        binding.idEditText.text.clear()
        binding.pwEditText.text.clear()
    }


}
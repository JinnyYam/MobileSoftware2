package com.example.mobilesoftware.account;

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.MainActivity
import com.example.mobilesoftware.data.User
import com.example.mobilesoftware.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance() // Firestore 인스턴스 생성
    }
    private val mAuth = FirebaseAuth.getInstance()

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그인 버튼 클릭
        binding.loginButton.setOnClickListener {
            val id = binding.idEditText.text.toString().trim()
            val pw = binding.pwEditText.text.toString().trim()

            if (id.isNotEmpty() && pw.isNotEmpty()) {
                login(id,pw)

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

        // 비밀번호 찾기 텍스트 클릭 이벤트
        binding.searchPwTextview.setOnClickListener{
            val checkPwIntent : Intent = Intent(this, CheckPwActivity::class.java)
            launcher.launch(checkPwIntent)  // ActivityResultLauncher를 사용하여 새로운 액티비티 실행
        }

        // 회원가입 텍스트 클릭 이벤트
        binding.joinTextView.setOnClickListener {
            val joinIntent = Intent(this, JoinActivity::class.java)
            launcher.launch(joinIntent) // JoinActivity 시작
        }
    }
    private fun login(id: String, pw: String) {
        db.collection("users")
            .whereEqualTo("uid", id) // 이메일 조건
            .whereEqualTo("password", pw) // 비밀번호 조건
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // 로그인 성공
                    val user = documents.first()
                    val userName = user.getString("name") ?: "사용자"

                    Toast.makeText(this, "환영합니다, $userName!", Toast.LENGTH_SHORT).show()

                    //로그인 정보 저장
                    User.userId = id
                    User.userName = userName

                    // 메인 화면으로 이동
                    val intent = Intent(this, MainActivity::class.java) // MainActivity로 교체 필요
                    startActivity(intent)
                    finish()
                } else {
                    // 로그인 실패
                    Toast.makeText(this, "아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "로그인 중 오류 발생: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
    // 앱이 다시 활성화될 때 로그인 상태 확인
    override fun onStart() {
        super.onStart()
        binding.idEditText.text.clear()
        binding.pwEditText.text.clear()

    }
}
package com.example.mobilesoftware.account

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.databinding.ActivityCheckPwBinding
import com.google.firebase.firestore.FirebaseFirestore

class CheckPwActivity : AppCompatActivity() {
    lateinit var id:String
    lateinit var pw:String

    private val binding: ActivityCheckPwBinding by lazy {
        ActivityCheckPwBinding.inflate(layoutInflater)
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 비밀번호 찾기 버튼 클릭
        binding.searchPwButton.setOnClickListener {
            id = binding.searchIdTextview.text.toString().trim()

            if (id.isNotEmpty()) {
                findPassword(id)
            } else {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 뒤로가기 버튼 클릭
        binding.backButton.setOnClickListener {
            // 결과를 인텐트에 담아 반환
            intent.putExtra("ID",id)
            intent.putExtra("PW",pw)

            setResult(Activity.RESULT_OK, intent)  // 결과 전달
            finish()  // TodoActivity 종료
        }
    }

    private fun findPassword(email: String) {
        // Firestore에서 이메일로 사용자 검색
        db.collection("users")
            .whereEqualTo("id", email) // id 필드와 입력된 이메일 비교
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // 사용자 데이터를 가져옴
                    val user = documents.first()
                    pw = user.getString("password") ?: "비밀번호 없음"

                    // 비밀번호 표시
                    binding.myPw.text = "비밀번호는 \"$pw\"입니다."
                    binding.myPw.visibility = View.VISIBLE
                } else {
                    // 사용자 데이터 없음
                    Toast.makeText(this, "해당 이메일로 등록된 계정을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // 에러 처리
                Toast.makeText(this, "비밀번호를 찾는 중 오류가 발생했습니다: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

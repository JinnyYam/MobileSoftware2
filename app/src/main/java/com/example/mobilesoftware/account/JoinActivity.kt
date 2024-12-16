//package com.example.mobilesoftware.account;
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.mobilesoftware.databinding.ActivityJoinBinding
//import com.google.firebase.firestore.FirebaseFirestore
//
//class JoinActivity : AppCompatActivity() {
//    val binding: ActivityJoinBinding by lazy {
//        ActivityJoinBinding.inflate(layoutInflater)
//    }
//
//    // Firestore 인스턴스 초기화
//    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        binding.button.setOnClickListener {
//            val name = binding.nameEditText.text.toString() // 사용자 이름
//            val id = binding.idEditText.text.toString()
//            val pw = binding.pwEditText.text.toString()
//
//            if (name.isNotEmpty() && id.isNotEmpty() && pw.isNotEmpty()) {
//                binding.textView2.visibility=View.VISIBLE
//                addUserToFirestore(name, id, pw)
//            } else {
//                // 에러 메시지 표시
//                Toast.makeText(this,"모든 정보를 입력하세요", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun addUserToFirestore(name: String, id: String, pw: String) {
//        // Firestore에 저장할 사용자 데이터
//        val user = hashMapOf(
////            "uid" to Math.random().toInt().toString(),
//            "name" to name,
//            "id" to id,
//            "password" to pw
//        )
//
//        // Firestore 컬렉션에 데이터 추가
//        firestore.collection("users")
//            .add(user) // 문서 ID는 자동 생성
//            .addOnSuccessListener { documentReference ->
//                Toast.makeText(this, "회원가입 성공: ${name}", Toast.LENGTH_SHORT).show()
//
//                // 결과 데이터를 Intent에 담아 반환
//                val resultIntent = Intent().apply {
//                    putExtra("NAME", name)
//                    putExtra("ID", id)
//                    putExtra("PW", pw)
//                }
//                setResult(Activity.RESULT_OK, resultIntent) // 결과 설정
//                finish() // JoinActivity 종료
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(this, "회원가입 실패: ${exception.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//}

package com.example.mobilesoftware.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobilesoftware.databinding.ActivityJoinBinding
import com.google.firebase.firestore.FirebaseFirestore

class JoinActivity : AppCompatActivity() {
    private val binding: ActivityJoinBinding by lazy {
        ActivityJoinBinding.inflate(layoutInflater)
    }

    // Firestore 인스턴스 초기화
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val id = binding.idEditText.text.toString().trim() // 사용자 입력 ID
            val pw = binding.pwEditText.text.toString().trim() // 사용자 입력 비밀번호

            if (name.isNotEmpty() && id.isNotEmpty() && pw.isNotEmpty()) {
                binding.textView2.visibility = View.VISIBLE
                registerUser(name, id, pw)
            } else {
                // 입력 필드가 비어있을 경우 에러 메시지 표시
                Toast.makeText(this, "모든 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(name: String, id: String, password: String) {
        // Firestore에 저장할 사용자 데이터 정의
        val user = hashMapOf(
            "uid" to id, // 사용자가 입력한 ID를 고유 ID로 사용
            "name" to name,
            "password" to password, // 비밀번호 저장 (해싱이 권장됨)
            "profileMessage" to "안녕하세요! 새로운 사용자입니다."
        )

        // Firestore에 데이터 저장
        firestore.collection("users").document(id) // ID를 문서 ID로 설정
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "회원가입 성공: $name", Toast.LENGTH_SHORT).show()

                // 회원가입 결과 반환
                val resultIntent = Intent().apply {
                    putExtra("NAME", name)
                    putExtra("ID", id)
                    putExtra("PW", password)
                }
                setResult(Activity.RESULT_OK, resultIntent) // 결과 반환
                finish() // JoinActivity 종료
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "회원가입 실패: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

package com.example.mobilesoftware

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mobilesoftware.data.User
import com.example.mobilesoftware.databinding.ActivityPostBinding
import com.example.mobilesoftware.model.main_data
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.util.UUID

class PostActivity : AppCompatActivity() {

    private var selectedCategory: String? = null
    private val userId = User.userId
    private lateinit var binding: ActivityPostBinding
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        setUpManyThings()

    }

    private fun setUpManyThings() {
        setupToolbar()
        setupBottomNavigation()
        setupCategorySelection()
        setupAddImageButton()
        setupPostButton()
    }

    // 툴바 설정
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false) // 제목 비표시
            setDisplayHomeAsUpEnabled(true)  // 뒤로가기 버튼 활성화
        }
    }

    // 하단 네비게이션 초기화
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val bottomNavigationManager = BottomNavigationManager(this)
        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)
    }

    // 카테고리 선택 이벤트 초기화
    private fun setupCategorySelection() {
        binding.radioCategory.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding.categoryPlaylist.id -> selectedCategory = "playlist"
                binding.categoryEat.id -> selectedCategory = "eat"
                binding.categoryLook.id -> selectedCategory = "look"
                binding.categoryPlace.id -> selectedCategory = "place"
            }
        }
    }

    // 이미지 추가 버튼 설정
    private fun setupAddImageButton() {
        binding.addImageButton.setOnClickListener { openGallery() }
    }

//    // 포스트 버튼 클릭 설정
//    private fun setupPostButton() {
//        binding.postBtn.setOnClickListener {
//            val post = getPostData() // Post 데이터를 가져옴
//            post?.let { navigateToPostView(it) }
//        }
//    }
    // 포스트 버튼 클릭 이벤트
    private fun setupPostButton() {
        binding.postBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val contents = binding.content.text.toString()
            val category = selectedCategory

            val location = GeoPoint(0.0,0.0) // 임시로 저장 (위치 기능 없음)
            val photo = "" // 사진은 나중에 업로드 URL로 추가 예정

            // Firebase로 저장
            savePostToFirestore(title, contents, category, location, photo)
        }
    }

    // Firestore에 게시물 저장
    private fun savePostToFirestore(
        title: String,
        contents: String,
        category: String?,
        location: GeoPoint,
        photo: String
    ) {

        if (userId != null) {
            val postId = UUID.randomUUID().toString() // 게시물 고유 ID
            val postData = hashMapOf(
                "title" to title,
                "contents" to contents,
                "category" to category,
                "location" to location,
                "photo" to photo
            )

            // Firestore에 접근하여 데이터 저장
            firestore.collection("users")
                .document(userId)
                .collection("posts")
                .document(postId)
                .set(postData)
                .addOnSuccessListener {
                    Toast.makeText(this, "게시물이 등록되었습니다!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "등록 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }





//
//    // 선택된 카테고리의 배경색 토글
//    private fun toggleSelection(selectedTextView: TextView) {
//        // 이전에 선택된 TextView 배경 초기화
//        this.selectedTextView?.setBackgroundColor(Color.TRANSPARENT)
//
//        // 선택된 TextView 배경색 설정
//        if (this.selectedTextView == selectedTextView) {
//            this.selectedTextView = null // 다시 클릭 시 선택 해제
//        } else {
//            selectedTextView.setBackgroundColor(
//                ContextCompat.getColor(this, R.color.blue4)
//            )
//            this.selectedTextView = selectedTextView
//        }
//    }

    // 뒤로가기 버튼 동작 설정
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // 갤러리 열기
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        galleryLauncher.launch(intent)
    }

    // 갤러리에서 선택한 이미지 처리
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    binding.postImage.setImageURI(imageUri) // 이미지 표시
                } else {
                    Toast.makeText(this, "이미지를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    // 화면 복귀 시 하단 네비게이션 상태 초기화
    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        if (bottomNavigationView.selectedItemId != R.id.posting) {
            bottomNavigationView.selectedItemId = R.id.posting
        }
    }

    // PostViewActivity로 이동
    private fun navigateToPostView(post: main_data.Post) {
        val intent = Intent(this, PostViewActivity::class.java).apply {
            putExtra("POST_TITLE", post.title)
            putExtra("POST_IMAGE_URL", post.imageUrl)
        }
        startActivity(intent)
    }

    // Post 데이터를 가져오는 메서드 (예제)
    private fun getPostData(): main_data.Post? {
        return main_data.Post("Sample Title", "https://example.com/image.jpg")
    }
}

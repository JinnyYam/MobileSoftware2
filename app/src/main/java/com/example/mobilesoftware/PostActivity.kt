//package com.example.mobilesoftware
//
//import android.content.Intent
//import android.graphics.Color
//import android.net.Uri
//import android.os.Bundle
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.core.content.ContextCompat
//import com.example.mobilesoftware.databinding.ActivityPostBinding
//import com.example.mobilesoftware.model.main_data
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class PostActivity : AppCompatActivity() {
//
//    private var selectedTextView: TextView? = null
//    private lateinit var binding: ActivityPostBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // toolbar 설정
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 표시 끄기
//        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 업버튼 활성화
//
//        // 네비게이션 뷰 초기 상태 설정
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
//
//        val bottomNavigationManager = BottomNavigationManager(
//            this // 액티비티 전달
//        )
//        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)
//
//
//        // addImageButton 클릭 이벤트
//        binding.addImageButton.setOnClickListener {
//            openGallery()
//        }
//
//        // 카테고리 선택시 선택 표시(배경색 변경)
//        binding.categoryPlaylist.setOnClickListener { toggleSelection(binding.categoryPlaylist) }
//        binding.categoryEat.setOnClickListener { toggleSelection(binding.categoryEat) }
//        binding.categoryLook.setOnClickListener { toggleSelection(binding.categoryLook) }
//        binding.categoryPlace.setOnClickListener { toggleSelection(binding.categoryPlace) }
//
//        // Post 객체를 전달받아 View를 열도록 구현
//        binding.postBtn.setOnClickListener {
//            val post = getPostData() // 실제 데이터를 가져오는 메서드 호출
//            post?.let { navigateToPostView(it) }
//        }
//    }
//
//
//    private fun toggleSelection(selectedTextView: TextView) {
//        // 이미 선택된 TextView가 있다면, 그 배경색을 원래대로 돌려놓기
//        this.selectedTextView?.setBackgroundColor(Color.TRANSPARENT)
//
//        // 선택된 카테고리의 배경색 변경
//        if (this.selectedTextView == selectedTextView) {
//            // 다시 클릭 시 색을 원래대로 복원
//            this.selectedTextView = null
//        } else {
//            selectedTextView.setBackgroundColor(
//                ContextCompat.getColor(
//                    this,
//                    R.color.blue4
//                )
//            ) // 선택시 배경색 변경
//            this.selectedTextView = selectedTextView
//        }
//    }
//
//
//    // toolbar의 뒤로가기 버튼 클릭 시 이전 화면으로 이동
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
//
//    // 갤러리 열기
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
//        galleryLauncher.launch(intent)
//    }
//
//    // 갤러리 결과 처리
//    private val galleryLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if(result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
//                val imageUri: Uri? = result.data?.data
//                if(imageUri != null) {
//                    binding.postImage.setImageURI((imageUri))
//                } else {
//                    Toast.makeText(requireContext(), "이미지를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    override fun onResume() {
//        super.onResume()
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
//        if (bottomNavigationView.selectedItemId != R.id.posting) {
//            bottomNavigationView.selectedItemId = R.id.posting
//        }
//    }
//    private fun navigateToPostView(post: main_data.Post) {
//        val intent = Intent(this, PostViewActivity::class.java).apply {
//            putExtra("POST_TITLE", post.title)
//            putExtra("POST_IMAGE_URL", post.imageUrl)
//        }
//        startActivity(intent)
//    }
//
//    // 예제: Post 데이터를 가져오는 함수
//    private fun getPostData(): main_data.Post? {
//        // 실제 데이터 로직을 여기에 구현 (예: 데이터베이스, API, 또는 더미 데이터)
//        return main_data.Post("Sample Title", "https://example.com/image.jpg") // 예제 데이터
//    }
//
//}
//
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
import com.example.mobilesoftware.databinding.ActivityPostBinding
import com.example.mobilesoftware.model.main_data
import com.google.android.material.bottomnavigation.BottomNavigationView

class PostActivity : AppCompatActivity() {

    // 현재 선택된 카테고리 TextView를 저장
    private var selectedTextView: TextView? = null

    // View Binding 객체
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.categoryPlaylist.setOnClickListener { toggleSelection(binding.categoryPlaylist) }
        binding.categoryEat.setOnClickListener { toggleSelection(binding.categoryEat) }
        binding.categoryLook.setOnClickListener { toggleSelection(binding.categoryLook) }
        binding.categoryPlace.setOnClickListener { toggleSelection(binding.categoryPlace) }
    }

    // 이미지 추가 버튼 설정
    private fun setupAddImageButton() {
        binding.addImageButton.setOnClickListener { openGallery() }
    }

    // 포스트 버튼 클릭 설정
    private fun setupPostButton() {
        binding.postBtn.setOnClickListener {
            val post = getPostData() // Post 데이터를 가져옴
            post?.let { navigateToPostView(it) }
        }
    }

    // 선택된 카테고리의 배경색 토글
    private fun toggleSelection(selectedTextView: TextView) {
        // 이전에 선택된 TextView 배경 초기화
        this.selectedTextView?.setBackgroundColor(Color.TRANSPARENT)

        // 선택된 TextView 배경색 설정
        if (this.selectedTextView == selectedTextView) {
            this.selectedTextView = null // 다시 클릭 시 선택 해제
        } else {
            selectedTextView.setBackgroundColor(
                ContextCompat.getColor(this, R.color.blue4)
            )
            this.selectedTextView = selectedTextView
        }
    }

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

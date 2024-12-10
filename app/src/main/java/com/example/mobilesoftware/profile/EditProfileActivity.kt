package com.example.mobilesoftware.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mobilesoftware.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 제목 표시 끄기
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 업버튼 활성화

        // addImageButton 클릭 이벤트
        binding.addImageButton.setOnClickListener {
            openGallery()
        }
    }

    // 갤러리 열기
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    // 갤러리 결과 처리
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    // Glide를 사용하여 이미지를 둥글게 설정
                    Glide.with(this)
                        .load(imageUri)
                        .circleCrop() // 둥글게 자르기
                        .into(binding.profileImage) // ImageView에 적용
                } else {
                    Toast.makeText(this, "이미지를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

}
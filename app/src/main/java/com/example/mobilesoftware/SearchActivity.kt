package com.example.mobilesoftware;

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 네비게이션 뷰 초기 상태 설정
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val bottomNavigationManager = BottomNavigationManager(
            this // 액티비티 전달
        )
        bottomNavigationManager.setupWithBottomNavigationView(bottomNavigationView)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) // 2열 그리드

        // 샘플 이미지 데이터
        val imageList = listOf(
            R.drawable.sample_image1,
            R.drawable.sample_image2,
            R.drawable.sample_image3,
            R.drawable.sample_image4,
            R.drawable.sample_image5,
            R.drawable.sample_image6,
            R.drawable.sample_image1,
            R.drawable.sample_image2,
            R.drawable.sample_image3,
            R.drawable.sample_image4,
            R.drawable.sample_image5,
            R.drawable.sample_image6,
            R.drawable.sample_image1,
            R.drawable.sample_image2,
            R.drawable.sample_image3,
            R.drawable.sample_image4,
            R.drawable.sample_image5,
            R.drawable.sample_image6
        )

        val adapter = FeedAdapter(this, imageList)
        recyclerView.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        // 검색 기능 설정
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@SearchActivity, "Search: $query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 실시간 검색 처리
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // 뒤로가기 버튼 클릭 시
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        if (bottomNavigationView.selectedItemId != R.id.search) {
            bottomNavigationView.selectedItemId = R.id.search
        }
    }
}

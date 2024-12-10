package com.example.mobilesoftware;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobilesoftware.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 특정 장소들의 LatLng 리스트
        val location1 = LatLng(37.545902, 126.964397) // 숙명여자대학교
        val location2 = LatLng(37.566535, 126.977969) // 서울 시청

        // LatLngBounds 빌더로 경계 생성
        val boundsBuilder = LatLngBounds.Builder()
        boundsBuilder.include(location1) // 장소 추가
        boundsBuilder.include(location2) // 장소 추가

        // LatLngBounds 객체 생성
        val bounds = boundsBuilder.build()

        // 지도에 마커 추가
        mMap.addMarker(MarkerOptions().position(location1).title("숙명여자대학교"))
        mMap.addMarker(MarkerOptions().position(location2).title("서울 시청"))

        // 카메라를 LatLngBounds에 맞게 이동
        val padding = 300 // 경계와 화면의 여백 (픽셀)
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }


}
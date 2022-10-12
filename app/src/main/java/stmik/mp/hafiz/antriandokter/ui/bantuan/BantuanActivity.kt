package stmik.mp.hafiz.antriandokter.ui.bantuan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.ActivityBantuanBinding

class BantuanActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityBantuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bindView()
    }

    private fun bindView() {
        binding.ivBantuanBack.setOnClickListener {
            onBackPressed()
        }
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
        val zoomLevel = 15.0f

        // Add a marker in Sydney and move the camera
        val mediq = LatLng(-6.1984308958084, 106.85068787996461)
        mMap.addMarker(MarkerOptions().position(mediq).title("Marker in STIE & STMIK Jayakarta"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mediq))
        mMap.setMinZoomPreference(zoomLevel)
    }
}
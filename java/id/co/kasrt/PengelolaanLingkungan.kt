package id.co.kasrt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.co.kasrt.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PengelolaanLingkungan : AppCompatActivity() {

    private lateinit var suhuTextView: TextView
    private lateinit var kelembabanTextView: TextView
    private lateinit var kualitasUdaraTextView: TextView
    private lateinit var refreshButton: Button
    private lateinit var ubahdatButton: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var apiService: ApiService

    lateinit var autocompleteTextView: AutoCompleteTextView
    lateinit var adapterItems: ArrayAdapter<String>

    private var selectedKota: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengelolaan_lingkungan)

        autocompleteTextView = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedKota)
        autocompleteTextView.setAdapter(adapterItems)

        autocompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedKota = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Item: $selectedKota", Toast.LENGTH_SHORT).show()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apispreadsheets.com/data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        suhuTextView = findViewById(R.id.suhuTextView)
        kelembabanTextView = findViewById(R.id.kelembabanTextView)
        kualitasUdaraTextView = findViewById(R.id.kualitasUdaraTextView)
        refreshButton = findViewById(R.id.refreshButton)
        ubahdatButton = findViewById(R.id.ubahdat)

        refreshButton.setOnClickListener {
            fetchWeatherData()
        }

        ubahdatButton.setOnClickListener {
            getLastLocation()
        }

        fetchWeatherData()
        fetchKotaData()
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        fetchWeatherData()
                    }
                }
        }
    }

    private fun fetchWeatherData() {
        apiService.getWeatherData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()?.data?.firstOrNull()
                    weatherData?.let {
                        suhuTextView.text = "Suhu: ${it.suhu} °C"
                        kelembabanTextView.text = "Kelembaban: ${it.kelembaban} %"
                        kualitasUdaraTextView.text = "Kualitas Udara: ${it.kualitasUdara}"

                        // Masukkan daftar kota yang didapatkan ke arraylist selectedKota
                        selectedKota.clear()
                        selectedKota.add(it.kota)
                        adapterItems.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@PengelolaanLingkungan, "Gagal memuat data cuaca", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@PengelolaanLingkungan, "Gagal memuat data cuaca: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchKotaData() {
        apiService.getKota().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val kotaList = response.body()?.data?.map { it.kota }
                    kotaList?.let {
                        adapterItems.clear()
                        adapterItems.addAll(it)
                        adapterItems.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@PengelolaanLingkungan, "Gagal memuat data kota", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@PengelolaanLingkungan, "Gagal memuat data kota: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}

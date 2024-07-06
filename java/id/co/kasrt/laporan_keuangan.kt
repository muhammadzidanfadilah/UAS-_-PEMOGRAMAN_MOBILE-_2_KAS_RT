package id.co.kasrt

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class laporan_keuangan : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var textViewNamadepan: TextView? = null
    private var textViewNamabelakang: TextView? = null
    private var textViewEmail: TextView? = null
    private var textViewAlamat: TextView? = null
    private var textViewIuran: TextView? = null

    private var textViewTotalIuran: TextView? = null
    private var textViewTotalRekap: TextView? = null
    private var textViewPengeluaran: TextView? = null
    private var textViewPemanfaatan: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_keuangan)
        imageView = findViewById(R.id.imageView)
        textViewNamadepan = findViewById(R.id.namdep)
        textViewNamabelakang = findViewById(R.id.nambel)
        textViewEmail = findViewById(R.id.email)
        textViewAlamat = findViewById(R.id.alamat)
        textViewIuran = findViewById(R.id.IuranBulanan)
        textViewTotalIuran = findViewById(R.id.IuranIndividu)
        textViewTotalRekap = findViewById(R.id.IuranAkhir)
        textViewPengeluaran = findViewById(R.id.Pengeluaran)
        textViewPemanfaatan = findViewById(R.id.Pemanfaatan)
        ambilDataDariAPI()
    }

    private fun ambilDataDariAPI() {
        val url = "https://api.apispreadsheets.com/data/aEG8TC78MAVF3vMH/"
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val daftarWarga = response.getJSONArray("data")
                        for (i in 0 until daftarWarga.length()) {
                            val warga = daftarWarga.getJSONObject(i)
                            val namaDepan = warga.getString("nama depan")
                            val namaBelakang = warga.getString("nama belakang")
                            val email = warga.getString("email")
                            val alamat = warga.getString("alamat")
                            val iuranBulananWarga = warga.getDouble("iuran bulanan warga")
                            val iuranIndividuWarga = warga.getDouble("iuran individu warga")
                            val iuranRekap = warga.getDouble("iuran warga pada akhir rekap iuran bulanan")
                            val pengeluaranIuran = warga.getDouble("pengeluaran iuran")
                            val pemanfaatanIuran = warga.getDouble("pemanfaatan dari iuran warga")

                            // Ambil dan tampilkan data lainnya sesuai kebutuhan
                            textViewNamadepan?.text = "Nama depan: $namaDepan "
                            textViewNamabelakang?.text = "Nama belakang: $namaBelakang"
                            textViewEmail?.text = "Email: $email"
                            textViewAlamat?.text = "Alamat Rumah: $alamat"
                            textViewIuran?.text = "Jumlah iuran bulanan warga: $iuranBulananWarga"
                            textViewTotalIuran?.text = "Total iuran individu warga: $iuranIndividuWarga"
                            textViewTotalRekap?.text = "Total iuran warga pada akhir\nrekap iuran bulanan: $iuranRekap"
                            textViewPengeluaran?.text = "Pengeluaran iuran dari hasil\niuran warga: $pengeluaranIuran"
                            textViewPemanfaatan?.text = "Pemanfaatan dari iuran warga\nuntuk apa saja: $pemanfaatanIuran"
                            // ... setel data lainnya

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error ->
            textViewNamadepan?.text = "Error: ${error.message}"
        })
        requestQueue.add(jsonObjectRequest)
    }
}

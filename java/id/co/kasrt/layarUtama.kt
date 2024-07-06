package id.co.kasrt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.messaging.FirebaseMessaging


class layarUtama : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layar_utama)

        val apiKey = "AIzaSyC9I8RAPfbNZh_BLve0j-tNJ1XNWA7yTgY"
        val deviceToken = "624327903423-nfdf0mksbatlciovi9aul96aiuave4vo.apps.googleusercontent.com"



        FirebaseMessaging.getInstance().subscribeToTopic("Iuran")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }

            }

        val topView: View = findViewById(R.id.top_View)
        val cardView_orang: CardView = findViewById(R.id.tambah)
        val cardView_api: CardView = findViewById(R.id.lihat)
        val cardView_ubah: CardView = findViewById(R.id.ubah)
        val cardView_hapus: CardView = findViewById(R.id.hapus)

        val cardView_warga: CardView = findViewById(R.id.datawarga)
        val cardView_laporan: CardView = findViewById(R.id.laporan)

        cardView_orang.setOnClickListener {
            val intent = Intent(this, add_data::class.java)
            startActivity(intent)
        }

        cardView_api.setOnClickListener {
            val intent = Intent(this, read_data::class.java)
            startActivity(intent)
        }
        cardView_ubah.setOnClickListener {
            val intent = Intent(this, ubah_data::class.java)
            startActivity(intent)
        }
        cardView_hapus.setOnClickListener {
            val intent = Intent(this, hapus_data::class.java)
            startActivity(intent)
        }

        cardView_warga.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        cardView_laporan.setOnClickListener {
            val intent = Intent(this, NavSamping::class.java)
            startActivity(intent)
        }
    }
    // Fungsi untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
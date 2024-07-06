package id.co.kasrt

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import okhttp3.*

class read_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)

        val db = Firebase.firestore

        val namdep: TextView = findViewById(R.id.namdep)
        val nambel: TextView = findViewById(R.id.nambel)
        val email: TextView = findViewById(R.id.email)
        val alamat: TextView = findViewById(R.id.alamat)
        val iuran_bulanan: TextView = findViewById(R.id.IuranBulanan)
        val iuran_individu: TextView = findViewById(R.id.IuranIndividu)
        val iuran_akhir: TextView = findViewById(R.id.IuranAkhir)
        val pengeluaran: TextView = findViewById(R.id.Pengeluaran)
        val pemanfaatan: TextView = findViewById(R.id.Pemanfaatan)



        db.collection("laporan")
            .whereEqualTo("Nama Depan", "Kumara Davin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    // Retrieve each field and log it or use it as needed
                    val namaDepan = document.getString("Nama Depan") ?: "N/A"
                    val namaBelakang = document.getString("Nama Belakang") ?: "N/A"
                    val emailData = document.getString("Email") ?: "N/A"
                    val alamatRumah = document.getString("Alamat Rumah") ?: "N/A"
                    val jumlahIuranBulanan = document.getDouble("Jumlah Iuran Bulanan")?.toString() ?: "0"
                    val pemanfaatanIuran = document.getString("Pemanfaatan Iuran") ?: "N/A"
                    val pengeluaranIuran = document.getDouble("Pengeluaran Iuran")?.toString() ?: "0"
                    val totalIuranIndividu = document.getDouble("Total Iuran Individu")?.toString() ?: "0"
                    val totalIuranAkhir = document.getDouble("Total Iuran Akhir")?.toString() ?: "0"

                    namdep.text = "Nama Depan: $namaDepan"
                    nambel.text = "Nama Belakang: $namaBelakang"
                    email.text = "Email: $emailData"
                    alamat.text = "Alamat Rumah: $alamatRumah"
                    iuran_bulanan.text = "Jumlah iuran bulanan warga: $jumlahIuranBulanan"
                    pemanfaatan.text = "Pemanfaatan dari iuran warga\nuntuk apa saja: $pemanfaatanIuran"
                    pengeluaran.text = "Pengeluaran iuran dari hasil\niuran warga: $pengeluaranIuran"
                    iuran_individu.text = "Total Iuran Individu warga: $totalIuranIndividu"
                    iuran_akhir.text = "Total iuran warga pada\nakhir rekap iuran bulanan: $totalIuranAkhir"

                    if (totalIuranAkhir == "0") {
                        main()
                        subscribeToIuranTopic()
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    fun subscribeToIuranTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("Iuran")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                println(msg)
            }
    }

    fun main() {
        MyFirebaseMessagingServices()
    }

    companion object {
        private const val TAG = "read_data"
    }
}
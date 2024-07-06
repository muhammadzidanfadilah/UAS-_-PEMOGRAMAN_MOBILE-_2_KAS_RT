package id.co.kasrt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG

class add_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        val db = Firebase.firestore

        val namdep: EditText = findViewById(R.id.namdep)
        val nambel: EditText = findViewById(R.id.nambel)
        val email: EditText = findViewById(R.id.email)
        val alamat: EditText = findViewById(R.id.alamat)
        val iuran_bulanan: EditText = findViewById(R.id.IuranBulanan)
        val iuran_individu: EditText = findViewById(R.id.IuranIndividu)
        val iuran_akhir: EditText = findViewById(R.id.IuranAkhir)
        val pengeluaran: EditText = findViewById(R.id.Pengeluaran)
        val pemanfaatan: EditText = findViewById(R.id.Pemanfaatan)

        val kirim: Button = findViewById(R.id.kirim)

        kirim.setOnClickListener {
            val namaDepan = namdep.text.toString().trim()
            val namaBelakang = nambel.text.toString().trim()
            val emailText = email.text.toString().trim()
            val alamatRumah = alamat.text.toString().trim()

            val jumlahIuranBulanan = iuran_bulanan.text.toString().toIntOrNull() ?: 0
            val totalIuranIndividu = iuran_individu.text.toString().toIntOrNull() ?: 0
            val totalIuranAkhir = iuran_akhir.text.toString().toIntOrNull() ?: 0    // int
            val pengeluaranIuran = pengeluaran.text.toString().toIntOrNull() ?: 0

            val pemanfaatanIuran = pemanfaatan.text.toString().trim()

            val laporan = hashMapOf(
                "Nama Depan" to namaDepan,
                "Nama Belakang" to namaBelakang,
                "Email" to emailText,
                "Alamat Rumah" to alamatRumah,
                "Jumlah Iuran Bulanan" to jumlahIuranBulanan,
                "Total Iuran Individu" to totalIuranIndividu,
                "Total Iuran Akhir" to totalIuranAkhir,
                "Pengeluaran Iuran" to pengeluaranIuran,
                "Pemanfaatan Iuran" to pemanfaatanIuran
            )

            // Add a new document with a generated ID
            db.collection("laporan")
                .add(laporan)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this, "Gagal mengirim laporan", Toast.LENGTH_SHORT).show()
                }
        }

    }
}
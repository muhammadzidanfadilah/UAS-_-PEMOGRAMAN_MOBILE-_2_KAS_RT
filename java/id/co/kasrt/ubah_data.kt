package id.co.kasrt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants

class ubah_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_data)

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

        db.collection("laporan")
            .whereEqualTo("Nama Depan", "Kumara Davin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(Constants.MessageNotificationKeys.TAG, "${document.id} => ${document.data}")
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

                    namdep.setText(namaDepan)
                    nambel.setText(namaBelakang)
                    email.setText(emailData)
                    alamat.setText(alamatRumah)
                    iuran_bulanan.setText(jumlahIuranBulanan)
                    pemanfaatan.setText(pemanfaatanIuran)
                    pengeluaran.setText(pengeluaranIuran)
                    iuran_individu.setText(totalIuranIndividu)
                    iuran_akhir.setText(totalIuranAkhir)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(Constants.MessageNotificationKeys.TAG, "Error getting documents.", exception)
            }

        val documentId = "kCQRhdqmFFbBwCR5gSiU" // id dokumen

        kirim.setOnClickListener {
            val namaDepan = namdep.text.toString().trim()
            val namaBelakang = nambel.text.toString().trim()
            val emailText = email.text.toString().trim()
            val alamatRumah = alamat.text.toString().trim()

            val jumlahIuranBulanan = iuran_bulanan.text.toString().toIntOrNull() ?: 0
            val totalIuranIndividu = iuran_individu.text.toString().toIntOrNull() ?: 0
            val totalIuranAkhir = iuran_akhir.text.toString().toIntOrNull() ?: 0
            val pengeluaranIuran = pengeluaran.text.toString().toIntOrNull() ?: 0

            val pemanfaatanIuran = pemanfaatan.text.toString().trim()

            val ubahData: MutableMap<String, Any> = hashMapOf(
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

            db.collection("laporan")
                .document(documentId)
                .update(ubahData)
                .addOnSuccessListener {
                    // Berhasil diupdate
                    Log.d("FirestoreUpdate", "DocumentSnapshot successfully updated!")
                    Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Gagal diupdate
                    Log.w("FirestoreUpdate", "Error updating document", e)
                    Toast.makeText(this, "Gagal mengubah data", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
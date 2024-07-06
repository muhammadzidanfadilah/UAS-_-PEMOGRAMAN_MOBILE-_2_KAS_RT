package id.co.kasrt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class payment : AppCompatActivity() {

    private lateinit var billAmountTextView: TextView
    private lateinit var dueDateTextView: TextView
    private lateinit var payButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Inisialisasi Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // Menghubungkan view dengan variabel
        billAmountTextView = findViewById(R.id.billAmountTextView)
        dueDateTextView = findViewById(R.id.dueDateTextView)
        payButton = findViewById(R.id.payButton)

        // Ambil informasi tagihan dari Firestore
        fetchBillDetails()

        // Set listener untuk tombol bayar
        payButton.setOnClickListener {
            processPayment()
        }
    }

    private fun fetchBillDetails() {
        db.collection("bills")
            .document("user_bill")  // Ganti dengan ID dokumen tagihan yang sesuai
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val amount = document.getString("amount")
                        val dueDate = document.getString("dueDate")
                        billAmountTextView.text = "Jumlah Tagihan: $amount"
                        dueDateTextView.text = "Jatuh Tempo: $dueDate"
                    } else {
                        Toast.makeText(this, "Tidak ada informasi tagihan yang ditemukan.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Gagal mengambil informasi tagihan.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun processPayment() {
        // Logika untuk memproses pembayaran
        // Misalnya, memperbarui status tagihan di Firestore
        db.collection("bills")
            .document("user_bill")  // Ganti dengan ID dokumen tagihan yang sesuai
            .update("status", "paid")
            .addOnSuccessListener {
                Toast.makeText(this, "Pembayaran berhasil.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Pembayaran gagal.", Toast.LENGTH_SHORT).show()
            }
    }
}
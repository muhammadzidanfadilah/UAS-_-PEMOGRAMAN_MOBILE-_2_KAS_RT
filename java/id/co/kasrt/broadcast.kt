package id.co.kasrt

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class broadcast : AppCompatActivity() {

    private lateinit var broadcastEditText: EditText
    private lateinit var sendBroadcastButton: Button
    private lateinit var broadcastListView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var broadcastMessages: MutableList<String>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)

        // Inisialisasi Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // Menghubungkan view dengan variabel
        broadcastEditText = findViewById(R.id.broadcastEditText)
        sendBroadcastButton = findViewById(R.id.sendBroadcastButton)
        broadcastListView = findViewById(R.id.broadcastListView)

        // Inisialisasi list dan adapter untuk broadcast
        broadcastMessages = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, broadcastMessages)
        broadcastListView.adapter = adapter

        // Set listener untuk tombol kirim broadcast
        sendBroadcastButton.setOnClickListener {
            val message = broadcastEditText.text.toString()
            if (message.isNotEmpty()) {
                sendBroadcast(message)
            } else {
                Toast.makeText(this, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        // Mendapatkan pesan broadcast dari Firestore
        fetchBroadcastMessages()
    }

    private fun sendBroadcast(message: String) {
        val broadcastData = hashMapOf(
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("broadcasts")
            .add(broadcastData)
            .addOnSuccessListener {
                Toast.makeText(this, "Informasi siaran berhasil dikirim.", Toast.LENGTH_SHORT).show()
                broadcastEditText.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengirim informasi siaran.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchBroadcastMessages() {
        db.collection("broadcasts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val message = document.getString("message")
                        if (message != null) {
                            broadcastMessages.add(message)
                        }
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Gagal mengambil informasi siaran.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
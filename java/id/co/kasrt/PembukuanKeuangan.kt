package id.co.kasrt

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_pembukuan_keuangan.*

class PembukuanKeuangan : AppCompatActivity() {

    private val daftarTransaksi = mutableListOf<Transaksi>()
    private lateinit var adapter: ArrayAdapter<Transaksi>

    private val edit_text_description = findViewById<EditText>(R.id.edit_text_description)
    private val edit_text_amount = findViewById<EditText>(R.id.edit_text_amount)
    private val spinner_type = findViewById<Spinner>(R.id.spinner_type)

    private val text_view_balance = findViewById<TextView>(R.id.text_view_balance)

    private var saldo: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembukuan_keuangan)

        val list_view_transactions = findViewById<ListView>(R.id.list_view_transactions)
        val button_add_transaction = findViewById<Button>(R.id.button_add_transaction)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarTransaksi)
        list_view_transactions.adapter = adapter

        button_add_transaction.setOnClickListener {
            tambahTransaksi()
        }

        // Data dummy untuk spinner (jenis transaksi)
        val jenisTransaksi = resources.getStringArray(R.array.transaction_types)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisTransaksi)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_type.adapter = spinnerAdapter
    }

    private fun tambahTransaksi() {
        val deskripsi = edit_text_description.text.toString().trim()
        val jumlahStr = edit_text_amount.text.toString().trim()
        val jenis = spinner_type.selectedItem.toString()

        if (deskripsi.isEmpty() || jumlahStr.isEmpty()) {
            Toast.makeText(this, "Silakan masukkan deskripsi dan jumlah", Toast.LENGTH_SHORT).show()
            return
        }

        val jumlah = jumlahStr.toDouble()

        // Membuat objek Transaksi
        val transaksi = Transaksi(deskripsi, jumlah, jenis)
        daftarTransaksi.add(transaksi)
        adapter.notifyDataSetChanged()

        // Memperbarui saldo
        if (jenis == "Pemasukan") {
            saldo += jumlah
        } else {
            saldo -= jumlah
        }

        perbaruiSaldo()

        // Mengosongkan kolom input
        edit_text_description.text.clear()
        edit_text_amount.text.clear()
    }

    private fun perbaruiSaldo() {
        text_view_balance.text = "Saldo: Rp${String.format("%.2f", saldo)}"
    }
}

data class Transaksi(val deskripsi: String, val jumlah: Double, val jenis: String) {
    override fun toString(): String {
        return "$deskripsi: ${if (jenis == "Pemasukan") "+Rp" else "-Rp"}${String.format("%.2f", jumlah)}"
    }
}

package id.co.kasrt

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class settings : AppCompatActivity() {
    private lateinit var clickBroadcastReceiver: ClickBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Inisialisasi dan daftarkan BroadcastReceiver
        clickBroadcastReceiver = ClickBroadcastReceiver()
        val filter = IntentFilter("id.co.kasrt.BUTTON_CLICKED")
        registerReceiver(clickBroadcastReceiver, filter)

        // Temukan tombol dan atur onClickListener untuk mengirim broadcast
        val button = findViewById<Button>(R.id.klikpanik)
        button.setOnClickListener {
            val intent = Intent("id.co.kasrt.BUTTON_CLICKED")
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        unregisterReceiver(clickBroadcastReceiver)
    }
}

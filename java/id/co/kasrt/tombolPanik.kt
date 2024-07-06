package id.co.kasrt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class tombolPanik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tombol_panik)

        val panicButton: Button = findViewById(R.id.panicButton)
        panicButton.setOnClickListener {
            val intent = Intent()
            intent.action = "id.co.kasrt.PANIC_ACTION"
            sendBroadcast(intent)
        }
    }
}

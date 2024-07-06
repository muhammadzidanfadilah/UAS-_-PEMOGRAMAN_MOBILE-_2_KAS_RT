package id.co.kasrt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "id.co.kasrt.PANIC_ACTION") {
            Toast.makeText(context, "Panic button pressed!", Toast.LENGTH_LONG).show()
        }
    }
}

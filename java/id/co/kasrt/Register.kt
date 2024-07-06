package id.co.kasrt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val rootView = window.decorView.findViewById<RelativeLayout>(R.id.root_layout)
        val blurView = findViewById<BlurView>(R.id.blurView)

        val email: EditText = findViewById(R.id.email)
        val pass: EditText = findViewById(R.id.password)
        val confirmpass: EditText = findViewById(R.id.confirmPassword)

        val register: Button = findViewById(R.id.register)

        val radius = 1f
        val windowBackground = window.decorView.background

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setHasFixedTransformationMatrix(true)
    }
}
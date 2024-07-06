package id.co.kasrt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RelativeLayout
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class akun : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)

        val rootView = window.decorView.findViewById<RelativeLayout>(R.id.root_layout)
        val blurView = findViewById<BlurView>(R.id.blurView)

        val login: Button = findViewById(R.id.logins)
        val register: Button = findViewById(R.id.regyster)

        val radius = 1f
        val windowBackground = window.decorView.background

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setHasFixedTransformationMatrix(true)

        login.setOnClickListener {
            val modulasi: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink_masuk)
            login.startAnimation(modulasi)
            register.startAnimation(modulasi)

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val modulasi: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink_masuk)
            login.startAnimation(modulasi)
            register.startAnimation(modulasi)

            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
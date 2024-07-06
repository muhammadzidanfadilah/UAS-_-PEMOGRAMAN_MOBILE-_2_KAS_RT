package id.co.kasrt

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NavSamping : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_samping)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit()
            }
            R.id.nav_about -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AboutFragment()).commit()
            }

            R.id.nav_panik -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PanikFragment()).commit()
            }

            R.id.broadcast -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BroadcastFragment()).commit()
            }

            R.id.wajah -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WajahFragment()).commit()
            }

            R.id.lingkungan -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LingkunganFragment()).commit()
            }

            R.id.chatbot -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ChatbotFragment()).commit()
            }

            R.id.payment -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PaymentFragment()).commit()
            }

            R.id.digitalisasi -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DigitFragment()).commit()
            }

            R.id.nav_logout -> {
                Toast.makeText(this, "Anda telah keluar", Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                val intent = Intent(this, akun::class.java)
                startActivity(intent)
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

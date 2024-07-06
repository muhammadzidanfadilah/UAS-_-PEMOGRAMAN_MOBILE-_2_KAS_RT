package id.co.kasrt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import id.co.kasrt.model.Dataitem
import id.co.kasrt.model.ResponseUser
import id.co.kasrt.network.ApiConfig

// call retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// call okhttp
//import okhttp3.Call
//import okhttp3.Callback

class MainActivity2 : AppCompatActivity() {
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        adapter = UserAdapter(mutableListOf())

        val rv_users  = findViewById<RecyclerView>(R.id.rv_users)  // Buat val
        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter
        getUser()


    }
    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("1")
        client.enqueue(object: Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<Dataitem> // ?: emptyList()

                    for (data in dataArray) {
                        adapter.addUser(data)
                        println(data.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@MainActivity2, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

}

package id.co.kasrt


import com.google.gson.annotations.SerializedName
import id.co.kasrt.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface ApiService {
    @GET("pm2FfNTBsB8C6P5E")
    fun getWeatherData(): Call<ApiResponse>

    @GET("pm2FfNTBsB8C6P5E")
    fun getKota(): Call<ApiResponse>
}

data class ApiResponse(
    @SerializedName("data") val data: List<WeatherData>
)

data class WeatherData(
    @SerializedName("suhu") val suhu: Double,
    @SerializedName("kelembaban") val kelembaban: Double,
    @SerializedName("kualitas_udara") val kualitasUdara: String,
    @SerializedName("kota") val kota: String
)

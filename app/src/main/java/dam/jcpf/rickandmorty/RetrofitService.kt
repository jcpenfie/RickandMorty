package dam.jcpf.rickandmorty

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Las dos data class base que usa la API de Rick and Morty. La que me interesa es la de results
data class EpisodesResponse(
    val info: Info,
    val results: List<Episodes>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

interface RetrofitService {
    @GET("api/episode")
    fun getEpisodes(): Call<EpisodesResponse> // Quita suspend y usa Call
}

object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/"

    fun getRetroftInstance(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}
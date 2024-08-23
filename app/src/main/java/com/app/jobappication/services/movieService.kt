package com.firstapp.jetpack1.movieApp

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"

interface MovieAPIService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): MovieModel

    companion object {
        private var apiService: MovieAPIService? = null

        fun getInstance(): MovieAPIService {
            if (apiService == null) {
                val gson = GsonBuilder().setLenient().create()
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(MovieAPIService::class.java)
            }
            return apiService!!
        }
    }
}

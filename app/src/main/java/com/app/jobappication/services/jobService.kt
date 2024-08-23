package com.app.jobappication.services

import com.app.jobappication.model.JobModel
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://findwork.dev/api/"
const val API_KEY = "85dbc03b30e78a3d48c605a342c097fd0f7cdec0"

interface JobAPIService {
    @GET("jobs/")
    suspend fun getJobs(
        @Query("search") search: String? = null,
        @Query("source") source: String? = null,
        @Query("location") location: String? = null,
        @Query("remote") remote: Boolean? = null,
        @Query("company_num_employees") companyNumEmployees: String? = null,
        @Query("employment_type") employmentType: String? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("page") page: Int? = null
    ): JobModel

    companion object {
        private var apiService: JobAPIService? = null

        fun getInstance(): JobAPIService {
            if (apiService == null) {
                val gson = GsonBuilder().setLenient().create()

                // Create an interceptor to add the API key in the header
                val authInterceptor = Interceptor { chain ->
                    val originalRequest = chain.request()
                    val newRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Token $API_KEY")
                        .build()
                    chain.proceed(newRequest)
                }

                // Create an OkHttpClient and add the interceptor
                val client = OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()

                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)  // Add the OkHttpClient with the interceptor
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(JobAPIService::class.java)
            }
            return apiService!!
        }
    }
}

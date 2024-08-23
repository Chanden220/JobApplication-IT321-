package com.app.jobappication.viewmodel

import android.net.Uri
import android.net.http.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jobappication.model.JobModel
import com.app.jobappication.model.Results
import com.app.jobappication.services.JobAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class JobViewModel : ViewModel() {

    // Use MutableStateFlow to manage state
    private val _jobs = mutableStateListOf<Results>()
    val jobs: List<Results> = _jobs

    var nextPageUrl: String? by mutableStateOf("")
    var prevPageUrl: String? by mutableStateOf("")
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)
    var page by mutableStateOf(1)
    private val jobApiService = JobAPIService.getInstance()

    fun fetchJobs(
        search: String = "",
        source: String? = null,
        location: String? = null,
        remote: Boolean? = null,
        companyNumEmployees: String? = null,
        employmentType: String? = null,
        orderBy: String = "date"
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = jobApiService.getJobs(
                    search = search,
                    source = source,
                    location = location,
                    remote = remote,
                    companyNumEmployees = companyNumEmployees,
                    employmentType = employmentType,
                    orderBy = orderBy,
                    page = page
                )
                _jobs.clear()
                _jobs.addAll(response.results)

                // Update next and previous page URLs
                nextPageUrl = response.next
                prevPageUrl = response.previous
            } catch (e: IOException) {
                errorMessage = "Network error: ${e.message}"
            } catch (e: HttpException) {
                errorMessage = "HTTP error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchNextPage() {
        nextPageUrl?.let {
            val nextPage = extractPageNumber(it)
            if (nextPage != null) {
                page = nextPage
                fetchJobs()
            }
        }
    }

    fun fetchPreviousPage() {
        prevPageUrl?.let {
            val prevPage = extractPageNumber(it)
            if (prevPage != null) {
                page = prevPage
                fetchJobs()
            }
        }
    }

    private fun extractPageNumber(url: String): Int? {
        return Uri.parse(url).getQueryParameter("page")?.toInt()
    }
}

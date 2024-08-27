package com.app.jobappication.viewmodel

import android.net.Uri
import android.net.http.HttpException
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jobappication.model.JobModel
import com.app.jobappication.model.Results
import com.app.jobappication.services.JobAPIService
import kotlinx.coroutines.launch
import java.io.IOException

class JobViewModel : ViewModel() {

    private val _jobs = mutableStateListOf<Results>()
    val jobs: List<Results> = _jobs

    private val _favoriteJobs = mutableStateListOf<Results>()
    val favoriteJobs: List<Results> = _favoriteJobs

    var nextPageUrl: String? by mutableStateOf("")

    var prevPageUrl: String? by mutableStateOf("")
    var employeeType: String? by mutableStateOf("")
    var locationSearch: String? by mutableStateOf("")
    var search: String? by mutableStateOf("")
    var remote: Boolean? by mutableStateOf(null)
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)
    var page by mutableStateOf(1)
    private val jobApiService = JobAPIService.getInstance()

    fun fetchJobs(
        search: String? = null,
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
                println("fetchJobs parameters: search=$search, source=$source, location=$location, remote=$remote, companyNumEmployees=$companyNumEmployees, employmentType=$employmentType, orderBy=$orderBy, page=$page")

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

    fun addJobToFavorites(job: Results) {
        if (!_favoriteJobs.contains(job)) {
            _favoriteJobs.add(job)
        }
    }

    fun removeJobFromFavorites(job: Results) {
        _favoriteJobs.remove(job)
    }

    fun isJobFavorite(job: Results): Boolean {
        return _favoriteJobs.contains(job)
    }
}

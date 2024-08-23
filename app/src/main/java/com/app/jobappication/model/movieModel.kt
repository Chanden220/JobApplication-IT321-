// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.firstapp.jetpack1.movieApp

data class MovieModel (
    val page: Long,
    val total_pages: Long,
    val results: List<Result>,
    val totalResults: Long
)

data class Result (
    val overview: String,
    val originalLanguage: OriginalLanguage,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIDS: List<Long>,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val popularity: Double,
    val vote_average: Double,
    val id: Long,
    val adult: Boolean,
    val vote_count: Long
){
    val full_poster_path : String
        get() = "https://image.tmdb.org/t/p/original/" + poster_path
}

enum class OriginalLanguage {
    En,
    Es,
    Fr,
    Ko
}

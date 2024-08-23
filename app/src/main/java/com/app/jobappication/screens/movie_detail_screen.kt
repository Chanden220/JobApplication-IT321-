package com.app.jobappication.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.firstapp.jetpack1.movieApp.Result

@Composable
fun movieDetailScreen(navController: NavController, selectMovie: Result?) {
    Scaffold(
        topBar = {
            composeTopBar(navController, selectMovie)
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.dp)
                .border(2.dp, Color.Gray)
        ) {
            selectMovie?.let { movie ->
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    item {
                        Text(
                            text = movie.title,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    item {
                        AsyncImage(
                            model = movie.full_poster_path,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(400.dp)
                                .height(400.dp)
                                .padding(8.dp),

                            )
                    }
                    item {
                        Text(
                            text = "Release Date : ${movie.release_date}",
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    item {
                        Text(
                            text = "Rating : ${movie.vote_average}★(${movie.vote_count})",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    item {
                        Text(
                            text = "Description :",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    item {
                        Text(
                            text = movie.overview,
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun composeTopBar(navController: NavController, selectMovie: Result?) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        },
        title = { Text("${selectMovie?.title}") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Share, contentDescription = "Share")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Settings, contentDescription = "Settings")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
    )
}

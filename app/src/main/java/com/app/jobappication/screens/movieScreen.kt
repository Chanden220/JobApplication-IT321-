package com.app.jobappication.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.firstapp.jetpack1.R
import com.app.jobappication.viewmodel.MovieViewModel
import com.firstapp.jetpack1.movieApp.Result

// ViewModel to manage the theme state
class ThemeViewModel : ViewModel() {
    var isDarkTheme by mutableStateOf(false)
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}

@Composable
fun movieApp() {
    val navController = rememberNavController()
    val vm = MovieViewModel()
    val themeViewModel: ThemeViewModel = viewModel()

    val lightColors = lightColorScheme(
        primary = Color.Black,
        onPrimary = Color.White,
        surface = Color.White,
        onSurface = Color.Black
    )

    val darkColors = darkColorScheme(
        primary = Color.Gray,
        onPrimary = Color.Black,
        surface = Color.Black,
        onSurface = Color.White
    )

    MaterialTheme(
        colorScheme = if (themeViewModel.isDarkTheme) darkColors else lightColors
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                movieScreen(vm = vm, navController, themeViewModel)
            }
            composable("detail/{movieName}", arguments = listOf(navArgument("movieName") {
                type = NavType.StringType
            })) { backStackEntry ->
                val productName = backStackEntry.arguments?.getString("movieName")
                val selectMovie = vm.movies.firstOrNull { it.title == productName }
                movieDetailScreen(navController, selectMovie)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun movieScreen(vm: MovieViewModel, navController: NavController, themeViewModel: ThemeViewModel) {

    LaunchedEffect(Unit) { vm.fetchMovies() }
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary) ,
            title = { Text("Movie",color = MaterialTheme.colorScheme.primary) },
            actions = {
                IconButton(onClick = { vm.fetchMovies() }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = {vm.sortMenu = !vm.sortMenu}){
                    Icon(Icons.Default.Menu, contentDescription = "SortMenu",tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { themeViewModel.toggleTheme() }) {
                    vm.fetchMovies()
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = if (themeViewModel.isDarkTheme) R.drawable.light_mode else R.drawable.dark_mode),
                        contentDescription = "Toggle Theme",
                        tint = MaterialTheme.colorScheme.primary

                    )
                }
            }
        )
    },
        bottomBar = {
            BottomAppBar() {
                if (vm.isLoading) {

                } else{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (vm.page > 1) {
                                    vm.page -= 1
                                    vm.fetchMovies()
                                }
                            },
                            enabled = vm.page > 1
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text("${vm.page} of ${vm.totalPage}")
                        IconButton(onClick = {
                            vm.page += 1
                            vm.fetchMovies()
                        },
                            enabled = vm.page < vm.totalPage
                        ) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                movieBody(vm, navController)
            }
        }
    }
}

@Composable
fun movieBody(vm: MovieViewModel, navController: NavController) {
    var displayMovieItem by remember { mutableStateOf(true) }
    if (vm.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if(vm.sortMenu){
            Column {
                Text("Sort By :",modifier = Modifier.padding(start = 8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { vm.updateSortCriterion("popularity") },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (vm.sortCriterion == "popularity" && !vm.sortByBool) "Popularity ↑" else "Popularity ↓",
                            fontSize = 12.sp
                        )
                    }
                    Button(
                        onClick = { vm.updateSortCriterion("release_date") },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (vm.sortCriterion == "release_date" && !vm.sortByBool) "Release Date ↑" else "Release Date ↓",
                            fontSize = 12.sp
                        )
                    }
                    Button(onClick = { vm.updateSortCriterion("vote_average") }) {
                        Text(
                            text = if (vm.sortCriterion == "vote_average" && !vm.sortByBool) "Vote Average ↑" else "Vote Average ↓",
                            fontSize = 11.sp
                        )
                    }

                }
                Text("Layout :",modifier = Modifier.padding(start = 8.dp))
                Row {
                    IconButton(
                        onClick = { displayMovieItem = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.three_grid),
                            contentDescription = "Grid Views"
                        )
                    }
                    IconButton(
                        onClick = { displayMovieItem = false },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.one_layout),
                            contentDescription = "List Views"
                        )
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (displayMovieItem) {
                itemsIndexed(vm.movies.chunked(3)) { _, rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { movie ->
                            movieItemGrid(movie, navController)
                        }
                    }
                }
            }else
            {
                items(vm.movies.size){
                    movieItemList(movie = vm.movies[it], navController = navController)
                }
            }
        }
    }
}

@Composable
fun movieItemGrid(movie: Result, navController: NavController) {
    Column(
        modifier = Modifier
            .width(140.dp)  // Adjust width as needed
            .clickable { navController.navigate("detail/${movie.title}") },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
        ) {
            AsyncImage(
                model = movie.full_poster_path,
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
            )
            Box(
                contentAlignment = Alignment.BottomStart
                ,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularVoteAverage(voteAverage = movie.vote_average)
            }
        }
        Text("${movie.title}", fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, color = MaterialTheme.colorScheme.onSurface)
        Text(text = "Release: ${movie.release_date}", fontSize = 10.sp, maxLines = 1,color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    }
}

@Composable
fun CircularVoteAverage(voteAverage: Double) {
    val percentage = (voteAverage * 10).toInt()
    val color = when {
        percentage >= 75 -> Color.Green
        percentage >= 50 -> Color.Yellow
        else -> Color.Red
    }

    Box( contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
        ) {
            val sweepAngle = 360 * (percentage / 100f)


            drawCircle(
                color = Color.DarkGray,
                radius = size.minDimension / 2,
                center = center
            )

            drawArc(
                color = Color.Gray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8f)
            )
            drawArc(
                color = color,
                startAngle = 270f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 8f)
            )

        }
        Text(
            text = "$percentage%",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
fun movieItemList(movie: Result, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detail/${movie.title}") }
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .align(Alignment.CenterVertically)
        ) {
            AsyncImage(
                model = movie.full_poster_path,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${movie.vote_average} ★ (${movie.vote_count})",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Release date: ${movie.release_date}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

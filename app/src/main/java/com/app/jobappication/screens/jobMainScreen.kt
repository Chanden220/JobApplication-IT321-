package com.app.jobappication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.jobappication.model.Results
import com.app.jobappication.viewmodel.JobViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobMainScreen(vm: JobViewModel = viewModel(), navController: NavController) {
    // Launch the job fetching on component load
    LaunchedEffect(Unit) { vm.fetchJobs() }
    Nav { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF1F1F1))
        ){
        JobBody(vm, navController)
        }
    }


}

@Composable
fun JobBody(vm: JobViewModel, navController: NavController) {
    var expandedCategory: Boolean by remember { mutableStateOf(false) }
    var expandedLoactionSearch: Boolean by remember { mutableStateOf(false) }
    if (vm.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (vm.errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = vm.errorMessage, color = Color.Red)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            // Carousel Section using LazyRow with auto-scrolling
            AutoScrollingCarousel(jobs = vm.jobs)
            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Row {
                    Icon(Icons.Default.Category, contentDescription = "Category")
                    Text(
                        text = "Category", fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "CategoryAll",modifier = Modifier.clickable { navController.navigate("bycategory") })
            }
            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Box(
                    modifier = Modifier
                        .width(185.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(
                            color = Color(0xFF022E4D),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)

                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color(0xFFFFA500)
                        )
                        Text(
                            text = "Location",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .width(185.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(
                            color = Color(0xFF022E4D),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)

                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Icon(
                            Icons.Default.Work,
                            contentDescription = "WorkMode",
                            tint = Color(0xFFFFA500)
                        )
                        Text(
                            text = "Work Type",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Box(
                    modifier = Modifier
                        .width(185.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(
                            color = Color(0xFF022E4D),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)

                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Icon(
                            Icons.Default.Wifi,
                            contentDescription = "Remote",
                            tint = Color(0xFFFFA500)
                        )
                        Text(
                            text = "Remote",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

            }

            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Text(text = "New",fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "NewwAll")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(vm.jobs.take(10)) { job ->
                    JobItemGrid(job, navController,vm)
                }
            }
        }
    }
}

@Composable
fun AutoScrollingCarousel(jobs: List<Results>) {
    val carouselState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scrolling logic with looping
    LaunchedEffect(key1 = carouselState) {
        coroutineScope.launch {
            while (true) {
                delay(3000)
                val nextIndex = (carouselState.firstVisibleItemIndex + 1) % jobs.size
                if (nextIndex == 0 && carouselState.firstVisibleItemIndex == jobs.lastIndex) {

                    carouselState.scrollToItem(0)
                } else {
                    carouselState.animateScrollToItem(index = nextIndex)
                }
            }
        }
    }

    LazyRow(
        state = carouselState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(jobs.take(5)) { job ->
            CarouselItem(job = job)
        }
    }
}

@Composable
fun CarouselItem(job: Results) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Text(text = job.role ?: "Unknown Role", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun JobItemGrid(job: Results, navController: NavController, jobViewModel: JobViewModel) {
    // Determine if the job is a favorite
    val isFavorite = jobViewModel.isJobFavorite(job)

    Surface(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .clickable {
                job.id?.let { id ->
                    navController.navigate("detail/$id")
                }
            },
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp)) // Add shadow with elevation and shape
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp)
                ) // Ensure the background matches the shadow shape
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = job.role ?: "Unknown Role",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = job.location ?: "Unknown Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = job.companyName ?: "Unknown Company",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row {
                        Text(
                            text = "${job.employmentType ?: "Unknown Type"} | ",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = job.datePosted ?: "Unknown Date",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(verticalAlignment = Alignment.Bottom) {
                        Button(
                            onClick = { navController.navigate("detail/${job.id}") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFA500) // Dark orange color
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .width(80.dp)
                                .height(30.dp)
                        ) {
                            Text("Apply", fontSize = 10.sp)
                        }
                        IconButton(
                            onClick = {
                                if (isFavorite) {
                                    jobViewModel.removeJobFromFavorites(job)
                                } else {
                                    jobViewModel.addJobToFavorites(job)
                                }
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .align(Alignment.Bottom)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Red
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

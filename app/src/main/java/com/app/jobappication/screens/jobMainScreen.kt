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
import androidx.compose.material.icons.filled.NewReleases
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.jobappication.model.Results
import com.app.jobappication.viewmodel.JobViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobMainScreen(vm: JobViewModel = viewModel(), navController: NavController) {
    // Launch the job fetching on component load
    LaunchedEffect(Unit) { vm.fetchJobs() }
    Nav(navController) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
        JobBody(vm, navController)
        }
    }


}

@Composable
fun JobBody(vm: JobViewModel, navController: NavController) {
      if (vm.isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (vm.errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
            Text(text = vm.errorMessage, color = Color.Red)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
            AutoScrollingCarousel()
            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth()
                .clickable { navController.navigate("bycategory") },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Row {
                    Icon(Icons.Default.Category, contentDescription = "Category",tint = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "Category", fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp ,modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "CategoryAll",modifier = Modifier.clickable { navController.navigate("bycategory") },tint = MaterialTheme.colorScheme.onSurface)
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
                        .clickable { navController.navigate("bycategory") }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
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
                        .clickable { navController.navigate("bycategory") }

                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
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
                        .clickable { navController.navigate("bycategory") }

                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
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
                .fillMaxWidth()
                .clickable { navController.navigate("newJob") },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom){
                Row {
                    Icon(
                        Icons.Default.NewReleases,
                        contentDescription = "New",
                        tint = Color.Red
                    )
                    Text(
                        text = "New", fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,modifier = Modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "NewwAll",tint = MaterialTheme.colorScheme.onSurface,modifier = Modifier.clickable { navController.navigate("newJob") })
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            for (job in vm.jobs.take(10)) {
                    JobItemGrid(job, navController, vm)
                }
            }

        }
    }
}

@Composable
fun AutoScrollingCarousel() {
    val carouselState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val imageUrls = listOf(
        "https://cdn.vectorstock.com/i/1000x1000/59/22/work-at-home-banner-business-workspace-workplace-vector-25945922.webp",
        "https://media.istockphoto.com/id/1355871299/vector/great-job-promotion-work-appreciation-banner-megaphone-great-job-recruiting-sign-employee.jpg?s=612x612&w=0&k=20&c=6isXlDWmRNHf6-LE_A4agOwZ5CM6z7v9PaIiMjVJVsQ="
    )

    val infiniteImageUrls = listOf(imageUrls.last()) + imageUrls + listOf(imageUrls.first())

    LaunchedEffect(key1 = carouselState) {
        coroutineScope.launch {
            // Ensure the carousel starts in the middle to avoid showing extra items
            carouselState.scrollToItem(1)

            while (true) {
                delay(3000)
                val currentIndex = carouselState.firstVisibleItemIndex
                val nextIndex = if (currentIndex == infiniteImageUrls.size - 1) {
                    1 // Skip to the first item in the middle
                } else {
                    currentIndex + 1
                }
                carouselState.animateScrollToItem(nextIndex)
            }
        }
    }

    LazyRow(
        state = carouselState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(infiniteImageUrls.size) { index ->
            CarouselItem(url = infiniteImageUrls[index])
        }
    }
}

@Composable
fun CarouselItem(url: String) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = url,
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun JobItemGrid(job: Results, navController: NavController, jobViewModel: JobViewModel) {

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
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(24.dp)
                                .clickable {
                                    if (isFavorite) {
                                        jobViewModel.removeJobFromFavorites(job)
                                    } else {
                                        jobViewModel.addJobToFavorites(job)
                                    }
                                },
                            tint = Color.Red
                        )

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun JobAllScreen(vm: JobViewModel = viewModel(), navController: NavController) {
    // Launch the job fetching on component load
    LaunchedEffect(Unit) { vm.fetchJobs(search = vm.search, location = vm.locationSearch,employmentType = vm.employeeType) }
    Nav(navController) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ){
            JobAllBody(vm, navController)
        }
    }


}

@Composable
fun JobAllBody(vm: JobViewModel, navController: NavController) {

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

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment= Alignment.CenterVertically,
            ) {
                TextField(
                    value = vm.search ?: "",
                    onValueChange = { newValue ->
                        vm.search = newValue
                    },
                    placeholder = { Text(text = "Search Job") },

                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp) // Padding inside the TextField
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black, // Change cursor color to better match
                    ),
                    shape = RoundedCornerShape(40.dp) ,
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                )

                Button(
                    onClick = { vm.fetchJobs(search = vm.search) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF022E4D)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                ) {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )

                }

            }
            Row(modifier = Modifier
                .padding(9.dp)
                .fillMaxWidth(),

                verticalAlignment = Alignment.Bottom){
                Icon(Icons.Default.Work, contentDescription = "All",tint=Color(0xFF022E4D))
                Text(text = "All",fontWeight = FontWeight.Bold,
                    fontSize = 16.sp, modifier = Modifier.padding(horizontal = 8.dp),
                color=MaterialTheme.colorScheme.onSurface)

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


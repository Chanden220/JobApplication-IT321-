package com.app.jobappication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.jobappication.model.Results
import com.app.jobappication.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun jobMainScreen(vm: JobViewModel = viewModel(), navController: NavController) {
    // Launch the job fetching on component load
    LaunchedEffect(Unit) { vm.fetchJobs() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jobs", color = MaterialTheme.colorScheme.primary) },
                actions = {
                    IconButton(onClick = { vm.fetchJobs() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = { /* Handle sorting or other actions */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {  if (vm.page > 1) { vm.page -= 1; vm.fetchJobs() } },
                        enabled = vm.prevPageUrl != null
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous Page")
                    }
                    Text("${vm.page}", fontSize = 16.sp)
                    IconButton(
                        onClick = { vm.fetchNextPage() },
                        enabled = vm.nextPageUrl != null
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next Page")
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                jobBody(vm, navController)
            }
        }
    }
}

@Composable
fun jobBody(vm: JobViewModel, navController: NavController) {

    if (vm.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (vm.errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = vm.errorMessage, color = Color.Red)
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(vm.jobs.size) { index ->
                jobItemGrid(vm.jobs[index], navController)
            }
        }
    }
}

@Composable
fun jobItemGrid(job: Results, navController: NavController) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                job.id?.let { id ->
                    navController.navigate("detail/$id")
                }
            },
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = job.role ?: "Unknown Role",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = job.companyName ?: "Unknown Company",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

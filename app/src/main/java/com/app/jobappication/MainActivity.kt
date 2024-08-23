package com.app.jobappication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.jobappication.screens.jobMainScreen
import com.app.jobappication.ui.theme.JobAppicationTheme
import com.app.jobappication.viewmodel.JobViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobAppicationTheme {
                jobApp()
            }
        }
    }
}

@Composable
fun jobApp() {
    val navController = rememberNavController()
    val vm: JobViewModel = viewModel() // Use viewModel() to obtain an instance of JobViewModel

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable(
            "detail/{jobId}",
            arguments = listOf(navArgument("jobId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: return@composable
            JobDetailScreen(jobId = jobId, navController = navController)
        }
        composable("jobs") {
            jobMainScreen(vm, navController)
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(

        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome to the Job Application!",
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { navController.navigate("jobs") }) {
                    Text("View Jobs")
                }
            }
        }
    )
}

@Composable
fun JobDetailScreen(jobId: String?, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            // Fetch job details based on jobId from ViewModel and display here
            Text(
                text = "Job Details for Job ID: $jobId",
                modifier = Modifier.padding(16.dp)
            )
            // Add more content as needed
        }
    )
}

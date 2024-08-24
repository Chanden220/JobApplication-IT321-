package com.app.jobappication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.app.jobappication.screens.JobMainScreen
import com.app.jobappication.screens.MyScreen
import com.app.jobappication.ui.theme.JobAppicationTheme
import com.app.jobappication.viewmodel.JobViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobAppicationTheme {
               JobApp()
                //MyScreen()
            }
        }
    }
}

@Composable
fun JobApp() {
    val navController = rememberNavController()
    val vm: JobViewModel = viewModel() // Use viewModel() to obtain an instance of JobViewModel

    NavHost(navController = navController, startDestination = "jobs") {
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
            JobMainScreen(vm, navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        content = {
            AsyncImage(model = R.drawable.background_welcome,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color(0xFF86CDFF).copy(alpha = 0.1f), // 0%
                                0.06f to Color(0xFF7EC3F4).copy(alpha = 0.1f), // 6%
                                0.13f to Color(0xFF75B8E9).copy(alpha = 0.4f), // 13%
                                0.25f to Color(0xFF64A4D2).copy(alpha = 0.4f), // 25%
                                0.5f to Color(0xFF437CA5).copy(alpha = 0.8f),  // 50%
                                1.0f to Color(0xFF002C4C).copy(alpha = 1f)   // 100%
                            )
                        )
                    )
            )
            {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box {
                        Text(
                            text = "Job Finder",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFF022E4D), // Correct color setup
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Caught Your Chance!",
                            modifier = Modifier.padding(start = 110.dp,top=55.dp),
                            color = Color(0xFF022E4D), // Correct color setup
                            fontSize = 16.sp
                        )
                    }


                    Button(modifier = Modifier.padding(top=180.dp),onClick = { navController.navigate("jobs") }) {
                        Text("Explore >>")
                    }
                }
            }

        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

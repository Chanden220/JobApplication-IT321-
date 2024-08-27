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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.app.jobappication.screens.AboutUsScreen
import com.app.jobappication.screens.DetailScreen
import com.app.jobappication.screens.JobAllScreen
import com.app.jobappication.screens.JobCategoryScreen
import com.app.jobappication.screens.JobFavScreen
import com.app.jobappication.screens.JobMainScreen
import com.app.jobappication.screens.JobNewScreen
import com.app.jobappication.screens.Nav
import com.app.jobappication.screens.ProfileScreen

import com.app.jobappication.ui.theme.JobAppicationTheme
import com.app.jobappication.viewmodel.JobViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobAppicationTheme {
               JobApp()
            }
        }
    }
}

@Composable
fun JobApp() {
    val navController = rememberNavController()
    val vm: JobViewModel = viewModel()

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
        composable("bycategory"){
            JobCategoryScreen( vm, navController )
        }
        composable("newJob") {
            JobNewScreen(vm, navController )
        }
        composable("allJob") {
            JobAllScreen(vm, navController )
        }
        composable("aboutUs") {
            AboutUsScreen(navController )
        }
        composable("profile") {
            ProfileScreen(2 )
        }
        composable("favorite"){
            JobFavScreen(vm,navController)
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
fun JobDetailScreen(jobId: String?, vm: JobViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(Unit) { vm.fetchJobs(search = vm.search, location = vm.locationSearch,employmentType = vm.employeeType) }
    Nav(navController) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DetailScreen(
                imageUrl = "https://c4.wallpaperflare.com/wallpaper/685/180/1019/work-is-worship-wallpaper-preview.jpg",
                title = "UX/UI Developer",
                date = "ABC Company | August 24, 2024",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it.",
                responsible = "he point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters\n" +
                        "Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now ",
                requirement = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.\n" +
                        "he point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters",
                location = "ABC Company | Head Office",
                deadline = "30 August, 2024",
                contact = "Tel: (+855) 96 325 8741\n" +
                        "Email: abccompany@example.com"
            )
        }
    }
}


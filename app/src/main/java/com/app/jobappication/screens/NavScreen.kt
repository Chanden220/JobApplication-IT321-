package com.app.jobappication.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

//@Composable
//fun MyScreen() {
//    Nav() { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .background(Color(0xFFF1F1F1))
//        ) {
//            ProfileScreen(userId = 2)
////            DetailScreen(
////                imageUrl = "https://c4.wallpaperflare.com/wallpaper/685/180/1019/work-is-worship-wallpaper-preview.jpg",
////                title = "UX/UI Developer",
////                date = "ABC Company | August 24, 2024",
////                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it.",
////                responsible = "he point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters\n" +
////                        "Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now ",
////                requirement = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.\n" +
////                        "he point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters",
////                location = "ABC Company | Head Office",
////                deadline = "30 August, 2024",
////                contact = "Tel: (+855) 96 325 8741\n" +
////                        "Email: abccompany@example.com"
////            )
//        }
//    }
//}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Nav(navController: NavController,content: @Composable (PaddingValues) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(
                title = {
                    Text(
                        text = getGreeting(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        val iconSize = with(LocalDensity.current) { 30.sp.toDp() }
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier.size(iconSize),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    val iconSize = with(LocalDensity.current) { 30.sp.toDp() }
                    IconButton(onClick = { /* TODO: Handle notification click */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(iconSize),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { navController.navigate("aboutUs") }) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "About Us",
                            modifier = Modifier.size(iconSize),
                            tint = Color.White
                        )
                    }

                }
            )
        },
        drawerContent = {
            DrawerContent(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            content(innerPadding)
        }
    }
}

@Composable
fun CustomTopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF022E4D))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationIcon?.invoke()
            Spacer(modifier = Modifier.weight(1f))
            title()
            Spacer(modifier = Modifier.weight(1f))
            actions?.invoke()
        }
    }
}

@Composable
fun DrawerContent(navController: NavController) {
    Column {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.Gray, shape = RectangleShape)
                .padding(16.dp).background(MaterialTheme.colorScheme.surface)
        ) {
            Text("Username", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("allJob") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .then(Modifier.shadow(0.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text("All Jobs", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start,color = MaterialTheme.colorScheme.onSurface)

        }
        Button(
            onClick = { navController.navigate("favorite") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .then(Modifier.shadow(0.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text("Favorites", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start,color = MaterialTheme.colorScheme.onSurface)

        }

        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .then(Modifier.shadow(0.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text("Settings", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start,color = MaterialTheme.colorScheme.onSurface)
        }

        Button(
            onClick = { navController.navigate("profile") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .then(Modifier.shadow(0.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            contentPadding = PaddingValues(0.dp),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text("Profile", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start,color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

fun getGreeting(): String {
    val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    return when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}
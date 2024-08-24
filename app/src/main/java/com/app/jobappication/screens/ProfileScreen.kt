package com.app.jobappication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.app.jobappication.model.ApiResponse
import com.app.jobappication.model.User
import com.app.jobappication.services.RetrofitClient
import com.app.jobappication.util.IconButtonWithText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.app.jobappication.util.TextField

@Composable
fun ProfileScreen(userId: Int) {
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        RetrofitClient.instance.getUser(userId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    user = response.body()?.results?.firstOrNull()
                } else {
                    errorMessage = "Failed to load data"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                errorMessage = t.message
                isLoading = false
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.h6
                )
            }
            user != null -> {
                ProfileContent(user = user!!)
            }
        }
    }
}

@Composable
fun ProfileContent(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(user.picture.large),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "${user.name.first} ${user.name.last}", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
        Text(text = user.email, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Full Width Field: ID
        TextField(
            value = "${user.id.name} ${user.id.value}",
            modifier = Modifier.fillMaxWidth()
        )

        // Half Width Fields: Date of Birth and Registered Date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = formatDate(user.dob.date),
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = formatDate(user.registered.date),
                modifier = Modifier.weight(1f)
            )
        }

        // Half Width Fields: Phone and Cell
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = user.phone,
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = user.cell,
                modifier = Modifier.weight(1f)
            )
        }

        TextField(
            value = "${user.location.street.number} ${user.location.street.name}, ${user.location.city}, ${user.location.state}, ${user.location.country}, ${user.location.postcode}",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        IconButtonWithText(
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            text = "Apply",
            onClick = { /* Handle button click */ }
        )
    }
}

fun formatDate(dateString: String): String {
    return dateString.substring(0, 10) // Example: Extracts 'YYYY-MM-DD'
}
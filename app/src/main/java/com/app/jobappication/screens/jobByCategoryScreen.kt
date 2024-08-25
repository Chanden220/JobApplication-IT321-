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
fun JobCategoryScreen(vm: JobViewModel = viewModel(), navController: NavController) {
    // Launch the job fetching on component load
    LaunchedEffect(Unit) { vm.fetchJobs(search = vm.search, location = vm.locationSearch,employmentType = vm.employeeType) }
    Nav { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF1F1F1))
        ){
            JobCategoryBody(vm, navController)
        }
    }


}

@Composable
fun JobCategoryBody(vm: JobViewModel, navController: NavController) {
    var expandedCategory: Boolean by remember { mutableStateOf(false) }
    var expandedRemote: Boolean by remember { mutableStateOf(false) }
    var expandedLoactionSearch: Boolean by remember { mutableStateOf(false) }
    val employeeTypes = listOf("any", "full time", "contract")
    val remoteTypes = listOf("any", "yes", "no")
    val selectedRemoteType = vm.remote
    val selectedEmployeeType = vm.employeeType
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
                        .clickable { expandedLoactionSearch = !expandedLoactionSearch
                                    expandedCategory = false
                        expandedRemote=false}
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
                        .clickable { expandedCategory = !expandedCategory
                        expandedLoactionSearch=false
                        expandedRemote=false}
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
                        .clickable { expandedRemote = !expandedRemote
                                    expandedCategory = false
                        expandedLoactionSearch=false}
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
            if(expandedCategory){
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    employeeTypes.forEach { employeeType ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    vm.employeeType = employeeType
                                }
                        ) {

                            RadioButton(
                                selected = (employeeType == selectedEmployeeType),
                                onClick = {
                                    if(employeeType == "any"){
                                        vm.employeeType = ""
                                    }else{
                                        vm.employeeType = employeeType
                                    }

                                }
                            )

                            Text(
                                text = employeeType.capitalize(),
                                color = Color.White,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF022E4D),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
            if (expandedRemote) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    remoteTypes.forEach { remoteType ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    // Update vm.remote based on the selected remoteType
                                    vm.remote = when (remoteType) {
                                        "any" -> null
                                        "yes" -> true
                                        "no" -> false
                                        else -> vm.remote
                                    }
                                }
                        ) {
                            RadioButton(
                                selected = when (remoteType) {
                                    "any" -> vm.remote == null
                                    "yes" -> vm.remote == true
                                    "no" -> vm.remote == false
                                    else -> false
                                },
                                onClick = {
                                    // Update vm.remote based on the selected remoteType
                                    vm.remote = when (remoteType) {
                                        "any" -> null
                                        "yes" -> true
                                        "no" -> false
                                        else -> vm.remote
                                    }
                                }
                            )

                            Text(
                                text = remoteType.capitalize(),
                                color = Color.White,
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF022E4D),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
            if (expandedLoactionSearch) {
                TextField(
                    value = vm.locationSearch ?: "",
                    onValueChange = { newValue -> vm.locationSearch = newValue },  // Correctly update the search value
                    placeholder = { Text(text = "Location Search") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent, // Hide the underline when focused
                        unfocusedIndicatorColor = Color.Transparent, // Hide the underline when unfocused
                        cursorColor = Color.White, // Set the color of the cursor

                    ),
                    shape = RoundedCornerShape(40.dp),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                )
            }

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
                    trailingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
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
                    onClick = { vm.fetchJobs(search = vm.search, location = vm.locationSearch,employmentType = vm.employeeType, remote = vm.remote) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF022E4D)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                ) {
                    Text("Filter", fontSize = 13.sp)
                }

            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(vm.jobs.take(10)) { job ->
                    JobItemGrid(job, navController,vm)
                }
            }
        }
    }
}


package com.app.jobappication.screens


import android.text.Spanned
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.jobappication.util.IconButtonWithText
import com.app.jobappication.util.RoundIconButton
import com.app.jobappication.viewmodel.JobViewModel
import androidx.compose.ui.text.buildAnnotatedString
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.text.style.StrikethroughSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

fun htmlToAnnotatedString(html: String): AnnotatedString {
    val spanned: Spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    return buildAnnotatedString {
        append(spanned.toString())
        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            when (span) {
                is StyleSpan -> {
                    when (span.style) {
                        android.graphics.Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold), start, end
                        )
                        android.graphics.Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic), start, end
                        )
                    }
                }
                is AbsoluteSizeSpan -> {
                    addStyle(
                        SpanStyle(fontSize = span.size.sp), start, end
                    )
                }
                is ForegroundColorSpan -> {
                    addStyle(
                        SpanStyle(color = Color(span.foregroundColor)), start, end
                    )
                }
                is UnderlineSpan -> {
                    addStyle(
                        SpanStyle(textDecoration = TextDecoration.Underline), start, end
                    )
                }
                is StrikethroughSpan -> {
                    addStyle(
                        SpanStyle(textDecoration = TextDecoration.LineThrough), start, end
                    )
                }
                is TypefaceSpan -> {
                    addStyle(
                        SpanStyle(fontFamily = FontFamily.Monospace), start, end
                    )
                }

            }
        }
    }
}

@Composable
fun DetailScreen(vm: JobViewModel, navController: NavController) {
    val annotatedString = htmlToAnnotatedString(vm.selectedJob.text ?: "Unknown Descriptions")
    Nav(navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface), // Set background color
            horizontalAlignment = Alignment.Start
        ) {
            // Display Job Logo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(vm.selectedJob.logo)
                    .crossfade(true)
                    .build(),
                contentDescription = "Job Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            // Display Job Role
            Text(
                text = vm.selectedJob.role ?: "Unknown Role",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface // Set text color
            )

            // Display Company Name
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = vm.selectedJob.companyName ?: "Unknown Company",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface // Set text color
            )

            // Display Date Posted
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = vm.selectedJob.datePosted ?: "Unknown Date",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Adjusted opacity for a lighter text color
                textAlign = TextAlign.Start
            )

            // Display Job Description
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface // Set text color
            )

            // Display Job Location
            if (!vm.selectedJob.location.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Location",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface // Set text color
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = vm.selectedJob.location ?: "Unknown Location",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface // Set text color
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                val isFavorite = vm.isJobFavorite(vm.selectedJob)

                RoundIconButton(
                    icon = Icons.Filled.Favorite.takeIf { isFavorite } ?: Icons.Outlined.FavoriteBorder,
                    onClick = {
                        if (isFavorite) {
                            vm.removeJobFromFavorites(vm.selectedJob)
                        } else {
                            vm.addJobToFavorites(vm.selectedJob)
                        }
                    }
                )

                IconButtonWithText(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    text = "Apply",
                    onClick = {
                        // Handle apply action
                    }
                )
            }
        }
    }
}

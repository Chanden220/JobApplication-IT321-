package com.app.jobappication.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextField(
    value: String,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 6.dp // Default vertical padding
) {
    androidx.compose.material.TextField(
        value = value,
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
            .background(Color.White, shape = RoundedCornerShape(5.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = Color.Black,
            disabledTextColor = Color.Black
        ),
        shape = RoundedCornerShape(5.dp),
        enabled = false,
        textStyle = TextStyle(fontSize = 16.sp)
    )
}
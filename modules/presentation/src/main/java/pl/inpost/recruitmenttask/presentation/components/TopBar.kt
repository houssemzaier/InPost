package pl.inpost.recruitmenttask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Text(
            text = "InPost",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.25.sp,
                lineHeight = 1.25.sp,
            ),
            modifier = Modifier.padding(16.dp),
        )
    }
}

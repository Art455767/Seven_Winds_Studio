package com.example.sevenwindsstudio

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTheme {
    val PrimaryBrown = Color(0xFF846340)
    val DarkBrown = Color(0xFF342D1A)
    val LightCream = Color(0xFFF6E5D1)
    val MediumBrown = Color(0xFFAF9479)
    val Background = Color(0xFFFAF9F9)
    val DividerGray = Color(0xFFC2C2C2)

    val Typography = Typography(
        h1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = PrimaryBrown
        ),
        h2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = PrimaryBrown
        ),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = PrimaryBrown
        ),
        subtitle1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MediumBrown
        ),
        button = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = LightCream
        )
    )
}

@Composable
fun CoffeeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = AppTheme.PrimaryBrown,
            primaryVariant = AppTheme.DarkBrown,
            secondary = AppTheme.LightCream,
            background = AppTheme.Background,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = AppTheme.PrimaryBrown,
            onBackground = AppTheme.PrimaryBrown,
            onSurface = AppTheme.PrimaryBrown
        ),
        typography = AppTheme.Typography,
        content = content
    )
}
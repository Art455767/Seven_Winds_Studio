package com.example.sevenwindsstudio

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {
    // Цвета из Figma
    val Primary = Color(0xFF846340)       // Основной коричневый
    val Dark = Color(0xFF342D1A)          // Темный акцент
    val Light = Color(0xFFF6E5D1)         // Светло-кремовый
    val Medium = Color(0xFFAF9479)        // Средний коричневый
    val Background = Color(0xFFFAF9F9)    // Основной фон
    val Divider = Color(0xFFC2C2C2)       // Разделители
    val Error = Color(0xFFD32F2F)         // Цвет ошибки
    val SecondaryText = Color(0xFF757575)

    // Типографика из Figma
    val Typography = Typography(
        h4 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Primary
        ),
        h5 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Primary
        ),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Primary
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Medium
        ),
        button = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Light
        ),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Medium
        )
    )
}

@Composable
fun CoffeeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = AppTheme.Primary,
            primaryVariant = AppTheme.Dark,
            secondary = AppTheme.Light,
            background = AppTheme.Background,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = AppTheme.Primary,
            error = AppTheme.Error
        ),
        typography = AppTheme.Typography,
        shapes = Shapes(
            small = RoundedCornerShape(5.dp),
            medium = RoundedCornerShape(24.5.dp),
            large = RoundedCornerShape(0.dp)
        ),
        content = content
    )
}
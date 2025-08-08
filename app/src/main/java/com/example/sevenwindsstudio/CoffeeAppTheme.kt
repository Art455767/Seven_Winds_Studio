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
    val Primary = Color(0xFF846340)       // Основной коричневый
    val Dark = Color(0xFF342D1A)          // Темный акцент
    val Light = Color(0xFFF6E5D1)         // Светло-кремовый
    val Medium = Color(0xFFAF9479)        // Средний коричневый
    val Background = Color(0xFFFAF9F9)    // Основной фон
    val BackgroundElevated = Color(0xFFFFFFFF) // Карточки и поверхности
    val Divider = Color(0xFFC2C2C2)       // Разделители
    val Error = Color(0xFFD32F2F)         // Цвет ошибки
    val TextPrimary = Color(0xFF212121)   // Основной текст
    val TextSecondary = Color(0xFF757575) // Вторичный текст

    // Формы
    val Shapes = Shapes(
        small = RoundedCornerShape(5.dp),
        medium = RoundedCornerShape(24.5.dp),
        large = RoundedCornerShape(0.dp)
    )

    // Типографика
    val Typography = Typography(
        h4 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        h5 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        h6 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        subtitle1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        subtitle2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            color = TextPrimary
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp,
            color = TextSecondary
        ),
        button = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            color = Light
        ),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
            color = Medium
        )
    )

    // Цветовая схема Material
    val colors = lightColors(
        primary = Primary,
        primaryVariant = Dark,
        secondary = Light,
        background = Background,
        surface = BackgroundElevated,
        onPrimary = Color.White,
        onSecondary = Primary,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        error = Error
    )
}

@Composable
fun CoffeeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = AppTheme.colors,
        typography = AppTheme.Typography,
        shapes = AppTheme.Shapes,
        content = content
    )
}
package com.example.sevenwindsstudio.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoffeeCup(
    modifier: Modifier = Modifier,
    cupColor: Color = Color(0xFF342D1A),
    borderColor: Color = Color(0xFFF6E5D1),
    borderWidth: Dp = 2.dp
) {
    Canvas(
        modifier = modifier.size(48.dp) // Уменьшил размер для маркеров на карте
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2.5f // Уменьшенный радиус для маркера

        // Упрощенная версия стаканчика для маркера
        // Основание (круг)
        drawCircle(
            color = cupColor,
            radius = radius,
            center = Offset(centerX, centerY)
        )

        // Корпус стакана (трапеция)
        val cupWidth = radius * 1.6f
        val cupHeight = radius * 1.2f
        drawRect(
            color = cupColor,
            topLeft = Offset(centerX - cupWidth/2, centerY - cupHeight),
            size = androidx.compose.ui.geometry.Size(cupWidth, cupHeight)
        )

        // Обводка
        val strokeWidth = borderWidth.toPx()
        val strokeStyle = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // Обводка основания
        drawCircle(
            color = borderColor,
            radius = radius - strokeWidth/2,
            center = Offset(centerX, centerY),
            style = strokeStyle
        )

        // Обводка корпуса
        drawRect(
            color = borderColor,
            topLeft = Offset(centerX - cupWidth/2, centerY - cupHeight),
            size = androidx.compose.ui.geometry.Size(cupWidth, cupHeight),
            style = strokeStyle
        )
    }
}
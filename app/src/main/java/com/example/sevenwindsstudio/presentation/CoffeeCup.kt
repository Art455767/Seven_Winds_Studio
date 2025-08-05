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
        modifier = modifier.size(58.dp)
    ) {
        // Основной круг (дно стакана)
        drawCircle(
            color = cupColor,
            radius = size.minDimension / 2,
            center = Offset(size.width / 2, size.height / 2)
        )

        // Тень под стаканом
        drawCircle(
            color = Color.Black.copy(alpha = 0.25f),
            radius = size.minDimension / 2,
            center = Offset(size.width / 2, size.height / 2 + 4f)
        )

        // Верхняя часть стакана (прямоугольник)
        drawRect(
            color = cupColor,
            topLeft = Offset(66.5f.toDp().toPx() - 45f.toDp().toPx(), 269.25f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(15f.toDp().toPx(), 3.75f.toDp().toPx())
        )

        // Средняя часть стакана (прямоугольник)
        drawRect(
            color = cupColor,
            topLeft = Offset(65.25f.toDp().toPx() - 45f.toDp().toPx(), 273f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(17.5f.toDp().toPx(), 3.75f.toDp().toPx())
        )

        // Основная часть стакана (прямоугольник)
        drawRect(
            color = cupColor,
            topLeft = Offset(67.13f.toDp().toPx() - 45f.toDp().toPx(), 276.75f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(13.75f.toDp().toPx(), 12.5f.toDp().toPx())
        )

        // Обводки для всех элементов
        val strokeWidth = borderWidth.toPx()
        val strokeStyle = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // Обводка верхней части
        drawRect(
            color = borderColor,
            topLeft = Offset(66.5f.toDp().toPx() - 45f.toDp().toPx(), 269.25f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(15f.toDp().toPx(), 3.75f.toDp().toPx()),
            style = strokeStyle
        )

        // Обводка средней части
        drawRect(
            color = borderColor,
            topLeft = Offset(65.25f.toDp().toPx() - 45f.toDp().toPx(), 273f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(17.5f.toDp().toPx(), 3.75f.toDp().toPx()),
            style = strokeStyle
        )

        // Обводка основной части
        drawRect(
            color = borderColor,
            topLeft = Offset(67.13f.toDp().toPx() - 45f.toDp().toPx(), 276.75f.toDp().toPx() - 249f.toDp().toPx()),
            size = androidx.compose.ui.geometry.Size(13.75f.toDp().toPx(), 12.5f.toDp().toPx()),
            style = strokeStyle
        )

        // Обводка круга (дна)
        drawCircle(
            color = borderColor,
            radius = size.minDimension / 2 - strokeWidth / 2,
            center = Offset(size.width / 2, size.height / 2),
            style = strokeStyle
        )
    }
}
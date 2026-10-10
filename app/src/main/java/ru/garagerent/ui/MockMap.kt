package ru.garagerent.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.garagerent.R
import ru.garagerent.domain.Garage
import ru.garagerent.ui.theme.Block
import ru.garagerent.ui.theme.Ink
import ru.garagerent.ui.theme.Paper
import ru.garagerent.ui.theme.Pin
import ru.garagerent.ui.theme.Sheet

@Composable
fun MockMap(
    garages: List<Garage>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier) {
        Canvas(Modifier.fillMaxSize()) {
            drawRect(Paper)
            val road = 16.dp.toPx()
            val cols = 3
            val rows = 5
            val cellW = size.width / cols
            val cellH = size.height / rows
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    drawRoundRect(
                        color = Block,
                        topLeft = Offset(col * cellW + road / 2, row * cellH + road / 2),
                        size = Size(cellW - road, cellH - road),
                        cornerRadius = CornerRadius(14.dp.toPx()),
                    )
                }
            }
            drawRect(Color.White, topLeft = Offset(0f, size.height * 0.28f), size = Size(size.width, road))
            drawRect(Color.White, topLeft = Offset(0f, size.height * 0.62f), size = Size(size.width, road))
            drawRect(Color.White, topLeft = Offset(size.width * 0.34f, 0f), size = Size(road, size.height))
            drawRect(Color.White, topLeft = Offset(size.width * 0.68f, 0f), size = Size(road, size.height))
        }
        garages.forEach { garage ->
            val x = (maxWidth * garage.pinX - 32.dp).coerceIn(8.dp, (maxWidth - 76.dp).coerceAtLeast(8.dp))
            val y = (maxHeight * garage.pinY - 20.dp).coerceIn(8.dp, (maxHeight - 52.dp).coerceAtLeast(8.dp))
            PricePin(
                text = stringResource(R.string.price_rub, garage.pricePerHour),
                modifier = Modifier.offset(x, y),
            )
        }
    }
}

@Composable
private fun PricePin(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            color = Sheet,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .background(Pin, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
        Canvas(Modifier.size(10.dp, 6.dp)) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width / 2f, size.height)
                close()
            }
            drawPath(path, Pin)
        }
    }
}

@Composable
fun FilterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.filters),
        color = Ink,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(Sheet, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
    )
}

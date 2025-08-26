import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daricx.ui.visualizations.Gauge
import com.daricx.ui.visualizations.HorizontalGauge


@Composable
fun MarketStatsRow(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 0.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                modifier = Modifier.weight(1f),
                title = "Market Cap",
                value = "$4.02T",
                change = "▲ 1.46%",
                changeColor = Color(0xFF4CAF50)
            )
            VerticalDivider()

            StatItem(
                modifier = Modifier.weight(1f),
                title = "CMC100",
                value = "$249.72",
                change = "▲ 1.74%",
                changeColor = Color(0xFF4CAF50)
            )
            VerticalDivider()

            AltcoinIndexGauge(modifier = Modifier.weight(1f))
            VerticalDivider()

            FearAndGreedGauge(modifier = Modifier.weight(1f))
        }
    }

}


@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    change: String,
    changeColor: Color
) {
    Column(modifier = modifier.fillMaxHeight().padding(horizontal = 5.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = Color.Gray,

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 13.sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = change,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
            color = changeColor,
        )
    }
}

/**
 * Placeholder برای کامپوننت چارت Altcoin Index شما.
 */
@Composable
fun AltcoinIndexGauge(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight().padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Altcoin Index",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(Modifier.fillMaxHeight()){
            HorizontalGauge(
                modifier = Modifier.fillMaxWidth(),
                value = 70,
            )
        }
    }
}


@Composable
fun FearAndGreedGauge(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight().padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Fear & Greed",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = modifier
                //.aspectRatio(1.8f) // ratio similar to the provided image
                .padding(0.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center){
                Gauge(
                    modifier = Modifier.fillMaxSize(),
                    value = 77,
                )
            }
        }
    }
}

/**
 * یک جداکننده عمودی ساده.
 */
@Composable
private fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}

// --- Preview ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MarketStatsRowPreview() {
    // استفاده از Surface برای داشتن یک پس‌زمینه واقعی‌تر در پیش‌نمایش
    Box(Modifier.padding(horizontal = 25.dp, vertical = 20.dp)) {
        MarketStatsRow()
    }
}

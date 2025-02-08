import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme

@Composable
fun InfoItems(details: List<Pair<String, String>>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        details.forEachIndexed { index, (label, value) ->
            ItemRow(label = label, value = value)
            if (index != details.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ItemRow(
    label: String,
    value: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            modifier = Modifier.weight(2f, fill = false),
            text = value,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLaunchDetails() {
    val items = listOf(
        "Name" to "Space Launch Complex 40 Space Launch Complex 40 Space Launch",
        "Location" to "Cape Canaveral, FL, USA",
        "Total Launches" to "162",
    )
    SpacehubTheme {
        InfoItems(details = items)
    }
}

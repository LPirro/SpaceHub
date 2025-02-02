import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
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
    label: String, value: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLaunchDetails() {

    val items = listOf(
        "Name" to "Space Launch Complex 40",
        "Location" to "Cape Canaveral, FL, USA",
        "Total Launches" to "162",
    )
    SpacehubTheme {
        InfoItems(details = items)
    }
}

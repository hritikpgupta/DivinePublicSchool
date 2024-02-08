package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SessionChipSection(it: List<String>, modifier: Modifier, onItemSelected: (String) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        var selectedItem by remember { mutableStateOf(it[0]) }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(
                horizontal = 20.dp, vertical = 15.dp
            )
        ) {
            items(it) { cardName ->
                SessionChip(
                    color = (if (cardName == selectedItem) Color.Green.copy(
                        0.1f
                    ) else Color.LightGray.copy(0.3f)),
                    name = cardName
                ) { id ->
                    selectedItem = id
                    onItemSelected(id)
                }
            }
        }
    }

}


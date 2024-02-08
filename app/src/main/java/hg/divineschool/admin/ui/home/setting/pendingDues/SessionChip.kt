package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.regularFont


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SessionChip(color: Color, name: String, onClick: (docId: String) -> Unit) {
    Card(
        backgroundColor = color.copy(0.5f),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        onClick = { onClick(name) },
        modifier = Modifier
            .requiredWidth(160.dp)
            .requiredHeight(40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text(
                text = name, fontSize = 20.sp, style = TextStyle(
                    fontFamily = regularFont, fontWeight = FontWeight.Bold
                ), maxLines = 1, softWrap = true, overflow = TextOverflow.Ellipsis
            )
        }

    }
}
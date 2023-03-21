package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatisticCard() {
    Card( backgroundColor = Color.LightGray.copy(0.7f),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(300.dp).padding(6.dp)
    ) {

    }
}
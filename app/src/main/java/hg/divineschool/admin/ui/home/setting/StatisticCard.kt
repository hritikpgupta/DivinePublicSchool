package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun StatisticCard() {
    Card( backgroundColor = Color.LightGray.copy(0.7f),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(280.dp).padding(6.dp)
    ) {


    }
}
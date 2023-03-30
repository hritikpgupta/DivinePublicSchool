package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun StatisticCard() {
    Card(
        backgroundColor = Color.LightGray.copy(0.2f),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(280.dp)
            .padding(6.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.8f)
                    .background(color = Color.Red.copy(0.3f))
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 4.dp)
                    .weight(0.2f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.open),
                        contentDescription = "Open",
                        modifier = Modifier.requiredSize(40.dp)
                    )
                    Spacer(modifier = Modifier.requiredWidth(4.dp))
                    Text(text = "7:00 am")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = hg.divineschool.admin.R.drawable.close),
                        contentDescription = "Close",
                        modifier = Modifier.requiredSize(40.dp)
                    )
                    Spacer(modifier = Modifier.requiredWidth(4.dp))
                    Text(text = "3:00 pm")
                }

            }
        }

    }
}
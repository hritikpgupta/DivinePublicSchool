package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
fun BookCard(name: String, price: String, onCardClick : (bookName : String, bookPrice : String) -> Unit) {
    Card(
        backgroundColor = Color.LightGray.copy(0.4f),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        onClick = {onCardClick(name,price)} ,
        border = BorderStroke(4.dp, Color.Black.copy(0.4f)),
        modifier = Modifier
            .requiredWidth(280.dp)
            .requiredHeight(80.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text(
                text = name,
                fontSize = 28.sp,
                style = TextStyle(
                    fontFamily = regularFont,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = price,
                fontSize = 26.sp,
                style = TextStyle(
                    fontFamily = regularFont,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun Title(invoiceNumber: String) {

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = invoiceNumber,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .wrapContentWidth(),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = regularFont,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                    .requiredSize(95.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "\"We make your child an achiever\"",
                color = Color.Black,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = regularFont,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(top = 0.dp)
            )
        }

    }
}
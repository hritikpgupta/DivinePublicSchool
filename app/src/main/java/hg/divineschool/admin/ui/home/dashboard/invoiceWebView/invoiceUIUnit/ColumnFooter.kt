package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.ui.theme.boldFont

@Composable
fun ColumnFooter(bookDetail: String, supplementDetail: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.requiredHeight(40.dp))
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, color = Color.Black.copy(0.6f))
                .padding(4.dp)

        ) {
            Text(
                text = "Book Details : $bookDetail",
                maxLines = 3,
                softWrap = true,
                overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.requiredHeight(4.dp))
            Text(
                text = "Supplement Details : $supplementDetail",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            thickness = 1.5.dp,
            color = Color.Black,
            modifier = Modifier
                .requiredWidth(200.dp)
                .padding(top = 60.dp)
        )
        Text(
            text = "Principal Signature",
            style = TextStyle(
                fontFamily = boldFont,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
        )
        Spacer(modifier = Modifier.requiredHeight(30.dp))

    }

}
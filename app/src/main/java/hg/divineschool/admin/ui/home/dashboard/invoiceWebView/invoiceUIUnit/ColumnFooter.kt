package hg.divineschool.admin.ui.home.dashboard.invoiceWebView.invoiceUIUnit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import hg.divineschool.admin.ui.theme.regularFont

@Composable
fun ColumnFooter(bookDetail: String, supplementDetail: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(0.5.dp, color = Color.Black.copy(0.8F))
        ) {
            Text(text = bookDetail, maxLines = 3, softWrap = true, overflow = TextOverflow.Clip)
            Spacer(modifier = Modifier.requiredHeight(4.dp))
            Text(
                text = supplementDetail,
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            thickness = 3.dp,
            color = Color.Black,
            modifier = Modifier
                .requiredWidth(200.dp)
                .padding(top = 140.dp)
        )
        Text(
            text = "Principal Signature",
            style = TextStyle(
                fontFamily = regularFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(top = 5.dp)
        )

    }

}
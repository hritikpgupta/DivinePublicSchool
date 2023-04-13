package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.data.models.Transaction
import hg.divineschool.admin.ui.theme.mediumFont
import hg.divineschool.admin.ui.theme.regularFont
import hg.divineschool.admin.ui.utils.INR
import hg.divineschool.admin.ui.utils.getFormattedValue


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionCard(transaction: Transaction, onCardClick: (obj: Transaction) -> Unit) {

    Card(
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.background,
        onClick = {
            onCardClick(transaction)
        },
        modifier = Modifier.requiredSize(280.dp, 150.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = transaction.invoiceNumber,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = regularFont
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray.copy(0.5f))
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.requiredHeight(4.dp))
            Text(
                text = transaction.studentName, style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = mediumFont
                ), modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = transaction.className, style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = mediumFont
                ), modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = "$INR ${transaction.amount}", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = mediumFont
                ), modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = transaction.timestamp.toDate().getFormattedValue(),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = mediumFont
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(color = Color.LightGray.copy(0.5f))

            )
        }
    }

}
package hg.divineschool.admin.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hg.divineschool.admin.R
import hg.divineschool.admin.ui.theme.boldFont

@Composable
fun DPSAppBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "App Bar")
        }
    }
}

@Composable
fun DPSBar(onBackPressed: () -> Unit, className: String) {
    TopAppBar(
        elevation = 4.dp,
        title = {
            Text(
                text = className, style = TextStyle(
                    fontFamily = boldFont,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Filled.ArrowBack, null, modifier = Modifier.requiredSize(28.dp)
                )
            }
        }
    )
}

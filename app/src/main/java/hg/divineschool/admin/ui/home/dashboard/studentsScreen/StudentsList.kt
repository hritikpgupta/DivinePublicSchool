package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentsList(classID: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
    ) {
        Text(text = classID, style = TextStyle(fontSize = 18.sp))
    }

}
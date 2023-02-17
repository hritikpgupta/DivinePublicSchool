package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hg.divineschool.admin.data.models.Student

@Composable
fun StudentCard(student: Student) {
    Card(
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.requiredSize(280.dp, 170.dp),

    ) {
        Text(text = student.religion)
    }
}
package hg.divineschool.admin.ui.home.dashboard.registerStudent

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.utils.customGetSerializable

@Composable
fun UpdateStudent(
    classID: String,
    className: String,
    navController: NavController,
    viewModel: RegisterStudentViewModel
){
    var currentStudent = navController.previousBackStackEntry?.arguments?.customGetSerializable<Student>("studentObj")

}
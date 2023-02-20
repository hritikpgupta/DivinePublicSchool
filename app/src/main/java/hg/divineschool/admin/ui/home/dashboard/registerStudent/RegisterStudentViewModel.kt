package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.RegisterStudentRepository
import hg.divineschool.admin.data.models.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterStudentViewModel @Inject constructor(
    private val repository: RegisterStudentRepository
) : ViewModel() {
    private val _registerStudentFlow = MutableStateFlow<Resource<Student>?>(null)
    val registerStudentFlow: StateFlow<Resource<Student>?> = _registerStudentFlow

    fun registerStudent(student: Student, classId: String, className: String, fileUriString: String) = viewModelScope.launch {
        _registerStudentFlow.value = Resource.Loading
        val result = repository.uploadProfileImage(student, classId,className, fileUriString)
        _registerStudentFlow.value = result
    }

}
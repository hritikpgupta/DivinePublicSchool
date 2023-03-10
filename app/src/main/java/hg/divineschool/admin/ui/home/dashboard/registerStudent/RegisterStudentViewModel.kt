package hg.divineschool.admin.ui.home.dashboard.registerStudent

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.RegisterStudentRepository
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.utils.Log_Tag
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

    private val _updateStudentFlow = MutableStateFlow<Resource<Student>?>(null)
    val updateStudentFlow: StateFlow<Resource<Student>?> = _updateStudentFlow

    fun registerStudent(student: Student, classId: String, className: String, fileUriString: String) = viewModelScope.launch {
        _registerStudentFlow.value = Resource.Loading
        val result = repository.uploadProfileImage(student, classId,className, fileUriString)
        _registerStudentFlow.value = result
    }

    fun updateStudent(student: Student, classId: String, className: String, fileUriString: String) = viewModelScope.launch {
        _updateStudentFlow.value = Resource.Loading
        val result = repository.updateProfileImage(student, classId,className, fileUriString)
        _updateStudentFlow.value = result
    }

}
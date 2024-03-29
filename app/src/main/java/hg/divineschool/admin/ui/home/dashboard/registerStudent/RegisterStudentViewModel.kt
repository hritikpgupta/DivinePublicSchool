package hg.divineschool.admin.ui.home.dashboard.registerStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.RegisterStudentRepository
import hg.divineschool.admin.data.models.Student
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterStudentViewModel @Inject constructor(
    private val repository: RegisterStudentRepository,
    private val ioDispatcher: CoroutineDispatcher

) : ViewModel() {
    private val _registerStudentFlow = MutableStateFlow<Resource<Student>?>(null)
    val registerStudentFlow: StateFlow<Resource<Student>?> = _registerStudentFlow

    private val _updateStudentFlow = MutableStateFlow<Resource<Student>?>(null)
    val updateStudentFlow: StateFlow<Resource<Student>?> = _updateStudentFlow

    fun registerStudent(
        student: Student,
        classId: String,
        className: String,
        fileUriString: String
    ) = viewModelScope.launch {
        _registerStudentFlow.value = Resource.Loading
        _registerStudentFlow.value = withContext(ioDispatcher) {
            repository.uploadProfileImage(student, classId, className, fileUriString)
        }
    }

    fun updateStudent(student: Student, classId: String, className: String, fileUriString: String) =
        viewModelScope.launch {
            _updateStudentFlow.value = Resource.Loading
            _updateStudentFlow.value = withContext(ioDispatcher) {
                repository.updateProfileImage(student, classId, className, fileUriString)
            }
        }

}
package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.StudentListRepository
import hg.divineschool.admin.data.models.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentListViewModel @Inject constructor(
    private val repository: StudentListRepository
) : ViewModel() {

    private val _studentListFlow = MutableStateFlow<Resource<List<Student>>?>(null)
    val studentListFlow: StateFlow<Resource<List<Student>>?> = _studentListFlow

     fun getAllStudents(id: Long) = viewModelScope.launch {
        _studentListFlow.value = Resource.Loading
        val result = repository.getStudentList(id);
        _studentListFlow.value = result
    }

}
package hg.divineschool.admin.ui.home.dashboard.studentsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.StudentListRepository
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.ui.utils.Log_Tag
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentListViewModel @Inject constructor(
    private val repository: StudentListRepository
) : ViewModel() {

    private var _studentListFlow = MutableStateFlow<Resource<List<Student>>?>(null)
    val studentListFlow: StateFlow<Resource<List<Student>>?> = _studentListFlow


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var studentList : List<Student> = emptyList()

    private var _students = MutableStateFlow(studentList)



    @OptIn(FlowPreview::class)
    val students = searchText.debounce(50L).combine(_students){ text, students ->
        if (text.isBlank()){
            students
        }else{
            students.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),_students.value)

    fun onSearchTextChange(text:String){
        _searchText.value = text
    }

    fun onClearSearchText(){
        _searchText.value =""
    }


     fun getAllStudents(id: Long) = viewModelScope.launch {
        _studentListFlow.value = Resource.Loading
        val result = repository.getStudentList(id)
         _students.value = repository.students!!
        _studentListFlow.value = result
    }

}
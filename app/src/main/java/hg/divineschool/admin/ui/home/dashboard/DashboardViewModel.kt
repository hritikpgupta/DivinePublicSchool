package hg.divineschool.admin.ui.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.DashboardRepository
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.ui.utils.convertIdToPath
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _classListFlow = MutableStateFlow<Resource<List<ClassInformation>>?>(null)
    val classListFlow: StateFlow<Resource<List<ClassInformation>>?> = _classListFlow

    private val _nameUpdateFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val nameUpdateFlow: StateFlow<Resource<Boolean>?> = _nameUpdateFlow

    init {
        getAllClasses()
        getFeeStructure()
    }

    fun getAllClasses() = viewModelScope.launch {
        _classListFlow.value = Resource.Loading
        val result = repository.getAllClasses()
        _classListFlow.value = result
    }
    private fun getFeeStructure() = viewModelScope.launch {
        repository.getFeeStructure()
        repository.getSchoolInformation()
    }
    fun updateClassTeacherName(classID : Long, name : String) = viewModelScope.launch {
        _nameUpdateFlow.value = Resource.Loading
        _nameUpdateFlow.value = repository.updateClassTeacher(classID.convertIdToPath(),name)
    }
}
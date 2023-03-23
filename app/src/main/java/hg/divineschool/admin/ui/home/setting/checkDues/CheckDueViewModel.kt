package hg.divineschool.admin.ui.home.setting.checkDues

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.StudentDue
import hg.divineschool.admin.ui.utils.Log_Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckDueViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {

    private var _studentListFlow = MutableStateFlow<Resource<List<StudentDue>>?>(null)
    val studentListFlow: StateFlow<Resource<List<StudentDue>>?> = _studentListFlow
    fun getAllDueStudents(className: String, monthName: String) = viewModelScope.launch {
        _studentListFlow.value = Resource.Loading
        _studentListFlow.value = repository.getAllStudentsDue(className, monthName)
    }

}
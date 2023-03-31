package hg.divineschool.admin.ui.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {

    private var _studentCountFlow = MutableStateFlow<Resource<Triple<Int, Int, Int>>?>(null)
    val studentCountFlow: StateFlow<Resource<Triple<Int, Int, Int>>?> = _studentCountFlow

    fun updateSchoolOpenState(b: Boolean) = viewModelScope.launch {
        repository.updateSchoolOpenState(b)
    }

    fun updateStartTime(time: String) = viewModelScope.launch {
        repository.updateSchoolOpenTime(time)
    }

    fun updateEndTime(time: String) = viewModelScope.launch {
        repository.updateSchoolCloseTime(time)
    }

    fun getStudentsCount() = viewModelScope.launch {
        _studentCountFlow.value = Resource.Loading
        _studentCountFlow.value = repository.getStudentsCount()
    }

}
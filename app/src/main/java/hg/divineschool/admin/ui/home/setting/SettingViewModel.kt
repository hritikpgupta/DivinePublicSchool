package hg.divineschool.admin.ui.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: SettingRepository,
    private val ioDispatcher: CoroutineDispatcher

) : ViewModel() {

    private var _studentCountFlow = MutableStateFlow<Resource<Triple<Int, Int, Int>>?>(null)
    val studentCountFlow: StateFlow<Resource<Triple<Int, Int, Int>>?> = _studentCountFlow

    fun updateSchoolOpenState(b: Boolean) = viewModelScope.launch {
        withContext(ioDispatcher) {
            repository.updateSchoolOpenState(b)
        }
    }

    fun updateStartTime(time: String) = viewModelScope.launch {
        withContext(ioDispatcher) {
            repository.updateSchoolOpenTime(time)
        }
    }

    fun updateEndTime(time: String) = viewModelScope.launch {
        withContext(ioDispatcher) {
            repository.updateSchoolCloseTime(time)
        }
    }

    fun getStudentsCount() = viewModelScope.launch {
        _studentCountFlow.value = Resource.FailureMessage("Not implemented yet")
        //_studentCountFlow.value = repository.getStudentsCount()
    }

}
package hg.divineschool.admin.ui.home.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.MigrationEvent
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {

    private val _adminSettingFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val adminSettingFlow: StateFlow<Resource<Boolean>?> = _adminSettingFlow

    private val _logFlow = MutableStateFlow<String?>(null)
    val logFlow: StateFlow<String?> = _logFlow

    init {
        migrateClassEightUser()
    }


    private fun migrateClassEightUser() = viewModelScope.launch {
        _adminSettingFlow.value = Resource.Loading
        _adminSettingFlow.value = repository.migrateClassEightUser()
        getMigrationLogs()
    }

    private fun getMigrationLogs() = viewModelScope.launch {
        repository.migrationEvent?.collect { event ->
            when (event) {
                is MigrationEvent.SendLog -> {
                    _logFlow.value = event.msg
                }
            }
        }
    }

}
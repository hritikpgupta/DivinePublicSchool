package hg.divineschool.admin.ui.home.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MigrationEvent {
    data class SendLog(val msg: String) : MigrationEvent()
    data class Failure(val e: java.lang.Exception) : MigrationEvent()
    data class Success(val boolean: Boolean) : MigrationEvent()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {

    private val dbMigrationEventChannel = Channel<MigrationEvent>()
    val migrationEvent = dbMigrationEventChannel.receiveAsFlow()

    init {
        migrateClassEightUser()
    }

    private fun migrateClassEightUser() = viewModelScope.launch {
        try {
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Database Migration"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Eight Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Seven Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Six Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Five Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Four Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Three Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Two Student"))
            delay(1000)
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class One Student"))
            dbMigrationEventChannel.send(MigrationEvent.Success(true))
        } catch (e: Exception) {
            dbMigrationEventChannel.send(MigrationEvent.Failure(e))
        }

    }

}
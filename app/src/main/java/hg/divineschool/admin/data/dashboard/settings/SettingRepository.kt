package hg.divineschool.admin.data.dashboard.settings

import com.google.firebase.auth.FirebaseUser
import hg.divineschool.admin.data.Resource
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    val migrationEvent: Flow<MigrationEvent>?

    suspend fun migrateClassEightUser(): Resource<Boolean>
}
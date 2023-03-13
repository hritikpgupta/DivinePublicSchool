package hg.divineschool.admin.data.dashboard.settings

interface SettingRepository {
    suspend fun migrateUser()
}
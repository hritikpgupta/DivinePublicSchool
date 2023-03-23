package hg.divineschool.admin.data.dashboard.settings

import hg.divineschool.admin.data.Resource

interface SettingRepository {
    suspend fun getAllStudents(className: String, monthName: String)
}
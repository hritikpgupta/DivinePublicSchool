package hg.divineschool.admin.data.dashboard.settings

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.StudentDue

interface SettingRepository {
    suspend fun getAllStudentsDue(className: String, monthName: String) : Resource<List<StudentDue>>
}
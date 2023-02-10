package hg.divineschool.admin.data.dashboard

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.SchoolInformation

interface DashboardRepository {

    suspend fun getSchoolInformation() : Resource<SchoolInformation>

}
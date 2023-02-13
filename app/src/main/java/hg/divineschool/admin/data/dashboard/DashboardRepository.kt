package hg.divineschool.admin.data.dashboard

import com.google.firebase.auth.FirebaseUser
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.data.models.SchoolInformation

interface DashboardRepository {


    suspend fun getSchoolInformation() : Resource<SchoolInformation>

    suspend fun getAllClasses() : Resource<List<ClassInformation>>

}
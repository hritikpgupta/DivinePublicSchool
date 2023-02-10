package hg.divineschool.admin.data.dashboard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.SchoolInformation
import hg.divineschool.admin.data.utils.awaitDocument
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : DashboardRepository {
    override suspend fun getSchoolInformation(): Resource<SchoolInformation> {
        return try {
            val result = db.collection("school").document("basicInfo").get().awaitDocument()
            val schoolInformation = SchoolInformation()
            result.let {
                it.data.let {
                    schoolInformation.apply {
                        contact = it?.get("contact") as Long
                        email = it["email"] as String
                        endsAt = it["endsAt"] as String
                        estd = it["estd"] as Long
                        isOpen = it["isOpen"] as Boolean
                        logo = it["logo"] as String
                        name = it["name"] as String
                        ssid = it["ssid"] as String
                        startsAt = it["startsAt"] as String
                        website = it["website"] as String
                    }
                }
            }
            Resource.Success(schoolInformation)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}
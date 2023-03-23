package hg.divineschool.admin.data.dashboard.settings

import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.utils.awaitDocument
import javax.inject.Inject


class SettingRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : SettingRepository {

    override suspend fun getAllStudents(className: String, monthName: String) {
        val studentList =
            db.collection("classes").document("classEight").collection("students").get()
                .awaitDocument()
        studentList.documents.let {
            if (it.isNotEmpty()) {
                it.forEach { doc ->
                    val scholarNumber = doc.getLong("scholarNumber") as Long
                    val result = db.collection("classes").document(className).collection("students")
                        .document(scholarNumber.toString()).collection("tuitionFee")
                        .document(monthName).get().awaitDocument()
                    result.let { it ->
                        it.data.let { value ->
                            if (!(value?.get("paid") as Boolean)) {

                            }
                        }
                    }
                }
            }
        }
    }
}
package hg.divineschool.admin.data.dashboard.settings

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.StudentDue
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
import java.util.*
import javax.inject.Inject


class SettingRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : SettingRepository {

    override suspend fun getAllStudentsDue(
        className: String, monthName: String
    ): Resource<List<StudentDue>> {
        val studentDueList = ArrayList<StudentDue>()
        return try {
            val studentList =
                db.collection("classes").document(className).collection("students").get()
                    .awaitDocument()
            studentList.documents.let {
                if (it.isNotEmpty()) {
                    it.forEach { doc ->
                        val scholarNumber = doc.getLong("scholarNumber") as Long
                        val result =
                            db.collection("classes").document(className).collection("students")
                                .document(scholarNumber.toString()).collection("tuitionFee")
                                .document(monthName).get().awaitDocument()
                        result.let { it ->
                            it.data.let { value ->
                                if (!(value?.get("paid") as Boolean)) {
                                    studentDueList.add(
                                        StudentDue(
                                            "${doc.getString("firstName") as String} ${
                                                doc.getString(
                                                    "lastName"
                                                ) as String
                                            }",
                                            doc.getLong("rollNumber") as Long,
                                            doc.getString("fathersName") as String
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Resource.Success(studentDueList)
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    }
}
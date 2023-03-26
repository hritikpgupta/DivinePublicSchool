package hg.divineschool.admin.data.dashboard.settings

import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.StudentDue
import hg.divineschool.admin.data.utils.awaitDocument
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

    override suspend fun updatePrice(feeStructure: FeeStructure): Resource<Boolean> {
        return try {

            db.collection("fees").document("feeStructure")
                .update(mapOf(
                    "admissionCharge" to feeStructure.admissionCharge,
                    "annualCharge" to feeStructure.annualCharge,
                    "beltPrice" to feeStructure.beltPrice,
                    "classEightTuition" to feeStructure.classEightTuition,
                    "classFiveTuition" to feeStructure.classFiveTuition,
                    "classFourTuition" to feeStructure.classFourTuition,
                    "classOneTuition" to feeStructure.classOneTuition,
                    "classSevenTuition" to feeStructure.classSevenTuition,
                    "classSixTuition" to feeStructure.classSixTuition,
                    "classThreeTuition" to feeStructure.classThreeTuition,
                    "classTwoTuition" to feeStructure.classTwoTuition,
                    "computerFeeJunior" to feeStructure.computerFeeJunior,
                    "computerFeeSenior" to feeStructure.computerFeeSenior,
                    "diaryFee" to feeStructure.diaryFee,
                    "examFee" to feeStructure.examFee,
                    "idAndFeeCardPrice" to feeStructure.idAndFeeCardPrice,
                    "lnTuition" to feeStructure.lnTuition,
                    "pgTuition" to feeStructure.pgTuition,
                    "tieFeeJunior" to feeStructure.tieFeeJunior,
                    "tieFeeSenior" to feeStructure.tieFeeSenior,
                    "unTuition" to feeStructure.unTuition,
                )).awaitDocument()
            FeeStructure.FEE_STRUCT.apply {
                pgTuition = feeStructure.pgTuition
                lnTuition = feeStructure.lnTuition
                unTuition = feeStructure.unTuition
                classOneTuition = feeStructure.classOneTuition
                classTwoTuition = feeStructure.classTwoTuition
                classThreeTuition = feeStructure.classThreeTuition
                classFourTuition = feeStructure.classFourTuition
                classFiveTuition = feeStructure.classFiveTuition
                classSixTuition = feeStructure.classSixTuition
                classSevenTuition = feeStructure.classSevenTuition
                classEightTuition = feeStructure.classEightTuition

                admissionCharge = feeStructure.admissionCharge
                annualCharge = feeStructure.annualCharge
                examFee = feeStructure.examFee
                computerFeeJunior = feeStructure.computerFeeJunior
                computerFeeSenior = feeStructure.computerFeeSenior

                beltPrice = feeStructure.beltPrice
                diaryFee = feeStructure.diaryFee
                idAndFeeCardPrice = feeStructure.idAndFeeCardPrice
                tieFeeJunior = feeStructure.tieFeeJunior
                tieFeeSenior = feeStructure.tieFeeSenior
            }


            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
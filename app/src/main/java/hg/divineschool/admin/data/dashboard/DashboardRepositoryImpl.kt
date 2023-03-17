package hg.divineschool.admin.data.dashboard

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.SchoolInformation
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
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

    override suspend fun getAllClasses(): Resource<List<ClassInformation>> {
        return try {
            val result = db.collection("classes")
                .orderBy("id", Query.Direction.ASCENDING)
                .get().awaitDocument()
            val classList = ArrayList<ClassInformation>()
            result.let {
                it.forEach { doc ->
                    classList.add(
                        ClassInformation(
                            transportStudentsCount = doc.get("transportStudentsCount") as Long,
                            classTeacherName = doc.get("classTeacherName") as String,
                            name = doc.get("name") as String,
                            studentsCount = doc.get("studentsCount") as Long,
                            newAdmission = doc.get("newAdmission") as Long,
                            id = doc.get("id") as Long
                        )
                    )
                }
            }
            Resource.Success(classList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun getFeeStructure() {
        Log.i(Log_Tag, "Getting Fee Structure")
        val result = db.collection("fees").document("feeStructure").get().awaitDocument()
        val feeStructure = FeeStructure()
        result.data.let { it ->
            feeStructure.apply {
                admissionCharge = (it?.get("admissionCharge") as Long).toInt()
                annualCharge = (it["annualCharge"] as Long).toInt()
                beltPrice = (it["beltPrice"] as Long).toInt()
                computerFeeJunior = (it["computerFeeJunior"] as Long).toInt()
                computerFeeSenior = (it["computerFeeSenior"] as Long).toInt()
                diaryFee = (it["diaryFee"] as Long).toInt()
                examFee = (it["examFee"] as Long).toInt()
                idAndFeeCardPrice = (it["idAndFeeCardPrice"] as Long).toInt()
                tieFeeJunior = (it["tieFeeJunior"] as Long).toInt()
                tieFeeSenior = (it["tieFeeSenior"] as Long).toInt()
                pgTuition = (it["pgTuition"] as Long).toInt()
                lnTuition = (it["lnTuition"] as Long).toInt()
                unTuition = (it["unTuition"] as Long).toInt()
                classOneTuition = (it["classOneTuition"] as Long).toInt()
                classTwoTuition = (it["classTwoTuition"] as Long).toInt()
                classThreeTuition = (it["classThreeTuition"] as Long).toInt()
                classFourTuition = (it["classFourTuition"] as Long).toInt()
                classFiveTuition = (it["classFiveTuition"] as Long).toInt()
                classSixTuition = (it["classSixTuition"] as Long).toInt()
                classSevenTuition = (it["classSevenTuition"] as Long).toInt()
                classEightTuition = (it["classEightTuition"] as Long).toInt()
                transportPlaces = (it["transportPlaces"] as Map<*, *>)
                pgBooks = (it["pgBooks"] as Map<*, *>)
                lnBooks = (it["lnBooks"] as Map<*, *>)
                unBooks = (it["unBooks"] as Map<*, *>)
                classOneBooks = (it["classOneBooks"] as Map<*, *>)
                classTwoBooks = (it["classTwoBooks"] as Map<*, *>)
                classThreeBooks = (it["classThreeBooks"] as Map<*, *>)
                classFourBooks = (it["classFourBooks"] as Map<*, *>)
                classFiveBooks = (it["classFiveBooks"] as Map<*, *>)
                classSixBooks = (it["classSixBooks"] as Map<*, *>)
                classSevenBooks = (it["classSevenBooks"] as Map<*, *>)
                classEightBooks = (it["classEightBooks"] as Map<*, *>)
            }
        }
        FeeStructure.FEE_STRUCT = feeStructure
    }

    override suspend fun updateClassTeacher(classID: String, name: String): Resource<Boolean> {
        return try {
            db.collection("classes").document(classID).update("classTeacherName",name).awaitDocument()
            Resource.Success(true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}
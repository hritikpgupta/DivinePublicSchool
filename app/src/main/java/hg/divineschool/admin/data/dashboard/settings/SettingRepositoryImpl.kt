package hg.divineschool.admin.data.dashboard.settings

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.*
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.*
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

    override suspend fun updatePrice(feeStructure: FeeStructure): Resource<Boolean> {
        return try {

            db.collection("fees").document("feeStructure").update(
                mapOf(
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
                )
            ).awaitDocument()
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

    override suspend fun getBookList(className: String): Resource<List<Book>> {
        return try {
            Resource.Success(className.getClassBook())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateBookList(className: String, book: Book): Resource<List<Book>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "${className.convertClassNameToBookField()}.${book.bookName}" to book.bookPrice
                )
            ).awaitDocument()
            Resource.Success(className.updateBookPrice(book))
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    }

    override suspend fun deleteBook(className: String, book: Book): Resource<List<Book>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "${className.convertClassNameToBookField()}.${book.bookName}" to FieldValue.delete()
                )
            ).awaitDocument()

            Resource.Success(className.deleteBook(book))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addBook(className: String, book: Book): Resource<List<Book>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "${className.convertClassNameToBookField()}.${book.bookName}" to book.bookPrice
                )
            ).awaitDocument()
            Resource.Success(className.updateBookPrice(book))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getAllPlace(): Resource<List<Place>> {
        return try {
            val places = ArrayList<Place>()
            FeeStructure.FEE_STRUCT.transportPlaces.forEach {
                places.add(Place(it.key.toString(), it.value.toString().toInt()))
            }
            Resource.Success(places)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updatePlace(place: Place): Resource<List<Place>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "transportPlaces.${place.placeName}" to place.placePrice
                )
            ).awaitDocument()
            Resource.Success(place.placeName.updatePlace(place))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deletePlace(place: Place): Resource<List<Place>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "transportPlaces.${place.placeName}" to FieldValue.delete()
                )
            ).awaitDocument()

            Resource.Success(place.placeName.deletePlace())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addPlace(place: Place): Resource<List<Place>> {
        return try {
            db.collection("fees").document("feeStructure").update(
                mapOf(
                    "transportPlaces.${place.placeName}" to place.placePrice
                )
            ).awaitDocument()
            Resource.Success(place.placeName.updatePlace(place))
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateSchoolOpenState(b: Boolean) {
        try {
            db.collection("school").document("basicInfo").update("isOpen", b).awaitDocument()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateSchoolOpenTime(time: String) {
        try {
            db.collection("school").document("basicInfo").update("startsAt", time).awaitDocument()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateSchoolCloseTime(time: String) {
        try {
            db.collection("school").document("basicInfo").update("endsAt", time).awaitDocument()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getStudentsCount(): Resource<Triple<Int, Int, Int>> {
        var totalStudentCount = 0
        var transportCount = 0
        var rteCount = 0
        return try {
            val eightCount = calculate("classEight")
            totalStudentCount += eightCount.first
            transportCount += eightCount.second
            rteCount += eightCount.third
            val sevenCount = calculate("classSeven")
            totalStudentCount += sevenCount.first
            transportCount += sevenCount.second
            rteCount += sevenCount.third
            val sixCount = calculate("classSix")
            totalStudentCount += sixCount.first
            transportCount += sixCount.second
            rteCount += sixCount.third
            val fiveCount = calculate("classFive")
            totalStudentCount += fiveCount.first
            transportCount += fiveCount.second
            rteCount += fiveCount.third
            val fourCount = calculate("classFour")
            totalStudentCount += fourCount.first
            transportCount += fourCount.second
            rteCount += fourCount.third
            val threeCount = calculate("classThree")
            totalStudentCount += threeCount.first
            transportCount += threeCount.second
            rteCount += threeCount.third
            val twoCount = calculate("classTwo")
            totalStudentCount += twoCount.first
            transportCount += twoCount.second
            rteCount += twoCount.third
            val oneCount = calculate("classOne")
            totalStudentCount += oneCount.first
            transportCount += oneCount.second
            rteCount += oneCount.third
            val unCount = calculate("classUpperNursery")
            totalStudentCount += unCount.first
            transportCount += unCount.second
            rteCount += unCount.third
            val lnCount = calculate("classLowerNursery")
            totalStudentCount += lnCount.first
            transportCount += lnCount.second
            rteCount += lnCount.third
            val pgCount = calculate("classPlayGroup")
            totalStudentCount += pgCount.first
            transportCount += pgCount.second
            rteCount += pgCount.third
            Resource.Success(Triple(totalStudentCount, transportCount, rteCount))
        } catch (e: java.lang.Exception) {
            Resource.Failure(e)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override suspend fun getTransactions(dateRangeState: DateRangePickerState): Resource<List<Transaction>> {
        return try {
            val list = ArrayList<Transaction>()

            if (dateRangeState.selectedEndDateMillis == null || dateRangeState.selectedStartDateMillis == null) {
                val data =
                    db.collection("transactions").orderBy("timestamp", Query.Direction.ASCENDING)
                        .limit(25).get().awaitDocument()
                data.documents.let {
                    if (it.isNotEmpty()) {
                        it.forEach { doc ->
                            list.add(
                                Transaction(
                                    amount = doc.getLong("amount") as Long,
                                    className = doc.getString("className") as String,
                                    scholarNumber = doc.getString("scholarNumber") as String,
                                    invoiceNumber = doc.getString("invoiceNumber") as String,
                                    studentName = doc.getString("studentName") as String,
                                    timestamp = doc.getTimestamp("timestamp") as Timestamp
                                )
                            )
                        }
                    }
                }
                Resource.Success(list)
            } else {
                val startDate = Date(dateRangeState.selectedStartDateMillis!!)
                val endDate = Date(dateRangeState.selectedEndDateMillis!!)
                val data = db.collection("transactions").whereGreaterThanOrEqualTo(
                    "timestamp", Timestamp(startDate)
                ).whereLessThanOrEqualTo("timestamp", Timestamp(endDate))
                    .orderBy("timestamp", Query.Direction.ASCENDING).get().awaitDocument()
                data.documents.let {
                    if (it.isNotEmpty()) {
                        it.forEach { doc ->
                            list.add(
                                Transaction(
                                    amount = doc.getLong("amount") as Long,
                                    className = doc.getString("className") as String,
                                    scholarNumber = doc.getString("scholarNumber") as String,
                                    invoiceNumber = doc.getString("invoiceNumber") as String,
                                    studentName = doc.getString("studentName") as String,
                                    timestamp = doc.getTimestamp("timestamp") as Timestamp
                                )
                            )
                        }
                    }
                }
                Resource.Success(list)
            }
        } catch (e: java.lang.Exception) {
            Resource.Failure(e)
        }
    }

    private suspend fun calculate(fromClass: String): Triple<Int, Int, Int> {
        var total = 0
        var transport = 0
        var rte = 0
        val students = db.collection("classes").document(fromClass).collection("students").get()
            .awaitDocument()
        students.documents.let {
            if (it.isNotEmpty()) {
                it.forEach { doc ->
                    total += 1
                    if (doc.getBoolean("transportStudent") as Boolean) {
                        transport += 1
                    }
                    if (doc.getBoolean("rte") as Boolean) {
                        rte += 1
                    }
                }
            }
        }
        return Triple(total, transport, rte)
    }
}
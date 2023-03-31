package hg.divineschool.admin.data.dashboard.settings

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.Place
import hg.divineschool.admin.data.models.StudentDue
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.*
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
                .update(
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
            db.collection("fees").document("feeStructure")
                .update(
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
            db.collection("fees").document("feeStructure")
                .update(
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
            db.collection("fees").document("feeStructure")
                .update(
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
            db.collection("fees").document("feeStructure")
                .update(
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

    override suspend fun getStudentsCount(): Triple<Int, Int, Int> {
        val totalStudentCount = 0
        val transportCount = 0
        val rteCount = 0

        return Triple(totalStudentCount, transportCount, rteCount)
    }
}
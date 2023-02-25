package hg.divineschool.admin.data.models

import android.os.Parcel
import android.os.Parcelable

data class Student(
    var image: String = "",
    var rollNumber: Long = 0,
    var enrollmentNumber: Long = 0,


    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirth: String = "",
    var gender: String = "",

    var fathersName: String = "",
    var mothersName: String = "",
    var guardianOccupation: String = "",
    var religion: String = "",


    var address: String = "",
    var contactNumber: Long = 0,
    var aadharNumber: Long = 0,


    var dateOfAdmission: String = "",
    var entryClass: String = "",
    var schoolAttended: String = "",


    var transportStudent: Boolean = false,
    var newStudent: Boolean = false,
    var orphan: Boolean = false,
): java.io.Serializable{
    fun doesMatchSearchQuery(query:String):Boolean{
        val matchingCombination = listOf("$firstName$lastName", "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
            "$rollNumber","$religion","$guardianOccupation")
        return matchingCombination.any { it.contains(query,ignoreCase = true) }
    }

}

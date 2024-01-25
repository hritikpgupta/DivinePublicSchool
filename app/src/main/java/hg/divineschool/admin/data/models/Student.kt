package hg.divineschool.admin.data.models

data class Student(
    var image: String = "",
    var rollNumber: Long = 0,
    var scholarNumber: Long = 0,
    var personalEducationNumber: String = "",


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
    var rte: Boolean = false,
    var active: Boolean = true,
): java.io.Serializable{
    fun doesMatchSearchQuery(query:String):Boolean{
        val matchingCombination = listOf("$firstName$lastName", "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
            gender, dateOfBirth,dateOfAdmission, "$scholarNumber",
            "$rollNumber", religion,
            guardianOccupation, "$contactNumber", "$aadharNumber", mothersName , fathersName
        )
        return matchingCombination.any { it.contains(query,ignoreCase = true) }
    }

}

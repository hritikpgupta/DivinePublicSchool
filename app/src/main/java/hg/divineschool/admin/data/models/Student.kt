package hg.divineschool.admin.data.models

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
    var isOrphan: Boolean = false,
)

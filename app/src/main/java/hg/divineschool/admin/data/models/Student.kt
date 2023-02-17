package hg.divineschool.admin.data.models

data class Student(
    var aadharNumber: Long = 0,
    var address : String = "",
    var contactNumber : Long = 0,
    var dateOfAdmission : String = "",
    var dateOfBirth : String = "",
    var enrollmentNumber : Long = 0,
    var entryClass : String = "",
    var fathersName : String = "",
    var firstName : String = "",
    var gender : String = "",
    var guardianOccupation : String = "",
    var image : String = "",
    var isOrphan : Boolean = false,
    var lastName : String = "",
    var mothersName : String = "",
    var newStudent : Boolean = false,
    var religion : String = "",
    var rollNumber : Long = 0,
    var schoolAttended : String = "",
    var transportStudent : Boolean = false
    )

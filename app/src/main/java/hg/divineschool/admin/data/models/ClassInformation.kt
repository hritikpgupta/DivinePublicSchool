package hg.divineschool.admin.data.models

data class ClassInformation(
    var transportStudentsCount: Long = 0,
    var id: Long = 0,
    var classTeacherName: String = "",
    var name: String = "",
    var studentsCount: Long = 0,
    var newAdmission: Long = 0
)

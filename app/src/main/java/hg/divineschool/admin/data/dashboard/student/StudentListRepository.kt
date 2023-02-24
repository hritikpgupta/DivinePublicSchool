package hg.divineschool.admin.data.dashboard.student

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.SchoolInformation
import hg.divineschool.admin.data.models.Student

interface StudentListRepository {
    val students : List<Student>?

    suspend fun getStudentList(classID : Long): Resource<List<Student>>
}
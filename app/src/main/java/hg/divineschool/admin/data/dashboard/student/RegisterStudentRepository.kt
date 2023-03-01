package hg.divineschool.admin.data.dashboard.student

import android.net.Uri
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student

interface RegisterStudentRepository {
    suspend fun uploadProfileImage(student: Student,classId: String,className: String, fileUriString : String): Resource<Student>
    suspend fun updateProfileImage(student: Student,classId: String,className: String, fileUriString : String): Resource<Student>
}
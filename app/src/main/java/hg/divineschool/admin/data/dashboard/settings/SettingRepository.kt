package hg.divineschool.admin.data.dashboard.settings

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.Place
import hg.divineschool.admin.data.models.StudentDue

interface SettingRepository {
    suspend fun getAllStudentsDue(className: String, monthName: String): Resource<List<StudentDue>>

    suspend fun updatePrice(feeStructure: FeeStructure): Resource<Boolean>

    suspend fun getBookList(className: String): Resource<List<Book>>

    suspend fun updateBookList(className: String, book: Book): Resource<List<Book>>

    suspend fun deleteBook(className: String, book: Book): Resource<List<Book>>

    suspend fun addBook(className: String, book: Book): Resource<List<Book>>

    suspend fun getAllPlace(): Resource<List<Place>>
    suspend fun updatePlace(place: Place): Resource<List<Place>>
    suspend fun deletePlace(place: Place): Resource<List<Place>>
    suspend fun addPlace(place: Place): Resource<List<Place>>
    suspend fun updateSchoolOpenState(b: Boolean)
    suspend fun updateSchoolOpenTime(time: String)
    suspend fun updateSchoolCloseTime(time: String)
    suspend fun getStudentsCount() : Resource<Triple<Int,Int,Int>>
}
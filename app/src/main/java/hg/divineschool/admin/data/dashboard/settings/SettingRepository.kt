package hg.divineschool.admin.data.dashboard.settings

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.StudentDue
import hg.divineschool.admin.ui.utils.classNames

interface SettingRepository {
    suspend fun getAllStudentsDue(className: String, monthName: String) : Resource<List<StudentDue>>

    suspend fun updatePrice( feeStructure: FeeStructure): Resource<Boolean>

    suspend fun getBookList(className: String) : Resource<List<Book>>

    suspend fun updateBookList(className: String, book: Book) : Resource<List<Book>>

    suspend fun deleteBook(className: String, book: Book) : Resource<List<Book>>

    suspend fun addBook(className: String, book: Book) : Resource<List<Book>>
}
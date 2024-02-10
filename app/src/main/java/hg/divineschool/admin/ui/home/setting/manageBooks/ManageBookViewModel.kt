package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.ui.utils.classNames
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageBookViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {
    private val _bookListFlow = MutableStateFlow<Resource<List<Book>>?>(null)
    val bookListFlow: StateFlow<Resource<List<Book>>?> = _bookListFlow

    init {
        getBookList()
    }

    fun getBookList(className : String = classNames[0])= viewModelScope.launch {
        _bookListFlow.value = Resource.Loading
        _bookListFlow.value = repository.getBookList(className)
    }
    fun updateBook(className: String, book: Book) = viewModelScope.launch {
        _bookListFlow.value = Resource.Loading
        _bookListFlow.value = repository.updateBookList(className,book)

    }
    fun deleteBook(className: String, book: Book) = viewModelScope.launch {
        _bookListFlow.value = Resource.Loading
        _bookListFlow.value = repository.deleteBook(className,book)
    }

    fun addBook(className: String,book: Book) = viewModelScope.launch {
        _bookListFlow.value = Resource.Loading
        _bookListFlow.value = repository.addBook(className,book)
    }
}
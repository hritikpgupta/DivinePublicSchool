package hg.divineschool.admin.ui.home.setting.manageBooks

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.ClassInformation
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.ui.home.DPSBar
import hg.divineschool.admin.ui.theme.boldFont
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
}
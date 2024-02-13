package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.StudentInvoiceRepository
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.StudentMonthFee
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentInvoiceViewModel @Inject constructor(
    private val repository: StudentInvoiceRepository,
    private val ioDispatcher: CoroutineDispatcher

) : ViewModel() {

    private var _studentInformation = MutableStateFlow<Resource<StudentMonthFee>?>(null)
    val studentInformation: StateFlow<Resource<StudentMonthFee>?> = _studentInformation

    private var _saveInvoice = MutableStateFlow<Resource<Invoice>?>(null)
    val saveInvoice: StateFlow<Resource<Invoice>?> = _saveInvoice

    private var _studentInvoices = MutableStateFlow<Resource<List<Invoice>>?>(null)
    val studentInvoices: StateFlow<Resource<List<Invoice>>?> = _studentInvoices


    fun getStudent(classID: String, scholarNumber: String) = viewModelScope.launch {
        _studentInformation.value = Resource.Loading
        _studentInformation.value = withContext(ioDispatcher) {
            repository.getStudent(classID, scholarNumber)
        }
    }

    fun saveInvoice(classID: String, scholarNumber: String, invoice: Invoice) =
        viewModelScope.launch {
            _saveInvoice.value = Resource.Loading
            _saveInvoice.value = withContext(ioDispatcher) {
                repository.saveInvoice(classID, scholarNumber, invoice)
            }
        }

    fun getAllInvoices(classID: String, studentScholarNumber: String) = viewModelScope.launch {
        _studentInvoices.value = Resource.Loading
        _studentInvoices.value = withContext(ioDispatcher) {
            repository.getAllInvoices(classID, studentScholarNumber)
        }
    }

}


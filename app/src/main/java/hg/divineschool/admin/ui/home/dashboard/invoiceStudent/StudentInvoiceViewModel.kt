package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.StudentInvoiceRepository
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.StudentMonthFee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentInvoiceViewModel @Inject constructor(
    private val repository: StudentInvoiceRepository
) : ViewModel() {

    private var _studentInformation = MutableStateFlow<Resource<StudentMonthFee>?>(null)
    val studentInformation: StateFlow<Resource<StudentMonthFee>?> = _studentInformation

    private var _saveInvoice = MutableStateFlow<Resource<Invoice>?>(null)
    val saveInvoice: StateFlow<Resource<Invoice>?> = _saveInvoice


    fun getStudent(classID: String, scholarNumber: String) = viewModelScope.launch {
        _studentInformation.value = Resource.Loading
        val result = repository.getStudent(classID, scholarNumber)
        _studentInformation.value = result
    }

    fun saveInvoice(classID: String, scholarNumber: String, invoice: Invoice) =
        viewModelScope.launch {
            _saveInvoice.value = Resource.Loading
            val result = repository.saveInvoice(classID, scholarNumber, invoice)
            _saveInvoice.value = result
        }

    fun getAllInvoices(classID: String, studentScholarNumber: String): List<Invoice> {
        var invoiceList = emptyList<Invoice>()
        viewModelScope.launch {
            invoiceList = repository.getAllInvoices(classID, studentScholarNumber)
        }
        return invoiceList
    }
}


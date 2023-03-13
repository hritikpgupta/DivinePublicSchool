package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepositoryImpl
import hg.divineschool.admin.data.dashboard.student.StudentInvoiceRepository
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.StudentMonthFee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentInvoiceViewModel @Inject constructor(
    private val repository: StudentInvoiceRepository,
) : ViewModel() {

    private var _studentInformation = MutableStateFlow<Resource<StudentMonthFee>?>(null)
    val studentInformation: StateFlow<Resource<StudentMonthFee>?> = _studentInformation

    private var _saveInvoice = MutableStateFlow<Resource<Invoice>?>(null)
    val saveInvoice: StateFlow<Resource<Invoice>?> = _saveInvoice

    private var _studentInvoices = MutableStateFlow<Resource<List<Invoice>>?>(null)
    val studentInvoices: StateFlow<Resource<List<Invoice>>?> = _studentInvoices


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

    fun getAllInvoices(classID: String, studentScholarNumber: String) = viewModelScope.launch {
        _studentInvoices.value = Resource.Loading
        val result = repository.getAllInvoices(classID, studentScholarNumber)
        _studentInvoices.value = result
    }

}


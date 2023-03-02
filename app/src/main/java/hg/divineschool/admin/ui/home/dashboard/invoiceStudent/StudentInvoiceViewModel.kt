package hg.divineschool.admin.ui.home.dashboard.invoiceStudent

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.dashboard.student.StudentInvoiceRepository
import javax.inject.Inject

@HiltViewModel
class StudentInvoiceViewModel @Inject constructor(
    private val repository: StudentInvoiceRepository
) : ViewModel() {

}
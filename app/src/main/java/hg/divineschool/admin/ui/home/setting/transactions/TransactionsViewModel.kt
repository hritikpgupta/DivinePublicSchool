package hg.divineschool.admin.ui.home.setting.transactions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private var respository: SettingRepository
) : ViewModel() {
}
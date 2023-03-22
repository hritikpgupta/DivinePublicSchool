package hg.divineschool.admin.ui.home.setting.checkDues

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import javax.inject.Inject

@HiltViewModel
class CheckDueViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {

}
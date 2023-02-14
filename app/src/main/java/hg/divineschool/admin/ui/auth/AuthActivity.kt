package hg.divineschool.admin.ui.auth

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.ui.AppNavHost
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme
import hg.divineschool.admin.ui.utils.LockScreenOrientation

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivinePublicSchoolTheme {
                AppNavHost(rememberNavController())
            }
        }
    }

}

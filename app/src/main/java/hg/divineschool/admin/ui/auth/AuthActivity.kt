package hg.divineschool.admin.ui.auth

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.ui.DpsNavHost
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContent {
            DivinePublicSchoolTheme {
                DpsNavHost(rememberNavController())
            }
        }

    }

}

package hg.divineschool.admin.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.ui.AppNavHost
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme

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

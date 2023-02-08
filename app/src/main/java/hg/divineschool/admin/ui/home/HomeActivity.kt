package hg.divineschool.admin.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivinePublicSchoolTheme {
                Log.i(HomeActivity::class.java.toString(), "Reached Here")
            }
        }
    }
}
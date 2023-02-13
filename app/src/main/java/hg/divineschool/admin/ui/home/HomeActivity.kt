package hg.divineschool.admin.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hg.divineschool.admin.ui.theme.DivinePublicSchoolTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivinePublicSchoolTheme {
                val navController = rememberNavController()
                val bgArgb = androidx.compose.material.MaterialTheme.colors.background.toArgb()
                this.window.statusBarColor = bgArgb
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        scaffoldState = rememberScaffoldState(),
                        topBar = { DPSAppBar() },
                        bottomBar = {
                            AppBottomNavigation(navController)
                        },
                    ) { innerPadding ->
                        AppNavigationGraph(navController, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
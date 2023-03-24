package hg.divineschool.admin.ui.home.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hg.divineschool.admin.AppScreen
import hg.divineschool.admin.BottomNavItem
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.SettingItem
import hg.divineschool.admin.ui.home.DPSAppBar
import hg.divineschool.admin.ui.theme.regularFont

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(navController: NavController) {
    val settings = listOf(
        SettingItem(1, "Check Dues", R.drawable.due_date, true),
        SettingItem(3, "Transactions", R.drawable.transaction, true),
        SettingItem(2, "Manage Fees", R.drawable.manage_fees, true),
        SettingItem(4, "Manage Books", R.drawable.manage_books, true),
        SettingItem(6, "Manage Location", R.drawable.manage_navigation, true),
        SettingItem(5, "Log Out", R.drawable.logout, false)
    )

    Scaffold(topBar = { DPSAppBar() }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colors.background.copy(0.8f))
        ) {
            StatisticCard()
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                userScrollEnabled = true,
                contentPadding = PaddingValues(
                    top = 15.dp, start = 8.dp, end = 8.dp, bottom = 80.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background.copy(0.6f)),
                columns = GridCells.Adaptive(280.dp)
            ) {
                items(settings) { str ->
                    val cardModifier = if (str.enabled) {
                        Modifier
                            .requiredWidth(280.dp)
                            .requiredHeight(150.dp)
                    } else {
                        Modifier
                            .requiredWidth(280.dp)
                            .requiredHeight(150.dp)
                            .drawWithContent {
                                drawContent()
                                drawRect(color = Color.LightGray.copy(0.7f))
                            }
                    }
                    Card(backgroundColor = Color.LightGray.copy(0.2f),
                        shape = RoundedCornerShape(4.dp),
                        elevation = 2.dp,
                        enabled = str.enabled,
                        border = BorderStroke(4.dp, Color.Black.copy(0.6f)),
                        modifier = cardModifier,
                        onClick = {
                            when (str.id) {
                                1 -> {
                                    navController.navigate(AppScreen.SettingScreen.CheckDues.route)
                                }
                                2 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageFees.route)
                                }
                                3 -> {
                                    navController.navigate(AppScreen.SettingScreen.Transaction.route)
                                }
                                4 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageBooks.route)
                                }
                                5 -> {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(AppScreen.SettingScreen.LogOut.route) {
                                        popUpTo(BottomNavItem.Home.route) { inclusive = true }
                                    }
                                }
                                6 -> {
                                    navController.navigate(AppScreen.SettingScreen.ManageLocation.route)
                                }
                            }
                        }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = str.icon),
                                contentDescription = str.name,
                                modifier = Modifier.requiredSize(80.dp)
                            )
                            Spacer(modifier = Modifier.requiredHeight(12.dp))
                            Text(
                                text = str.name, fontSize = 30.sp, style = TextStyle(
                                    fontFamily = regularFont, fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

        }
    }
}

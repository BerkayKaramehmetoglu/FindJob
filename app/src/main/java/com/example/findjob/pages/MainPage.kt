package com.example.findjob.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.findjob.datastore.clearUserSession
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        scrimColor = Color.Gray,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color(0xFFFFE642)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        "FIND A Jobs",
                        modifier = Modifier.padding(15.dp),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color(0xFF0E5459)
                    )
                    HorizontalDivider(color = Color.Black)
                    Text(
                        "Aktivitelerim",
                        modifier = Modifier.padding(15.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF0E5459)
                    )
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 10.dp, 0.dp),
                        label = {
                            Text(
                                text = "İş İlanı Ver",
                                color = Color(0xFF0E5459),
                                fontSize = 17.sp
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.Outlined.AddCircle,
                                tint = Color(0xFF0E5459),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        onClick = { navController.navigate("postjob_screen") }
                    )
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(10.dp, 15.dp, 10.dp, 15.dp),
                        label = {
                            Text(
                                "İş İlanlarım",
                                color = Color(0xFF0E5459),
                                fontSize = 17.sp
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF0E5459),
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        onClick = { navController.navigate("myjob_screen")  }
                    )
                    HorizontalDivider(color = Color.Black)
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(10.dp, 15.dp, 10.dp, 0.dp),
                        label = { Text("Çıkış", color = Color(0xFF0E5459), fontSize = 17.sp) },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.ExitToApp,
                                contentDescription = null,
                                tint = Color(0xFF0E5459),
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                clearUserSession(context)
                                navController.navigate("login_screen") {
                                    //popUpTo("main_screen") { inclusive = true }
                                }
                            }
                        },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Find a Jobs") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Buraya diğer iş ilanları gelecek")
            }
        }
    }
}
package br.edu.ifpb.pdm.oriymenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpb.pdm.oriymenu.ui.theme.OriymenuTheme
import br.edu.ifpb.pdm.oriymenu.ui.theme.screens.HomeScreen
import br.edu.ifpb.pdm.oriymenu.ui.theme.screens.LoginScreen
import br.edu.ifpb.pdm.oriymenu.ui.theme.screens.RegisterDish


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OriymenuTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("ORYI Menu")
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(onSignInClick = {
                    navController.navigate("home")
                })
            }
            composable("home") {
                HomeScreen(modifier = Modifier.padding(innerPadding), onNewDishClick = {
                    navController.navigate("registerDish")
                })
            }
            composable("registerDish") {
                RegisterDish(modifier = Modifier.padding(innerPadding), onRegisterClick = {
                    navController.navigate("home")
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    OriymenuTheme {
        MainApp()
    }
}

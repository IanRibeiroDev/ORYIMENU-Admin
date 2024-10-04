package br.edu.ifpb.pdm.oriymenu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpb.pdm.oriymenu.model.data.AdminDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    val adminDAO = AdminDAO()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ORYI - Admin",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Usuário") },
                placeholder = { Text(text = "Digite o seu email") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Senha") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            )
            Row {
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
//                            adminDAO.findByEmail(username, callback = { admin ->
//                                if (admin != null && admin.password == password) {
//                                    onSignInClick()
//                                } else {
//                                    errorMessage = "Login ou Senha inválidos!"
//                                }
//                            })
                            adminDAO.authenticateAdmin(username, password) { admin ->
                                if (admin != null) {
                                    onSignInClick()
                                } else {
                                    errorMessage = "Login ou Senha inválidos!"
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Entrar", color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        onSignUpClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Cadastrar", color = Color.White)
                }
            }
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
                LaunchedEffect(Unit) {
                    delay(3000)
                    errorMessage = null
                }
            }
        }
    }
}

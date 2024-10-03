package br.edu.ifpb.pdm.oriymenu.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.ifpb.pdm.oriymenu.model.data.Admin
import br.edu.ifpb.pdm.oriymenu.model.data.AdminDAO
import br.edu.ifpb.pdm.oriymenu.ui.viewmodels.RegisterAdminViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterAdmin(
    modifier: Modifier = Modifier,
    admin: Admin? = null,
    registerAdminViewModel: RegisterAdminViewModel = viewModel(),
    onRegisterClick: () -> Unit,
    onGoBackButton: () -> Unit,
    navController: NavController
) {
    // State for the admin details: name, email, password, and address
    val name by registerAdminViewModel.name.collectAsState()
    val email by registerAdminViewModel.email.collectAsState()
    val password by registerAdminViewModel.password.collectAsState()
    val address by registerAdminViewModel.address.collectAsState()

    val scope = rememberCoroutineScope()
    val fieldSize = 300.dp
    val zipCodeNumberOfDigits = 8
    val stateAbbreviationNumberOfChars = 2

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp)
        ) {
            Text(
                text = if (admin != null) "Atualizar admin" else "Cadastrar Admin",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = name,
            onValueChange = { registerAdminViewModel.updateName(it) },
            label = { Text(text = "Nome") },
            placeholder = { Text(text = "Digite o seu nome") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = email,
            onValueChange = { registerAdminViewModel.updateEmail(it) },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Digite o seu email (login)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = password,
            onValueChange = { registerAdminViewModel.updatePassword(it) },
            label = { Text(text = "Senha") },
            placeholder = { Text(text = "Digite uma senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = address?.zipCode ?: "",
            onValueChange = {
                // Restrict to digits and limit to 8 characters
                Log.d("ZIP CODE BEFORE IF", it)
                if (it.all { char -> char.isDigit() } && it.length <= zipCodeNumberOfDigits) {
                    registerAdminViewModel.updateZipCode(it)
                    Log.d("ZIP CODE ONCHANGE", it)

                    // Perform query when input length reaches 8 (number of digits in a Brazilian
                    // CEP)
                    if (it.length == zipCodeNumberOfDigits) {
                        registerAdminViewModel.fetchAddress()
                    }
                }
            },
            label = { Text(text = "CEP") },
            placeholder = { Text(text = "Digite o seu CEP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = address?.street ?: "",
            onValueChange = { registerAdminViewModel.updateAddressStreet(it) },
            label = { Text(text = "Logradouro") },
            placeholder = { Text(text = "Digite o seu logradouro") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = address?.number ?: "",
            onValueChange = { registerAdminViewModel.updateAddressNumber(it) },
            label = { Text(text = "Número") },
            placeholder = { Text(text = "Digite o número do seu logradouro") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = address?.city ?: "",
            onValueChange = { registerAdminViewModel.updateAddressCity(it) },
            label = { Text(text = "Cidade") },
            placeholder = { Text(text = "Digite o nome da sua cidade") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = address?.state ?: "",
            onValueChange = {
                // Restrict to letters and limit to 2 characters
                if (it.all { char -> char.isLetter() } &&
                    it.length <= stateAbbreviationNumberOfChars) {
                    registerAdminViewModel.updateAddressState(it)
                }
            },
            label = { Text(text = "Estado") },
            placeholder = { Text(text = "Digite a sigla do seu estado") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(6.dp))

//        Button(onClick = {
//            val newAdmin = Admin(
//                name = name,
//                email = email,
//                password = password,
//                address = address
//            )
//            // TODO: implement validation logic later
//            scope.launch(Dispatchers.IO) {
//                if (admin != null) {  // update an existing admin
//                    newAdmin.id = admin.id
//                    // Update admin in the database
//                    AdminDAO().update(newAdmin) {
//                        if (it) {
//                            onRegisterClick()
//                        }
//                    }
//                } else {  // register a new admin
//                    AdminDAO().save(newAdmin) {
//                        if (it) {
//                            onRegisterClick()
//                        }
//                    }
//                }
//            }
//        }) {
//            Text(text = if (admin != null) "Update" else "Register")
//        }

        OutlinedButton(onClick = { onGoBackButton() }) {
            Text(text = "Back")
        }
    }
}

package br.edu.ifpb.pdm.oriymenu.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import br.edu.ifpb.pdm.oriymenu.ui.theme.OriymenuTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterDish(modifier: Modifier = Modifier, onRegisterClick: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var meal by remember { mutableStateOf("") }
    var pathToImage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val enrollmentDigitLimit = 7

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
                text = "Novo prato",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nome") },
            placeholder = { Text(text = "O nome do prato") }
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Descrição") },
            placeholder = { Text(text = "Uma breve descrição sobre o prato") }
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = meal,
            onValueChange = { meal = it },
            label = { Text(text = "Refeição") },
            placeholder = { Text(text = "Almoço ou café da manhã") }
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = pathToImage,
            onValueChange = { pathToImage = it },
            label = { Text(text = "Imagem") },
            placeholder = { Text(text = "URL para a imagem do prato") }
        )
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = {
            val newDish = Dish(
                name = name,
                description = description,
                meal = meal,
                pathToImage = pathToImage
            )
            // TODO: implement validation logic later
            scope.launch(Dispatchers.IO) {
                DishDAO().save(dish = newDish, callback = {
                    if (it) {  // If the dish was successfully saved
                        onRegisterClick()
                    }
                })
            }
            // After successful registration logic
            onRegisterClick()
        }) {
            Text(text = "Adicionar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    OriymenuTheme {
        RegisterDish(onRegisterClick = {})
    }
}

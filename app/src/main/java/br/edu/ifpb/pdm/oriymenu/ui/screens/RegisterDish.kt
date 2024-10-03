import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavController
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import br.edu.ifpb.pdm.oriymenu.ui.theme.OriymenuTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterDish(
    modifier: Modifier = Modifier,
    dish: Dish? = null,
    onRegisterClick: () -> Unit,
    onGoBackButton: () -> Unit,
    navController: NavController
) {

    var name by remember { mutableStateOf(dish?.name ?: "") }
    var description by remember { mutableStateOf(dish?.description ?: "") }
    var meal by remember { mutableStateOf(dish?.meal ?: "") }
    var pathToImage by remember { mutableStateOf(dish?.pathToImage ?: "") }

    val scope = rememberCoroutineScope()
    val fieldSize = 300.dp

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
                text = if (dish != null) "Atualizar prato" else "Cadastrar prato",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nome") },
            placeholder = { Text(text = "O nome do prato") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Descrição") },
            placeholder = { Text(text = "Uma breve descrição sobre o prato") }
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = meal,
            onValueChange = { meal = it },
            label = { Text(text = "Refeição") },
            placeholder = { Text(text = "Almoço ou café da manhã") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier.width(fieldSize),
            value = pathToImage,
            onValueChange = { pathToImage = it },
            label = { Text(text = "Imagem") },
            placeholder = { Text(text = "URL para a imagem do prato") },
            singleLine = true
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
            if (dish != null) {  // update an existing dish
                newDish.id = dish.id

                scope.launch(Dispatchers.IO) {
                    DishDAO().update(dish = newDish, callback = {
                        if (it) {  // If the dish was successfully updated
                            onRegisterClick()
                        }
                    })
                }
            } else {  // insert a new dish
                scope.launch(Dispatchers.IO) {
                    DishDAO().save(dish = newDish, callback = {
                        if (it) {  // If the dish was successfully saved
                            onRegisterClick()
                        }
                    })
                }
            }
            // After successful registration logic
        }) {
            Text(text = if (dish != null) "Atualizar" else "Cadastrar")
        }
        OutlinedButton(onClick = {
            onGoBackButton()
        }) {
            Text(text = "Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    OriymenuTheme {
//        RegisterDish(onRegisterClick = {}, onGoBackButton = {})
    }
}
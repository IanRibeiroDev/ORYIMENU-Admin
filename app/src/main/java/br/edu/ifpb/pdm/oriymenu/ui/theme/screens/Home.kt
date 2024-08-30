package br.edu.ifpb.pdm.oriymenu.ui.theme.screens


import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpb.pdm.oriymenu.R
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import br.edu.ifpb.pdm.oriymenu.model.data.Menu
import br.edu.ifpb.pdm.oriymenu.model.data.MenuDAO
import coil.compose.AsyncImage
import com.google.gson.Gson

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNewDishClick: () -> Unit,
    onEditDishClick: (String) -> Unit
) {
    val dishes = remember { mutableStateListOf<Dish>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DishCard(dishes = dishes, onEditDishClick = onEditDishClick)
        Spacer(modifier = Modifier.height(16.dp))
        // FIXME: this button will be removed in the future as it is only for testing purposes
        // the data will be fetched from the database automatically
        Button(onClick = {
            onNewDishClick()
        }) {
            Text("Novo prato")
        }
        OutlinedButton(onClick = {
            DishDAO().findAll(callback = {
                dishes.clear()
                dishes.addAll(it)
            })
        }) {
            Text(text = "Listar pratos")
        }
    }
}

@Composable
fun DishCard(
    dishes: List<Dish>,
    onEditDishClick: (String) -> Unit
) {

    LazyColumn {
        items(dishes) { dish ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            )
            {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = dish.pathToImage,
                        contentDescription = dish.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = dish.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dish.description,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        OutlinedButton(onClick = {
                            // JSON logic to pass the dish object as a string to the RegisterDish screen
                            val selectedDish = Gson().toJson(dish)
                            onEditDishClick(selectedDish)
                        }) {
                            Text(text = "Editar")
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar"
                            )
                        }
                    }
                }
            }
        }
    }
}

package br.edu.ifpb.pdm.oriymenu.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onLogoffClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val dishDAO = DishDAO()
    val dishes = remember { mutableStateListOf<Dish>() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DishCard(
            imageRes = R.drawable.espaguete,
            name = "Spaghetti Carbonara",
            description = "Delicioso espaguete com molho carbonara e pedaços crocantes de bacon.",
            dishes = dishes
        )
        Spacer(modifier = Modifier.height(16.dp))
        // FIXME: this button will be removed in the future as it is only for testing purposes
        // the data will be fetched from the database automatically
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                dishDAO.findAll(callback = { returnedDishes ->
                    dishes.clear()
                    dishes.addAll(returnedDishes)
                })
            }
        }) {
            Text("Mostrar cardápio")
        }
    }
}

@Composable
fun DishCard(
    imageRes: Int,
    name: String,
    description: String,
    dishes: List<Dish>
) {
    val scope = rememberCoroutineScope()
    val menuDAO = MenuDAO()
    var menu by remember { mutableStateOf(Menu()) }

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
                }
            }
        }
    }
}

package br.edu.ifpb.pdm.oriymenu.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.ui.viewmodels.MenuViewModel
import br.edu.ifpb.pdm.oriymenu.ui.viewmodels.RegisterDishViewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DishCard(
    dishes: List<Dish>,
    menuViewModel: MenuViewModel = viewModel(),
//    registerDishViewModel: RegisterDishViewModel = viewModel(),
    onEditDishClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    // state to control the dialog
    val openAlertDialog by menuViewModel.openAlertDialog.collectAsState()

    val namesOfDaysOfWeek = menuViewModel.namesOfDaysOfWeek

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
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        OutlinedButton(onClick = {
                            // JSON logic to pass the dish object as a string to the RegisterDish screen
                            val selectedDish = Gson().toJson(dish)
//                            registerDishViewModel.updateSelectedDayOfWeek(
//                                namesOfDaysOfWeek[menuViewModel.selectedDayIndex.value]
//                            )
                            onEditDishClick(selectedDish)
                        }) {
                            Text(text = "Editar")
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar"
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        OutlinedButton(onClick = {
                            menuViewModel.setOpenAlertDialog(true)
                        }) {
                            Text(text = "Excluir")
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Excluir"
                            )
                        }
                    }
                }
            }
            when {
                openAlertDialog -> {
                    AlertDialogComponent(
                        onDismissRequest = { menuViewModel.setOpenAlertDialog(false) },
                        onConfirmation = {
                            menuViewModel.setOpenAlertDialog(false)
                            scope.launch(Dispatchers.IO) {
                                // Remove the dish from the day of the week
                                menuViewModel.removeDishFromDayOfWeek(
                                    namesOfDaysOfWeek[
                                        menuViewModel.selectedDayIndex.value], dish)
                            }
                        },
                        dialogTitle = "Remoção de prato",
                        dialogText = "Você tem certeza que deseja remover o prato?",
                        icon = Icons.Default.Info
                    )
                }
            }
        }
    }
}
package br.edu.ifpb.pdm.oriymenu.ui.theme.screens


import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpb.pdm.oriymenu.R
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import br.edu.ifpb.pdm.oriymenu.model.data.Menu
import br.edu.ifpb.pdm.oriymenu.model.data.MenuDAO
import br.edu.ifpb.pdm.oriymenu.model.data.WeekDayNames
import br.edu.ifpb.pdm.oriymenu.ui.theme.components.AlertDialogComponent
import br.edu.ifpb.pdm.oriymenu.ui.theme.viewmodels.MenuViewModel
import br.edu.ifpb.pdm.oriymenu.ui.theme.viewmodels.RegisterDishViewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    menuViewModel: MenuViewModel = viewModel(),
    onEditDishClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val dishes by menuViewModel.dishes.collectAsState()
    val namesOfDaysOfWeek = menuViewModel.namesOfDaysOfWeek

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SelectComponent(modifier, elements = namesOfDaysOfWeek, onSelect = { index ->
            scope.launch(Dispatchers.IO) {
                menuViewModel.fetchByDayOfWeek(namesOfDaysOfWeek[index])
            }
        })

        Spacer(modifier = Modifier.height(20.dp))

        DishCard(dishes = dishes, onEditDishClick = onEditDishClick)
        Spacer(modifier = Modifier.height(16.dp))

        LaunchedEffect(scope) {
            menuViewModel.fetchByDayOfWeek(namesOfDaysOfWeek[0])
        }
    }
}

@Composable
fun DishCard(
    dishes: List<Dish>,
    menuViewModel: MenuViewModel = viewModel(),
    registerDishViewModel: RegisterDishViewModel = viewModel(),
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
                            registerDishViewModel.updateSelectedDayOfWeek(
                                namesOfDaysOfWeek[menuViewModel.selectedElementIndex.value]
                            )
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
                                        menuViewModel.selectedElementIndex.value], dish)
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

@Composable
fun SelectComponent(
    modifier: Modifier = Modifier,
    elements: List<String>,
    menuViewModel: MenuViewModel = viewModel(),
    onSelect: (Int) -> Unit,
) {
    val isDropDownExpanded by menuViewModel.isDropDownExpanded.collectAsState()
    val currentDayIndex by menuViewModel.selectedElementIndex.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    menuViewModel.showDropDown()
                }
                    .padding(12.dp)
            ) {
                Text(text = elements[currentDayIndex])
                Image(
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = "Arrow Drop Down"
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.padding(horizontal = 16.dp),
            expanded = isDropDownExpanded,
            onDismissRequest = {
                menuViewModel.collapseDropDown()
            }
        ) {
            elements.forEachIndexed { index, name ->
                DropdownMenuItem(text = {
                    Text(text = name)
                },
                    onClick = {
                        menuViewModel.collapseDropDown()
                        menuViewModel.changeSelectedElementIndex(index)
                        onSelect(index)
                    })
            }
        }
    }
}

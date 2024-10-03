package br.edu.ifpb.pdm.oriymenu.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterDishViewModel: ViewModel() {
    // State for the dish name
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    // State for the dish description
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    // State for the dish meal type
    private val _meal = MutableStateFlow("")
    val meal: StateFlow<String> get() = _meal

    // State for the dish image path
    private val _pathToImage = MutableStateFlow("")
    val pathToImage: StateFlow<String> get() = _pathToImage

    // State for the selected day of the week
    private val _selectedDayOfWeek = MutableStateFlow("")
    val selectedDayOfWeek: StateFlow<String> get() = _selectedDayOfWeek

    // Function to update the name state
    fun updateName(newName: String) {
        _name.value = newName
    }

    // Function to update the description state
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    // Function to update the meal state
    fun updateMeal(newMeal: String) {
        _meal.value = newMeal
    }

    // Function to update the path to image state
    fun updatePathToImage(newPath: String) {
        _pathToImage.value = newPath
    }

    // Function to update the selected day of the week
    fun updateSelectedDayOfWeek(newDayOfWeek: String) {
        _selectedDayOfWeek.value = newDayOfWeek
    }

    // Function to save or update a dish
    fun saveOrUpdateDish(dish: Dish?, onRegisterClick: () -> Unit) {
        val newDish = Dish(
            name = _name.value,
            description = _description.value,
            meal = _meal.value,
            pathToImage = _pathToImage.value
        )

        if (dish != null) {  // update an existing dish
            newDish.id = dish.id
            viewModelScope.launch(Dispatchers.IO) {
                DishDAO().update(dish = newDish) {
                    if (it) {  // If the dish was successfully updated
                        onRegisterClick()
                    }
                }
            }
        } else {  // insert a new dish
            viewModelScope.launch(Dispatchers.IO) {
                DishDAO().save(dish = newDish) {
                    if (it) {  // If the dish was successfully saved
                        onRegisterClick()
                    }
                }
            }
        }
    }

}

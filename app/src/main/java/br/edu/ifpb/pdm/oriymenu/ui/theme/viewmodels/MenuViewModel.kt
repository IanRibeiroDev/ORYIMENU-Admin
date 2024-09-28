package br.edu.ifpb.pdm.oriymenu.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.pdm.oriymenu.model.data.Dish
import br.edu.ifpb.pdm.oriymenu.model.data.DishDAO
import br.edu.ifpb.pdm.oriymenu.model.data.WeekDayDAO
import br.edu.ifpb.pdm.oriymenu.model.data.WeekDayNames
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // DAOs for dishes and days of the week
    private val dishDAO = DishDAO()
    private val weekDayDAO = WeekDayDAO()

    // State for dishes
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes.asStateFlow()

    // State for dropdown
    private val _isDropDownExpanded = MutableStateFlow<Boolean>(false)
    val isDropDownExpanded = _isDropDownExpanded.asStateFlow()
    private val _selectedElementIndex = MutableStateFlow<Int>(0)
    val selectedElementIndex = _selectedElementIndex.asStateFlow()

    // State for dialog
    private val _openAlertDialog = MutableStateFlow(false)
    val openAlertDialog: StateFlow<Boolean> = _openAlertDialog.asStateFlow()

    // List of days of the week
    val namesOfDaysOfWeek = listOf(
        WeekDayNames.MONDAY.dayName, WeekDayNames.TUESDAY.dayName, WeekDayNames.WEDNESDAY.dayName,
        WeekDayNames.THURSDAY.dayName, WeekDayNames.FRIDAY.dayName
    )

    /**
     * Fetches the dishes for a given day of the week.
     *
     * This method retrieves the dishes associated with the specified day of the week
     * from the database. It uses a coroutine to perform the database operations on
     * the IO dispatcher. The fetched dishes are then updated in the `_dishes` state flow.
     *
     * @param name The name of the day of the week for which to fetch the dishes.
     */
    suspend fun fetchByDayOfWeek(name: String) {
        withContext(ioDispatcher) {
            weekDayDAO.findByDayOfWeek(name) { returnedDayOfWeek ->
                if (returnedDayOfWeek != null) {

                    val returnedDishes = mutableListOf<Dish>()

                    // Use a counter to ensure all dishes are fetched
                    val totalDishes = returnedDayOfWeek.dishes.size
                    var dishesFetched = 0

                    // Iterate through the list of dish references
                    for (dishRef in returnedDayOfWeek.dishes) {
                        dishDAO.findById(dishRef) { dish ->
                            if (dish != null) {
                                returnedDishes.add(dish)
                            }

                            // Increment the counter and if all dishes are fetched, update the state
                            dishesFetched++
                            if (dishesFetched == totalDishes) {
                                _dishes.value = returnedDishes
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes a dish from a specified day of the week.
     *
     * This method finds the day of the week in the database and removes the specified dish from it.
     * It then updates the day of the week in the database and refreshes the list of dishes for that day.
     * The database operations are performed on the IO dispatcher.
     *
     * @param dayOfWeek The name of the day of the week from which to remove the dish.
     * @param dish The dish to be removed.
     */
    suspend fun removeDishFromDayOfWeek(dayOfWeek: String, dish: Dish) {
        withContext(ioDispatcher) {
            weekDayDAO.findByDayOfWeek(dayOfWeek) { returnedDayOfWeek ->
                if (returnedDayOfWeek != null) {
                    val mutableDishes = returnedDayOfWeek.dishes.toMutableList()
                    mutableDishes.remove(dish.id)
                    returnedDayOfWeek.dishes = mutableDishes

                    weekDayDAO.update(returnedDayOfWeek) { updatedDayOfWeek ->
                        if (updatedDayOfWeek != null) {
                            viewModelScope.launch { fetchByDayOfWeek(dayOfWeek) }
                        }

                    }
                }
            }
        }
    }

    /**
     * Sets the state of the alert dialog.
     *
     * This method updates the `_openAlertDialog` state flow to the specified value.
     *
     * @param value The new state of the alert dialog.
     */
    fun setOpenAlertDialog(value: Boolean) {
        _openAlertDialog.value = value
    }

    /**
     * Collapses the dropdown menu.
     *
     * This method sets the `_isDropDownExpanded` state flow to `false`, collapsing the dropdown menu.
     */
    fun collapseDropDown() {
        _isDropDownExpanded.value = false
    }

    /**
     * Expands the dropdown menu.
     *
     * This method sets the `_isDropDownExpanded` state flow to `true`, expanding the dropdown menu.
     */
    fun showDropDown() {
        _isDropDownExpanded.value = true
    }

    /**
     * Changes the selected element index.
     *
     * This method updates the `_selectedElementIndex` state flow to the specified index.
     *
     * @param index The new index of the selected element.
     */
    fun changeSelectedElementIndex(index: Int) {
        _selectedElementIndex.value = index
    }
}

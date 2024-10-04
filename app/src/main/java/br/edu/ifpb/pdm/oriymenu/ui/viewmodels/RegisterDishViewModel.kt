package br.edu.ifpb.pdm.oriymenu.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.edu.ifpb.pdm.oriymenu.model.data.MealNames
import br.edu.ifpb.pdm.oriymenu.model.data.WeekDayNames
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterDishViewModel: ViewModel() {
    // State for day dropdown
    private val _isDayDropdownExpanded = MutableStateFlow<Boolean>(false)
    val isDayDropdownExpanded = _isDayDropdownExpanded.asStateFlow()

    private val _selectedDayIndex = MutableStateFlow<Int>(0)
    val selectedDayIndex = _selectedDayIndex.asStateFlow()

    // State for meal dropdown
    private val _isMealDropDownExpanded = MutableStateFlow<Boolean>(false)
    val isMealDropDownExpanded = _isMealDropDownExpanded.asStateFlow()

    private val _selectedMealIndex = MutableStateFlow<Int>(0)
    val selectedMealIndex = _selectedMealIndex.asStateFlow()

    // List of days of the week
    val namesOfDaysOfWeek = listOf(
        WeekDayNames.MONDAY.dayName, WeekDayNames.TUESDAY.dayName, WeekDayNames.WEDNESDAY.dayName,
        WeekDayNames.THURSDAY.dayName, WeekDayNames.FRIDAY.dayName
    )

    // List of meal types
    val mealTypes = listOf(MealNames.BREAKFAST.mealName, MealNames.LUNCH.mealName)

    fun showDayDropdown() {
        _isDayDropdownExpanded.value = true
    }

    fun collapseDayDropdown() {
        _isDayDropdownExpanded.value = false
    }

    fun changeSelectedDayIndex(index: Int) {
        _selectedDayIndex.value = index
    }

    fun showMealDropdown() {
        _isMealDropDownExpanded.value = true
    }

    fun collapseMealDropdown() {
        _isMealDropDownExpanded.value = false
    }

    fun changeSelectedMealIndex(index: Int) {
        _selectedMealIndex.value = index
    }
}

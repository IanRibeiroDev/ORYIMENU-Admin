package br.edu.ifpb.pdm.oriymenu.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.pdm.oriymenu.model.data.Address
import br.edu.ifpb.pdm.oriymenu.model.data.Admin
import br.edu.ifpb.pdm.oriymenu.model.data.AdminDAO
import br.edu.ifpb.pdm.oriymenu.model.data.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterAdminViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _zipCode = MutableStateFlow("")
    val zipCode: StateFlow<String> get() = _zipCode

    private val _street = MutableStateFlow("")
    val street: StateFlow<String> get() = _street

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> get() = _number

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> get() = _city

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    private val _address = MutableStateFlow<Address?>(Address())
    val address: StateFlow<Address?> get() = _address

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateZipCode(newZipCode: String) {
        _zipCode.value = newZipCode
    }

    fun updateStreet(newStreet: String) {
        _street.value = newStreet
    }

    fun updateNumber(newNumber: String) {
        _number.value = newNumber
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun updateState(newState: String) {
        _state.value = newState
    }

    // Function to save or update an admin
    fun saveAdmin(onRegisterClick: () -> Unit) {
        val newAdmin = Admin(
            name = name.value,
            email = email.value,
            password = password.value,
        )
        viewModelScope.launch(Dispatchers.IO) {
            AdminDAO().saveAdmin(newAdmin, _address.value!!) {
                onRegisterClick()
            }
        }
    }

    // Function to fetch address based on CEP
    fun fetchAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val address = RetrofitClient.addressService.getAddress(_zipCode.value)
                _address.value = address
            } catch (e: Exception) {
                // Handle the error appropriately
                Log.e("RegisterAdminViewModel", "Error fetching address: ${e.message}")
                _address.value = null
            }
        }
    }
}
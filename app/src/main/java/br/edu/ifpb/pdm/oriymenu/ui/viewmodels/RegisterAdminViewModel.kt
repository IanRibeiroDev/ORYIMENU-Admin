package br.edu.ifpb.pdm.oriymenu.ui.viewmodels

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

//    fun updateZipCode(newZipCode: String) {
//        _zipCode.value = newZipCode
//    }

//    fun updateZipCode(newZipCode: String) {
//        _address.value?.zipCode = newZipCode
//    }
//
//    fun updateAddressStreet(newStreet: String) {
//        _address.value?.street = newStreet
//    }
//
//    fun updateAddressNumber(newNumber: String) {
//        _address.value?.number = newNumber
//    }
//
//    fun updateAddressCity(newCity: String) {
//        _address.value?.city = newCity
//    }
//
//    fun updateAddressState(newState: String) {
//        _address.value?.state = newState
//    }

    fun updateZipCode(zipCode: String) {
        _address.value = _address.value?.copy(zipCode = zipCode) ?: Address(zipCode = zipCode)
    }

    fun updateAddressStreet(street: String) {
        _address.value = _address.value?.copy(street = street) ?: Address(street = street)
    }

    fun updateAddressNumber(number: String) {
        _address.value = _address.value?.copy(number = number) ?: Address(number = number)
    }

    fun updateAddressCity(city: String) {
        _address.value = _address.value?.copy(city = city) ?: Address(city = city)
    }

    fun updateAddressState(state: String) {
        _address.value = _address.value?.copy(state = state) ?: Address(state = state)
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
                val address = RetrofitClient.enderecoService.getAddress(_zipCode.value)
                _address.value = address
            } catch (e: Exception) {
                // Handle the error appropriately
                _address.value = null
            }
        }
    }
}
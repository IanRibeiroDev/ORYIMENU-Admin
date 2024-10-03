package br.edu.ifpb.pdm.oriymenu.model.data

data class Address(
    var zipCode: String = "",  // cep
    var street: String = "",  // logradouro
    var number: String = "",  // numero
    var city: String = "",  // localidade
    var state: String = "",  // uf
)
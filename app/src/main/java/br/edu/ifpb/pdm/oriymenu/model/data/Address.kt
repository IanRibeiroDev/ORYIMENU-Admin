package br.edu.ifpb.pdm.oriymenu.model.data

data class Address(
    val cep: String = "",
    val logradouro: String = "",
    val complemento: String = "",
    val unidade: String = "",
    val bairro: String = "",
    val localidade: String = "",
    val uf: String = "",
    val ibge: String = "",
    val gia: String = "",
    val ddd: String = "",
    val siafi: String = ""
)
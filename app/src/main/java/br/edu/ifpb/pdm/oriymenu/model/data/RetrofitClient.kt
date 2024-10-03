package br.edu.ifpb.pdm.oriymenu.model.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // const - já é conhecida em tempo de compilação
    // val - somente leitura
    private const val BASE_URL = "http://viacep.com.br/ws/"

    val enderecoService: AddressIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AddressIF::class.java)
    }
}
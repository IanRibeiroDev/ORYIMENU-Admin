package br.edu.ifpb.pdm.oriymenu.model.data

import retrofit2.http.GET
import retrofit2.http.Path

interface AddressIF {

    @GET("{cep}/json")
    suspend fun getAddress(@Path("cep") customerId: String): Address
}
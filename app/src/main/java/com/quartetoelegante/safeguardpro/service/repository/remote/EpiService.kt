package com.quartetoelegante.safeguardpro.service.repository.remote

import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EpiService {
    @GET("select_epi")
    suspend fun getEpis(): List<Epi>

    @Multipart
    @POST("add_epi")
    suspend fun createEpi(
        @Part("nome") nome: RequestBody,
        @Part("validade") validade: RequestBody,
        @Part("instrucao") instrucao: RequestBody
    ): Response<Epi>

    @GET("get_epi/{epi_id}")
    suspend fun getEpiById(@Path("epi_id") id: Int): Response<List<Epi>>

    @Multipart
    @PUT("update_epi/{epi_id}")
    suspend fun updateEpi(
        @Path("epi_id") epiId: Int,
        @Part("nome") nome: RequestBody,
        @Part("validade") validade: RequestBody,
        @Part("instrucao") instrucao: RequestBody
    ): Response<Epi>

    @DELETE("delete_epi/{epi_id}")
    suspend fun deleteEpiById(@Path("epi_id") id: Int): Response<Epi>
}
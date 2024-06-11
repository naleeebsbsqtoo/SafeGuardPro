package com.quartetoelegante.safeguardpro.service.repository.remote

import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Epi
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EntregaService {
    @GET("select_entrega")
    suspend fun getEntregas(): List<Entrega>

    @Multipart
    @POST("add_entrega")
    suspend fun createEntrega(
        @Part("data") data: RequestBody,
        @Part("validade") validade: RequestBody,
        @Part("epi") epi: RequestBody,
        @Part("funcionario") funcionario: RequestBody
    ): Response<Entrega>

    @GET("get_entrega/{entrega_id}")
    suspend fun getEntregaById(@Path("entrega_id") id: Int): Response<List<Entrega>>

    @Multipart
    @PUT("update_entrega/{entrega_id}")
    suspend fun updateEntrega(
        @Path("entrega_id") id: Int,
        @Part("data") data: RequestBody,
        @Part("validade") validade: RequestBody,
        @Part("epi") epi: RequestBody,
        @Part("funcionario") funcionario: RequestBody
    ): Response<Entrega>

    @DELETE("delete_entrega/{epi_id}")
    suspend fun deleteEntregaById(@Path("entrega_id") id: Int): Response<Entrega>
}
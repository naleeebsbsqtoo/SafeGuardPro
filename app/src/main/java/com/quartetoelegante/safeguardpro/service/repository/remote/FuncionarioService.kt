package com.quartetoelegante.safeguardpro.service.repository.remote


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

interface FuncionarioService {

    @GET("selectfuncionario")
    suspend fun getFuncionarios(): List<Funcionario>

    @Multipart
    @POST("add_funcionario")
    suspend fun createFunc(
        @Part("nome") nome: RequestBody,
        @Part("cpf") cpf: RequestBody
    ): Response<Funcionario>

    @GET("get_funcionario/{funcionario_id}")
    suspend fun getFuncById(@Path("funcionario_id") id: Int): Response<List<Funcionario>>

    @Multipart
    @PUT("update_funcionario/{funcionario_id}")
    suspend fun updateFunc(
        @Path("funcionario_id") funcionarioId: Int,
        @Part("nome") nome: RequestBody,
        @Part("cpf") cpf: RequestBody
    ): Response<Funcionario>

    @DELETE("delete_funcionario/{funcionario_id}")
    suspend fun deleteFuncById(@Path("funcionario_id") id: Int):Response<Funcionario>
}

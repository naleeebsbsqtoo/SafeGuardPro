package com.quartetoelegante.safeguardpro.service.repository

import android.content.Context
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.service.repository.remote.FuncionarioService
import com.quartetoelegante.safeguardpro.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class FuncionarioRepository(context: Context) {
    private val mRemote = RetrofitClient.createService(FuncionarioService::class.java)
    private val funcionarioEmpty = Funcionario(0, "", "")

    suspend fun getFuncionarios(): List<Funcionario> {
        return mRemote.getFuncionarios()
    }

    suspend fun insertFunc(funcionario: Funcionario): Funcionario {
        return mRemote.createFunc(
            nome = funcionario.nome.toRequestBody("text/plain".toMediaTypeOrNull()),
            cpf = funcionario.cpf.toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: funcionarioEmpty
    }

    suspend fun getFuncionario(id: Int): Funcionario {
        val response = mRemote.getFuncById(id)
        return if (response.isSuccessful) {
            response.body()?.first() ?: funcionarioEmpty
        } else {
            funcionarioEmpty
        }
    }

    suspend fun updateFunc(funcionario: Funcionario): Funcionario {
        return mRemote.updateFunc(
            funcionarioId = funcionario.id,
            nome = funcionario.nome.toRequestBody("text/plain".toMediaTypeOrNull()),
            cpf = funcionario.cpf.toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: funcionarioEmpty
    }

    suspend fun deleteFunc(id: Int): Boolean {
        return mRemote.deleteFuncById(id).isSuccessful
    }
}
package com.quartetoelegante.safeguardpro.service.repository

import android.content.Context
import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.repository.remote.EntregaService
import com.quartetoelegante.safeguardpro.service.repository.remote.EpiService
import com.quartetoelegante.safeguardpro.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EntregaRepository(context: Context) {
    private val mRemote = RetrofitClient.createService(EntregaService::class.java)
    private val entregaEmpty = Entrega(0, "", "", "", "", 0, 0)
    suspend fun getEntregas(): List<Entrega> {
        return mRemote.getEntregas()
    }
    suspend fun insertEntregas(entrega: Entrega): Entrega {
        return mRemote.createEntrega(
            data = entrega.data.toRequestBody("text/plain".toMediaTypeOrNull()),
            validade = entrega.validade.toRequestBody("text/plain".toMediaTypeOrNull()),
            epi = entrega.idEpi.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            funcionario = entrega.idFuncionario.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        ).body() ?: entregaEmpty
    }

    suspend fun getEntrega(id: Int): Entrega {
        val response = mRemote.getEntregaById(id)
        return if (response.isSuccessful) {
            response.body()?.first() ?: entregaEmpty
        } else {
            entregaEmpty
        }
    }

    suspend fun updateEntrega(entrega: Entrega): Entrega {
        return mRemote.updateEntrega(
            data = entrega.data.toRequestBody("text/plain".toMediaTypeOrNull()),
            validade = entrega.validade.toRequestBody("text/plain".toMediaTypeOrNull()),
            epi = entrega.idEpi.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            funcionario = entrega.idFuncionario.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            id = entrega.id
        ).body() ?: entregaEmpty
    }

    suspend fun deleteEntrega(id: Int): Boolean {
        return mRemote.deleteEntregaById(id).isSuccessful
    }
}
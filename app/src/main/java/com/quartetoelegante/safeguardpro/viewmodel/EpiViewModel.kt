package com.quartetoelegante.safeguardpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.service.repository.EpiRepository
import com.quartetoelegante.safeguardpro.service.repository.FuncionarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpiViewModel(apllication: Application): AndroidViewModel(apllication){
    private val repository = EpiRepository(apllication)

    private val mEpiList = MutableLiveData<List<Epi>>()
    val epiList: LiveData<List<Epi>> = mEpiList

    private val mUpdatedEpi = MutableLiveData<Epi>()
    val updatedEpi: MutableLiveData<Epi> = mUpdatedEpi

    private val mCreatedEpi= MutableLiveData<Epi>()
    val createdEpi: LiveData<Epi> = mCreatedEpi

    private val mEpi = MutableLiveData<Epi>()
    val epi: LiveData<Epi> = mEpi

    private val mDeleteEpi = MutableLiveData<Boolean>()
    val deleteEpi: LiveData<Boolean> = mDeleteEpi

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun loadEpis(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mEpiList.postValue(repository.getEpis())
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun update (epi: Epi){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val updatedEpi = repository.updateEpi(epi)
                mUpdatedEpi.postValue(updatedEpi)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }


    fun insert(epi: Epi){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val createdEpi = repository.insertEpi(epi)
                mCreatedEpi.postValue(createdEpi)
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }


    fun getEpi(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mEpi.postValue(repository.getEpi(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mDeleteEpi.postValue(repository.deleteEpi(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}

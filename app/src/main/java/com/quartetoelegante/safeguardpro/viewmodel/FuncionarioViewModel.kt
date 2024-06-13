package com.quartetoelegante.safeguardpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.service.repository.FuncionarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FuncionarioViewModel (apllication: Application): AndroidViewModel(apllication){
    private val repository = FuncionarioRepository(apllication)

    private val mFuncionarioList = MutableLiveData<List<Funcionario>>()
    val funcionarioList: LiveData<List<Funcionario>> = mFuncionarioList

    private val mUpdatedFuncionario = MutableLiveData<Funcionario>()
    val updatedFuncionario: LiveData<Funcionario> = mUpdatedFuncionario

    private val mFuncionario = MutableLiveData<Funcionario>()
    val funcionario: LiveData<Funcionario> = mFuncionario

    private val mDeleteFunc = MutableLiveData<Boolean>()
    val deleteFuncionario: LiveData<Boolean> = mDeleteFunc

    private val mCreatedFuncionario = MutableLiveData<Funcionario>()
    val cratedfuncionario: LiveData<Funcionario> = mCreatedFuncionario

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    fun load(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mFuncionarioList.postValue(repository.getFuncionarios())
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun insert(funcionario: Funcionario){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val createdFuncionario = repository.insertFunc(funcionario)
                mCreatedFuncionario.postValue(createdFuncionario)
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun update (funcionario: Funcionario){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val updatedFuncionario = repository.updateFunc(funcionario)
                mUpdatedFuncionario.postValue(updatedFuncionario)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun getFunc(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mFuncionario.postValue(repository.getFuncionario(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun getFuncByCpf(cpf: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mFuncionario.postValue(repository.getFuncionarioByCpf(cpf))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                mDeleteFunc.postValue(repository.deleteFunc(id))
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}

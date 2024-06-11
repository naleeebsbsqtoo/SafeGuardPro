package com.quartetoelegante.safeguardpro.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quartetoelegante.safeguardpro.databinding.ListItemEpiBinding
import com.quartetoelegante.safeguardpro.databinding.ListItemFuncBinding
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Funcionario

class FuncionarioAdapter(funcionarios: List<Funcionario>?, private val clickListListener: (Funcionario) -> Unit):
    RecyclerView.Adapter<FuncionarioAdapter.FuncionarioViewHolder>(){

    //criar uma lista vazia de pessoas
    private var funcionarioList: List<Funcionario> = arrayListOf()

    class FuncionarioViewHolder(private val binding: ListItemFuncBinding) : RecyclerView.ViewHolder(
        binding.root){

        //carrega as informaçoes da pessoa na lista
        fun bind(funcionario: Funcionario, clickListListener: (Funcionario) -> Unit){
            binding.tvnomefunc.text = funcionario.nome
            binding.tvcpf.text = funcionario.cpf


            //configura o click de algum item da lista
            binding.root.setOnClickListener{
                clickListListener(funcionario)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuncionarioViewHolder {
        //configurar o binding da lista
        val listItemFuncBinding = ListItemFuncBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FuncionarioViewHolder(listItemFuncBinding)
    }

    override fun getItemCount(): Int {
        return funcionarioList.count()
    }

    override fun onBindViewHolder(holder: FuncionarioViewHolder, position: Int) {
        holder.bind(funcionarioList[position], clickListListener)
    }

    //carrega a lista de pessoass para serem exibidas
    fun updateFuncionario(list: List<Funcionario>){
        funcionarioList = list
        notifyDataSetChanged()
    }
}
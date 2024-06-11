package com.quartetoelegante.safeguardpro.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quartetoelegante.safeguardpro.databinding.ListItemEpiBinding
import com.quartetoelegante.safeguardpro.service.model.Epi

class EpiAdapter(epis: List<Epi>?, private val clickListListener: (Epi) -> Unit):
    RecyclerView.Adapter<EpiAdapter.EpiViewHolder>(){

    //criar uma lista vazia de pessoas
    private var epiList: List<Epi> = arrayListOf()

    class EpiViewHolder(private val binding: ListItemEpiBinding) : RecyclerView.ViewHolder(
        binding.root){

        //carrega as informaçoes da pessoa na lista
        fun bind(epi: Epi, clickListListener: (Epi) -> Unit){
            binding.tvnomeepi.text = epi.nome
            binding.tvvalidade.text = epi.validade

            //configura o click de algum item da lista
            binding.root.setOnClickListener{
                clickListListener(epi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpiViewHolder {
        //configurar o binding da lista
        val listItemEpiBinding = ListItemEpiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpiViewHolder(listItemEpiBinding)
    }

    override fun getItemCount(): Int {
        return epiList.count()
    }

    override fun onBindViewHolder(holder: EpiViewHolder, position: Int) {
        holder.bind(epiList[position], clickListListener)
    }

    //carrega a lista de pessoass para serem exibidas
    fun updateEpis(list: List<Epi>){
        epiList = list
        notifyDataSetChanged()
    }
}
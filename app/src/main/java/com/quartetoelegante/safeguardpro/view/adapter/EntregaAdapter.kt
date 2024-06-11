package com.quartetoelegante.safeguardpro.view.adapter

import android.telephony.TelephonyCallback.EmergencyNumberListListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quartetoelegante.safeguardpro.databinding.ListItemEntregaBinding
import com.quartetoelegante.safeguardpro.databinding.ListItemEpiBinding
import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Epi

class EntregaAdapter(entregas: List<Entrega>?, private val clickListListener: (Entrega) -> Unit):
    RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder>(){

        private var entregaList: List<Entrega> = arrayListOf()

    class EntregaViewHolder(private val binding: ListItemEntregaBinding) : RecyclerView.ViewHolder(
        binding.root){

        //carrega as informaçoes da pessoa na lista
        fun bind(entrega: Entrega, clickListListener: (Entrega) -> Unit){
            binding.tvnomeentrega.text = entrega.idEpi.toString()
            binding.tvdataentrega.text = entrega.data
            binding.tvdatavalidade.text = entrega.validade
            binding.tvnomefuncionario.text = entrega.idFuncionario.toString()

            //configura o click de algum item da lista
            binding.root.setOnClickListener{
                clickListListener(entrega)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregaAdapter.EntregaViewHolder {
        //configurar o binding da lista
        val listItemEntregaBinding = ListItemEntregaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntregaAdapter.EntregaViewHolder(listItemEntregaBinding)
    }

    override fun getItemCount(): Int {
        return entregaList.count()
    }

    override fun onBindViewHolder(holder: EntregaAdapter.EntregaViewHolder, position: Int) {
        holder.bind(entregaList[position], clickListListener)
    }

    //carrega a lista de pessoas para serem exibidas
    fun updateEpis(list: List<Entrega>){
        entregaList = list
        notifyDataSetChanged()
    }
}
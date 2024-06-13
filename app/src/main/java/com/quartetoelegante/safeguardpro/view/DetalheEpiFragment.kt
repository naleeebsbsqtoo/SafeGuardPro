package com.quartetoelegante.safeguardpro.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.quartetoelegante.safeguardpro.R
import com.quartetoelegante.safeguardpro.databinding.FragmentDetalheEpiBinding
import com.quartetoelegante.safeguardpro.view.adapter.EntregaAdapter
import com.quartetoelegante.safeguardpro.view.adapter.EpiAdapter
import com.quartetoelegante.safeguardpro.viewmodel.EntregaViewModel
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel

class DetalheEpiFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: EpiViewModel by viewModels()


    private var _binding: FragmentDetalheEpiBinding? = null
    private val binding: FragmentDetalheEpiBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalheEpiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.getEpi(it.getInt("epiId"))
        }

        viewModel.epi.observe(viewLifecycleOwner) { epi ->
            binding.tvnomeepi.text = epi.nome
            binding.tvvalidade.text = epi.validade
//           TODO Fazer relacionamento com a entrega ou excluir o campo
//            binding.tventrega.text = epi.entrega
            binding.tvinstrucoes.text = epi.instrucao
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Log.e( "erro","Epis: $it")
        }
    }
}
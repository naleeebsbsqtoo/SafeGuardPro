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
import com.quartetoelegante.safeguardpro.databinding.FragmentInicialfunBinding
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Login
import com.quartetoelegante.safeguardpro.view.adapter.EpiAdapter
import com.quartetoelegante.safeguardpro.viewmodel.EntregaViewModel
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel

class InicialfunFragment : Fragment() {

    private var _binding: FragmentInicialfunBinding? = null
    private val binding: FragmentInicialfunBinding get() = _binding!!

    private val viewModel: EntregaViewModel by viewModels()
    private val viewModelEpi: EpiViewModel by viewModels()

    //Chamar o adapter
    private lateinit var adapter: EpiAdapter
    private val episFuncionario = mutableListOf<Epi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicialfunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //clicar em algum item da lista
        adapter = EpiAdapter(episFuncionario) { epi ->
            val epiBundle = Bundle()
            epiBundle.putInt("epiId", epi.id)
            arguments = epiBundle
            findNavController().navigate(R.id.detalheEpiFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvEpis
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.entregaList.observe(viewLifecycleOwner) { listEntregas ->
            val entregasFuncionario = listEntregas.filter { it.idFuncionario == Login.userId }
            entregasFuncionario.forEach {
                viewModelEpi.getEpi(it.idEpi)
            }
            Toast.makeText(requireContext(), "Epis: $listEntregas", Toast.LENGTH_LONG).show()
        }

        viewModelEpi.epi.observe(viewLifecycleOwner) { epi ->
            episFuncionario.add(epi)
            adapter.updateEpis(episFuncionario)
            Toast.makeText(requireContext(), "Epis: $epi", Toast.LENGTH_LONG).show()
        }

        //carregar entregas cadastradas
        viewModel.loadEntregas()

        viewModel.erro.observe(viewLifecycleOwner) {
            Log.e( "erro","Epis: $it")
        }
    }
}
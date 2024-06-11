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
import com.quartetoelegante.safeguardpro.databinding.FragmentInventarioBinding
import com.quartetoelegante.safeguardpro.view.adapter.EpiAdapter
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel

class InventarioFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: EpiViewModel by viewModels()

    //chamar o adapter
    private lateinit var adapter: EpiAdapter

    private var _binding: FragmentInventarioBinding? = null
    private val binding: FragmentInventarioBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em algum item da lista
        adapter = EpiAdapter(viewModel.epiList.value) { epi ->
            val epiBundle = Bundle()
            epiBundle.putInt("epiId", epi.id)
            arguments = epiBundle
            findNavController().navigate(R.id.inventarioFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvInventario
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.epiList.observe(viewLifecycleOwner) {
            adapter.updateEpis(it)
            Toast.makeText(requireContext(), "Epis: $it", Toast.LENGTH_LONG).show()
        }

        //carregar epis cadastradas
        viewModel.loadEpis()
        Toast.makeText(requireContext(), "passou load", Toast.LENGTH_LONG).show()

        viewModel.erro.observe(viewLifecycleOwner) {
            Log.e( "erro","Epis: $it")
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.cadastroepiFragment)
        }
    }
}
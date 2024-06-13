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
import com.quartetoelegante.safeguardpro.databinding.FragmentEntregasBinding
import com.quartetoelegante.safeguardpro.databinding.FragmentFuncionariosBinding
import com.quartetoelegante.safeguardpro.view.adapter.EntregaAdapter
import com.quartetoelegante.safeguardpro.view.adapter.FuncionarioAdapter
import com.quartetoelegante.safeguardpro.viewmodel.EntregaViewModel
import com.quartetoelegante.safeguardpro.viewmodel.FuncionarioViewModel


class EntregasFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: EntregaViewModel by viewModels()

    //chamar o adapter
    private lateinit var adapter: EntregaAdapter

    private var _binding: FragmentEntregasBinding? = null
    private val binding: FragmentEntregasBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntregasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em algum item da lista
        adapter = EntregaAdapter(viewModel.entregaList.value) { entrega ->
            val entregaBundle = Bundle()
            entregaBundle.putInt("entregaId", entrega.id)
            arguments = entregaBundle
            findNavController().navigate(R.id.entregaFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvInventarioEntrega
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.entregaList.observe(viewLifecycleOwner) {
            adapter.updateEpis(it)
            Toast.makeText(requireContext(), "Entregas: $it", Toast.LENGTH_LONG).show()
        }

        //carregar entregas cadastradas
        viewModel.loadEntregas()

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e( "erro","Erro Entregas: $it")
        }

        binding.btnAdd3.setOnClickListener {
            findNavController().navigate(R.id.entregaFragment)
        }
    }

}
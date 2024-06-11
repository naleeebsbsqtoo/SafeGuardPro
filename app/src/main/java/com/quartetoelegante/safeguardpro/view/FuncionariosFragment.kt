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
import com.quartetoelegante.safeguardpro.databinding.FragmentFuncionariosBinding
import com.quartetoelegante.safeguardpro.view.adapter.FuncionarioAdapter
import com.quartetoelegante.safeguardpro.viewmodel.FuncionarioViewModel

class FuncionariosFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: FuncionarioViewModel by viewModels()

    //chamar o adapter
    private lateinit var adapter: FuncionarioAdapter

    private var _binding: FragmentFuncionariosBinding? = null
    private val binding: FragmentFuncionariosBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFuncionariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //clicar em algum item da lista
        adapter = FuncionarioAdapter(viewModel.funcionarioList.value) { funcionario ->
            val funcionarioBundle = Bundle()
            funcionarioBundle.putInt("funcionarioId", funcionario.id)
            arguments = funcionarioBundle
            findNavController().navigate(R.id.funcionariosFragment, arguments)
        }

        //Configurar a recycler
        val recycler = binding.rvFuncionarios
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        //observar para adicionar um item na lista quando for adicionado
        viewModel.funcionarioList.observe(viewLifecycleOwner) {
            adapter.updateFuncionario(it)
            Toast.makeText(requireContext(), "Funcionarios: $it", Toast.LENGTH_LONG).show()
        }

        //carregar pessoas cadastradas
        viewModel.load()

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e( "erro","Erro Funcionarios: $it")
        }

        binding.btnAdd2.setOnClickListener {
            findNavController().navigate(R.id.cadastrofunFragment)
        }
    }
}
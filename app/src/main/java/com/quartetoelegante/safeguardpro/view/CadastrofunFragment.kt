package com.quartetoelegante.safeguardpro.view

import android.app.AlertDialog
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
import com.quartetoelegante.safeguardpro.databinding.FragmentCadastrofunBinding
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.view.adapter.FuncionarioAdapter
import com.quartetoelegante.safeguardpro.viewmodel.FuncionarioViewModel

class CadastrofunFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: FuncionarioViewModel by viewModels()

    //criar o binding
    private var _binding: FragmentCadastrofunBinding? = null
    private val binding: FragmentCadastrofunBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //configurar o binding
        _binding = FragmentCadastrofunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.getFunc(it.getInt("funcionarioId"))
        }

        //navegar para a lista de funcionario
        binding.btnCadastrofun.setOnClickListener {
            val nome = binding.edtNome.editableText.toString()
            val cpf = binding.edtcpf.editableText.toString()

            if (nome != "" && cpf != ""){
                val funcionario = Funcionario(
                    nome = nome,
                    cpf = cpf
                )

                viewModel.funcionario.value?.let {
                    funcionario.id = it.id
                    viewModel.update(funcionario)
                }?: run {
                    viewModel.insert(funcionario)
                }

                binding.edtNome.editableText.clear()
                binding.edtcpf.editableText.clear()

            }else{
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }

            findNavController().navigate(R.id.funcionariosFragment)
        }

        binding.btnExcluirfun.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de funcionario")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.funcionario.value?.id ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        viewModel.funcionario.observe(viewLifecycleOwner) { funcionario ->
            binding.edtNome.setText(funcionario.nome)
            binding.edtcpf.setText(funcionario.cpf)

            binding.btnExcluirfun.visibility = View.VISIBLE
        }

        viewModel.updatedFuncionario.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.deleteFuncionario.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro Funcionario", it)
        }
    }
}
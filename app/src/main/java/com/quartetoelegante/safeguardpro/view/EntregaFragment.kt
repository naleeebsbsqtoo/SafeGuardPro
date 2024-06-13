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
import com.quartetoelegante.safeguardpro.R
import com.quartetoelegante.safeguardpro.databinding.FragmentEntregaBinding
import com.quartetoelegante.safeguardpro.service.model.Entrega
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.viewmodel.EntregaViewModel
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel
import com.quartetoelegante.safeguardpro.viewmodel.FuncionarioViewModel

class EntregaFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: EntregaViewModel by viewModels()
    private val viewModelFunc: FuncionarioViewModel by viewModels()
    private val viewModelEpi: EpiViewModel by viewModels()

    private var _binding: FragmentEntregaBinding? = null
    private val binding: FragmentEntregaBinding get() = _binding!!

    private lateinit var epiByCa: Epi
    private lateinit var funcionarioByCpf: Funcionario

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntregaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Carregar a pessoa caso tenha selecionado
        arguments?.let {
            viewModel.getEntrega(it.getInt("entregaId"))
        }

        //navegar para a tela de detalhes
        binding.btnEntregar.setOnClickListener {
            val nome = binding.edtnome.editableText.toString()
            val tempo = binding.edttempouso.editableText.toString()
            val cpf = binding.edtcpf.editableText.toString()
            val ca = binding.edtCa.editableText.toString().toInt()

            if (nome != "" && cpf != "" && tempo != "" && ca != 0) {

                viewModelFunc.getFuncByCpf(cpf)
                viewModelEpi.getEpiByCa(ca)

                val entrega = Entrega(
                    nome = nome,
                    tempo_uso = tempo,
                    idFuncionario = funcionarioByCpf.id,
                    idEpi = epiByCa.id
                )

                viewModel.entrega.value?.let {
                    entrega.id = it.id
                    viewModel.update(entrega)
                } ?: run {
                    viewModel.insert(entrega)
                }

                binding.edtnome.editableText.clear()
                binding.edttempouso.editableText.clear()
                binding.edtcpf.editableText.clear()
                binding.edtCa.editableText.clear()
            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnExcluirentrega.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de entregas")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.entrega.value?.id ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }

        viewModel.entrega.observe(viewLifecycleOwner) { entrega ->
            binding.edtnome.setText(entrega.nome)
            binding.edtcpf.setText(entrega.idFuncionario)
            binding.edtCa.setText(entrega.idEpi)
            // TODO fazer relacionamento com funcionario e epi viewmodel para exibir ca e cpf
            binding.edtvalidade.setText(entrega.validade)
            binding.edtdataentrega.setText(entrega.data)
            binding.edttempouso.setText(entrega.tempo_uso)

            binding.btnExcluirentrega.visibility = View.VISIBLE
        }

        viewModel.updatedEntrega.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.deleteEntrega.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro de entrega", it)
        }

        viewModelEpi.epi.observe(viewLifecycleOwner) {
            epiByCa = it
        }

        viewModelFunc.funcionario.observe(viewLifecycleOwner) {
            funcionarioByCpf = it
        }
    }
}
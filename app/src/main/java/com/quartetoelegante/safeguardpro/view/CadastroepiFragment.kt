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
import com.quartetoelegante.safeguardpro.databinding.FragmentCadastroepiBinding
import com.quartetoelegante.safeguardpro.service.model.Epi
import com.quartetoelegante.safeguardpro.service.model.Funcionario
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel
import com.quartetoelegante.safeguardpro.viewmodel.FuncionarioViewModel

class CadastroepiFragment : Fragment() {

    //chamar viewmodel
    private val viewModel: EpiViewModel by viewModels()

    private var _binding: FragmentCadastroepiBinding? = null
    private val binding: FragmentCadastroepiBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroepiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navegar para a lista de epi
        binding.btnCadastroepi.setOnClickListener {
            val item = binding.edtNome.editableText.toString()
            val validade = binding.edtvalidade.editableText.toString()
            val tempo = binding.edttempouso.editableText.toString()
            val uso = binding.edtuso.editableText.toString()

            if (item != "" && validade != "" && tempo !="" && uso != "") {
                val epi = Epi(
                    nome = item,
                    validade = validade,
                    instrucao = uso,
                    tempo_uso = tempo
                )

                viewModel.epi.value?.let {
                    epi.id = it.id
                    viewModel.update(epi)
                } ?: run {
                    viewModel.insert(epi)
                }

                binding.edtNome.editableText.clear()
                binding.edtvalidade.editableText.clear()
                binding.edttempouso.editableText.clear()
                binding.edtuso.editableText.clear()

            } else {
                Toast.makeText(requireContext(), "Digite os dados", Toast.LENGTH_LONG).show()
            }
            findNavController().navigate(R.id.inventarioFragment)
        }

        binding.btnExcluirepi.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Exclusão de EPIs")
                .setMessage("Você realmente deseja excluir?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.delete(viewModel.epi.value?.id ?: 0)
                    findNavController().navigateUp()
                }
                .setNegativeButton("Não") { _, _ -> }
                .show()
        }
        viewModel.epi.observe(viewLifecycleOwner) { epi ->
            binding.edtNome.setText(epi.nome)
            binding.edtvalidade.setText(epi.validade)
            binding.edttempouso.setText(epi.tempo_uso)
            binding.edtuso.setText(epi.instrucao)

            binding.btnExcluirepi.visibility = View.VISIBLE
        }

        viewModel.updatedEpi.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.deleteEpi.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.erro.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Erro $it", Toast.LENGTH_LONG).show()
            Log.e("Erro Cadastro EPI", it)
        }
    }
}
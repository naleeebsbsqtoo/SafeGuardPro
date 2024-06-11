package com.quartetoelegante.safeguardpro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.quartetoelegante.safeguardpro.R
import com.quartetoelegante.safeguardpro.databinding.FragmentInicialBinding
import com.quartetoelegante.safeguardpro.databinding.FragmentInicialfunBinding
import com.quartetoelegante.safeguardpro.view.adapter.EpiAdapter
import com.quartetoelegante.safeguardpro.viewmodel.EpiViewModel

class InicialfunFragment : Fragment() {

    private var _binding: FragmentInicialfunBinding? = null
    private val binding: FragmentInicialfunBinding get() = _binding!!

    private val viewModel: EpiViewModel by viewModels()

    //Chamar o adapter
    private lateinit var adapter: EpiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicialfunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding
    }

}

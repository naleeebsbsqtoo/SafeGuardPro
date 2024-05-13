package com.quartetoelegante.safeguardpro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.quartetoelegante.safeguardpro.R
import com.quartetoelegante.safeguardpro.databinding.FragmentInicialfunBinding
import com.quartetoelegante.safeguardpro.databinding.FragmentInventarioBinding
import com.quartetoelegante.safeguardpro.databinding.FragmentListfunBinding

class ListfunFragment : Fragment() {

    private var _binding: FragmentListfunBinding? = null
    private val binding: FragmentListfunBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListfunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd2.setOnClickListener {
            findNavController().navigate(R.id.cadastrofunFragment)
    }
    }
}
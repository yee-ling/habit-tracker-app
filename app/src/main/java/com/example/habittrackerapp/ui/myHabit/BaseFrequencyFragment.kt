package com.example.habittrackerapp.ui.myHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittrackerapp.databinding.FragmentBaseFrequencyBinding
import com.example.habittrackerapp.ui.adapter.FrequencyAdapter

abstract class BaseFrequencyFragment : Fragment() {
    protected lateinit var binding: FragmentBaseFrequencyBinding
    protected abstract val viewModel: BaseFrequencyViewModel
    protected lateinit var adapter: FrequencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseFrequencyBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun setupAdapter() {
        adapter = FrequencyAdapter(habits = emptyList())
        binding.rvMyHabit.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyHabit.adapter = adapter
    }
}
package com.example.habittrackerapp.ui.manageHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittrackerapp.databinding.FragmentBaseManageBinding

abstract class BaseManageFragment : Fragment() {
    protected lateinit var binding: FragmentBaseManageBinding
    protected abstract val viewModel: BaseManageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseManageBinding.inflate(inflater, container, false)
        return binding.root
    }
}
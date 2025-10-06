package com.example.habittrackerapp.ui.manageHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.habittrackerapp.databinding.FragmentBaseManageBinding
import kotlinx.coroutines.launch

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
    fun habitRepeatCounter() {
        binding.ivMinus.setOnClickListener {
            viewModel.decrement()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.increment()
        }
        lifecycleScope.launch {
            viewModel.count.collect {
                binding.tvCount.text = it.toString()
            }
        }
    }
    abstract fun submit()
}
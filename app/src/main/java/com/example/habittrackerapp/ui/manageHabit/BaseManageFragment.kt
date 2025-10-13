package com.example.habittrackerapp.ui.manageHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.FragmentBaseManageBinding
import com.google.android.material.snackbar.Snackbar
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
    abstract fun submit()
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
    fun showErrorMessage() {
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
                snackbar.show()
            }
        }
    }
}
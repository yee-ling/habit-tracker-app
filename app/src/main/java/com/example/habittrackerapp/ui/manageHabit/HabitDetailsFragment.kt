package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.databinding.FragmentHabitDetailsBinding
import kotlinx.coroutines.launch

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private val viewModel: HabitDetailsViewModel by viewModels()
    private val args: HabitDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHabitById(args.habitId)
        lifecycleScope.launch {
            viewModel.habit.collect {
                binding.run {
                    tvName.text = it?.name.toString()
                    tvFrequency.text = it?.frequency.toString()
                    tvRepeats.text = it?.repeatsPerDay.toString()
//                    tvStartDate.text = it?.startDate.toString()
                }
            }
        }
    }
}
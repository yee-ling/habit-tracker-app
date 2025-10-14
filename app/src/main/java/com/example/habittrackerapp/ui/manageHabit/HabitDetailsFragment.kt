package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.FragmentHabitDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

class HabitDetailsFragment : Fragment() {
    private lateinit var binding: FragmentHabitDetailsBinding
    private val viewModel: HabitDetailsViewModel by viewModels {
        HabitDetailsViewModel.Factory
    }
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
        viewModel.getHabitByIdWithProgressByDate(args.habitId,args.selectedDate)
        observeHabitDetails()
        updateCurrentStreak()
        updateYourBestStreak()
        navigateToEditHabit()
        deleteHabit()
    }
    fun observeHabitDetails() {
        lifecycleScope.launch {
            viewModel.habit.collect {
                binding.run {
                    tvName.text = it?.habit?.name.toString()
                    tvFrequency.text = it?.habit?.frequency.toString()
                    tvCategory.text = it?.habit?.category.toString()
                    val progress = it?.progress?.find { it.habitId == args.habitId }
                    tvCurrentProgress.text = (progress?.progress?:0).toString()
                    cpCurrentProgress.max = it?.habit?.repeatsPerDay?:0
                    cpCurrentProgress.progress = progress?.progress?:0
                    tvRepeats.text = it?.habit?.repeatsPerDay.toString()
                    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    tvStartDate.text = dateFormat.format(Date(it?.habit?.startDate?:System.currentTimeMillis()))
                    tvEndDate.text = if (it?.habit?.endDate != null) {
                        dateFormat.format(Date(it.habit.endDate))
                    } else {
                        getString(R.string.no_end_date)
                    }
                }
            }
        }
    }
    fun updateCurrentStreak() {
        lifecycleScope.launch {
            binding.tvCurrentStreak.text = viewModel.getCurrentStreak(args.habitId).toString()
        }
    }
    fun updateYourBestStreak() {
        lifecycleScope.launch {
            binding.tvYourBest.text = viewModel.getLongestStreak(args.habitId).toString()
        }
    }
    fun navigateToEditHabit() {
        binding.mbEdit.setOnClickListener {
            val action = HabitDetailsFragmentDirections.actionHabitDetailsToEditHabit(args.habitId)
            findNavController().navigate(action)
        }
    }
    fun deleteHabit() {
        binding.mbDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_habit_title))
                .setMessage(getString(R.string.delete_habit_message))
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                    viewModel.deleteHabit(args.habitId)
                    dialog.dismiss()
                    findNavController().popBackStack()
                }.show()
        }
    }
}
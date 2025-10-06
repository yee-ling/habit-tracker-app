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
import com.example.habittrackerapp.databinding.FragmentHabitDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

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
                    tvCurrentProgress.text = it?.currentProgress.toString()
                    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                        .format(Date(it?.startDate?:System.currentTimeMillis()))
                    tvStartDate.text = dateFormat
                    val endDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    tvEndDate.text = if (it?.endDate != null) {
                        endDateFormat.format(Date(it.endDate))
                    } else {
                        "null"
                    }
                }
            }
        }
        binding.mbEdit.setOnClickListener {
            val action = HabitDetailsFragmentDirections.actionHabitDetailsToEditHabit(args.habitId)
            findNavController().navigate(action)
        }
        binding.mbDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete this habit?")
                .setMessage("This habit will be permanently removed, all progress made will also be deleted.")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Delete") { dialog, _ ->
                    viewModel.deleteHabit(args.habitId)
                    dialog.dismiss()
                    findNavController().popBackStack()
                }.show()
        }
    }
}
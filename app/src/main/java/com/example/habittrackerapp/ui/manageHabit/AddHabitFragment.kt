package com.example.habittrackerapp.ui.manageHabit

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.data.model.Frequency
import kotlinx.coroutines.launch
import java.util.Calendar

class AddHabitFragment : BaseManageFragment() {
    override val viewModel: AddHabitViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_habit", Bundle())
                findNavController().popBackStack()
            }
        }

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
        //date picker
//        val datePicker: DatePicker = binding.datePicker
//        val today = Calendar.getInstance()
//        datePicker.init(
//            today.get(Calendar.YEAR),
//            today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//        ) {view, year, month, day ->
//        }

        binding.run {
            mbSubmit.setOnClickListener {
                val name = etName.text.toString()

                val frequency = when(rgFrequency.checkedRadioButtonId) {
                    rbDaily.id -> Frequency.DAILY
                    rbWeekly.id -> Frequency.WEEKLY
                    rbMonthly.id -> Frequency.MONTHLY
                    rbYearly.id -> Frequency.YEARLY
                    else -> Frequency.DAILY
                }
                viewModel.addHabit(
                    name = name,
                    frequency = frequency
                )
            }
        }
    }

}
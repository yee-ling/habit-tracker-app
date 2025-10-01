package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
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
        habitRepeatCounter()
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
                val count = tvCount.text.toString().toInt()
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, datePicker.year)
                    set(Calendar.MONTH, datePicker.month)
                    set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val startDate = selectedDate.timeInMillis
                viewModel.addHabit(
                    name = name,
                    frequency = frequency,
                    count = count,
                    startDate = startDate
                )
            }
        }
    }
}
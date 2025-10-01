package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.model.Frequency
import kotlinx.coroutines.launch

class EditHabitFragment() : BaseManageFragment() {

    override val viewModel: EditHabitViewModel by viewModels()
    private val args: EditHabitFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHabitById(args.habitId)
        habitRepeatCounter()
        lifecycleScope.launch {
            viewModel.habit.collect {
                binding.run {
                    etName.setText(it?.name)
                    val habitFrequency = it?.frequency
                    val frequency = when(habitFrequency) {
                        Frequency.DAILY -> rbDaily.id
                        Frequency.WEEKLY -> rbWeekly.id
                        Frequency.MONTHLY -> rbMonthly.id
                        Frequency.YEARLY -> rbYearly.id
                        else -> rbDaily.id
                    }
                    rgFrequency.check(frequency)
                    mbSubmit.text = getString(R.string.update)
                }
            }
        }
    }
}
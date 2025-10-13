package com.example.habittrackerapp.ui.dialog

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import java.util.Calendar

class EndDateDialogFragment : BaseDialogFragment() {
    override val viewModel: EndDateDialogViewModel by viewModels()
    private val args: EndDateDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mbCancel.setOnClickListener {
            dismiss()
        }
        setupEndDatePicker()
        val cal = Calendar.getInstance().apply {
            timeInMillis = args.endDate
        }
        binding.datePicker.updateDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        binding.datePicker.minDate = Calendar.getInstance().timeInMillis
    }
    fun setupEndDatePicker() {
        binding.run {
            tvPlaceholder.text = getString(R.string.end_date)
            mbApply.setOnClickListener {
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, datePicker.year)
                    set(Calendar.MONTH, datePicker.month)
                    set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                }
                val endDate = selectedDate.timeInMillis
                findNavController().previousBackStackEntry?.savedStateHandle?.
                set(getString(R.string.endDate), endDate)
                dismiss()
            }
        }
    }
}
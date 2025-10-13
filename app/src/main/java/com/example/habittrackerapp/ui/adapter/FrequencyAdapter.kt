package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.databinding.ItemLayoutMyhabitBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FrequencyAdapter(
    private var habits: List<Habit>
) : RecyclerView.Adapter<FrequencyAdapter.FrequencyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FrequencyViewHolder {
        val binding = ItemLayoutMyhabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FrequencyViewHolder(binding)
    }
    override fun onBindViewHolder(
        holder: FrequencyViewHolder,
        position: Int
    ) {
        val habit = habits[position]
        holder.bind(habit)
    }
    override fun getItemCount() = habits.size
    fun setHabits(habits: List<Habit>) {
        this.habits = habits
        notifyDataSetChanged()
    }
    inner class FrequencyViewHolder(
        private val binding: ItemLayoutMyhabitBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.run {
                tvName.text = habit.name
                tvCategory.text = habit.category.toString()
                tvRepeats.text = habit.repeatsPerDay.toString()
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                tvStartDate.text = formatter.format(Date(habit.startDate))
                tvEndDate.text = habit.endDate?.let {
                    formatter.format(Date(habit.endDate))
                } ?: binding.root.context.getString(R.string.no_end_date)
            }
        }
    }
}
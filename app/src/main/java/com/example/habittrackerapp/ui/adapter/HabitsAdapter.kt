package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.databinding.ItemLayoutHabitBinding

class HabitsAdapter(
    private var habits: List<HabitWithProgress>,
    private var selectedDate: Long,
    private var onClick: (Habit) -> Unit,
    private var onCheckboxClick: (HabitWithProgress) -> Unit
) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val binding = ItemLayoutHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }
    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        val habit = habits[position]
        holder.bind(habit)
    }
    override fun getItemCount() = habits.size
    fun setHabits(habitWithProgress: List<HabitWithProgress>) {
        this.habits = habitWithProgress
        notifyDataSetChanged()
    }
    fun setSelectedDate(date: Long) {
        selectedDate = date
        notifyDataSetChanged()
    }
    inner class HabitViewHolder(
        private val binding: ItemLayoutHabitBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(habitWithProgress: HabitWithProgress) {
            val habit = habitWithProgress.habit
            val progressForSelectedDate = habitWithProgress.progress.find { it.date == selectedDate }
            binding.run {
                tvName.text = habit.name
                tvFrequency.text = habit.frequency.toString()
                tvProgress.text = (progressForSelectedDate?.progress?:0).toString()
                tvRepeats.text = habit.repeatsPerDay.toString()
                tvCompleted.visibility = if(progressForSelectedDate?.isCompleted == true) View.VISIBLE
                else View.GONE
                llHabit.setOnClickListener {
                    onClick(habit)
                }
                circularCheckbox.max = habit.repeatsPerDay
                circularCheckbox.progress = progressForSelectedDate?.progress ?: 0
                circularCheckbox.setOnClickListener {
                    onCheckboxClick(habitWithProgress)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
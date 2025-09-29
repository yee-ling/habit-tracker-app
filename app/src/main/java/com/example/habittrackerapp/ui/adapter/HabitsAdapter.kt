package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.databinding.ItemLayoutHabitBinding

class HabitsAdapter(
    private var habits: List<Habit>
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
    fun setHabits(habits: List<Habit>) {
        this.habits = habits
        notifyDataSetChanged()
    }
    inner class HabitViewHolder(
        private val binding: ItemLayoutHabitBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.run {
                tvName.text = habit.name
                tvFrequency.text = habit.frequency.toString()
            }
        }
    }
}
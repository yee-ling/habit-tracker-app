package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseManageViewModel(
    protected val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    protected val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
//    abstract fun submit()
}
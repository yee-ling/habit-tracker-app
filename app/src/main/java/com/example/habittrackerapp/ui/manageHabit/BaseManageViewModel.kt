package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseManageViewModel(
    protected val repo: HabitsRepoRoom
) : ViewModel() {
    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    protected val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
    protected val _count = MutableStateFlow(1)
    val count = _count.asStateFlow()
    fun increment() {
    _count.value++
    }
    fun decrement() {
        if(_count.value > 1) {
            _count.value--
        }
    }
}
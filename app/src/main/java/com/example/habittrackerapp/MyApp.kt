package com.example.habittrackerapp

import android.app.Application
import androidx.room.Room
import com.example.habittrackerapp.data.db.MyDatabase
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import com.google.android.material.color.DynamicColors

class MyApp: Application() {
    lateinit var repo: HabitsRepoRoom
    override fun onCreate() {
        super.onCreate()
//        DynamicColors.applyToActivitiesIfAvailable(this)
        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        ).build()
        repo = HabitsRepoRoom(db.getHabitsDao(), db.getProgressDao())
    }
}
package com.example.mynotes.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.mynotes.models.TaskList

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    lateinit var onListAdded: (() -> Unit)

    val lists: MutableList<TaskList> by lazy {
        retrieveLists()
    }

    private fun retrieveLists(): MutableList<TaskList> {
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (tasklist in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(tasklist.value as HashSet<String>)
            val list = TaskList(tasklist.key, itemsHashSet)
            taskLists.add(list)
        }
        return taskLists
    }

    fun saveList(list: TaskList) {
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }

    fun updateList(list: TaskList) {
    sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        refreshLists()
    }

    fun refreshLists() {
        lists.addAll(retrieveLists())
    }
}
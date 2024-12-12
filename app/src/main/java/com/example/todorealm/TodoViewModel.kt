package com.example.todorealm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todorealm.models.TodoItem
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.launch

class TodoViewModel: ViewModel() {
    val realm = MyApp.realm

    fun createEntry(item: String, isCompleted: Boolean){

        viewModelScope.launch {
            realm.write {
                val toDoItem = TodoItem().apply {
                    description = item
                    iscompleted = isCompleted

                }

                copyToRealm(toDoItem, updatePolicy = UpdatePolicy.ALL)


            }
        }

    }

}
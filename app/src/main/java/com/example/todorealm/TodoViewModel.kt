package com.example.todorealm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todorealm.models.TodoItem
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel: ViewModel() {
    val realm = MyApp.realm

    val toDos = realm
        .query<TodoItem>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    var todoItem: TodoItem? by mutableStateOf(null)
        private set

    fun showtodoItems(item: TodoItem){
        todoItem = item

    }

    fun hidetodoItems(){
        todoItem = null
    }






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
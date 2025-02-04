package com.example.todorealm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todorealm.models.TodoItem
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

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


    var selectedItem: TodoItem? by mutableStateOf(null)
       private set
    var showDeleteDialog by mutableStateOf(false)
        private set
    fun showDeleteDioalog(item: TodoItem){
        selectedItem = item
        showDeleteDialog = true
    }
    fun dismissDeleteDialog(){
        showDeleteDialog = false
        selectedItem = null

    }



    fun createEntry(title: String, item: String){

        viewModelScope.launch {
            realm.write {
                val toDoItem = TodoItem().apply {
                    this.title = title
                    description = item
                    this.date = Date()

                }

                copyToRealm(toDoItem, updatePolicy = UpdatePolicy.ALL)


            }
        }

    }

    fun updateEntry(item: TodoItem, newTitle: String, newDescription: String) {
        realm.writeBlocking {
            findLatest(item)?.apply {
                title = newTitle
                description = newDescription
            }
        }
    }

    /*fun deleteItem(item: TodoItem){
        realm.writeBlocking {
            findLatest(item)?.also {
                delete(it)
            }
        }
    }*/

    fun deleteItem() {
        val item = selectedItem?: return
        realm.writeBlocking {
            val toDelete = query<TodoItem>("_id == $0", item._id).first().find()
            toDelete?.let {
                delete(it)
            }
        }
    }

}
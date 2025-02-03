package com.example.todorealm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todorealm.models.TodoItem

@Composable
fun ListPage(viewModel: TodoViewModel){
    var inputText by remember { mutableStateOf("") }
    var isComplete by remember { mutableStateOf(false) }

    val todos by viewModel.toDos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(value = inputText, onValueChange ={inputText = it},
                modifier = Modifier.weight(1f))

            Button(onClick = {
                viewModel.createEntry(inputText, isComplete)
            }) {
                Text(text = "ADD")

            }

        }

        todos.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(todos){ item ->
                    TodoItem(item = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                viewModel.showtodoItems(item)
                            })

                }

            }

        }

    }
}

@Composable
fun TodoItem(item: TodoItem, modifier: Modifier){
    Row(
        modifier = modifier
    ) {
        Text(text = item.description,
            fontSize = 14.sp)
        
        IconButton(onClick = {  }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete" )
            
        }

    }


}
package com.example.todorealm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todorealm.models.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPage(viewModel: TodoViewModel){
    var inputText by remember { mutableStateOf("") }
    val isComplete by remember { mutableStateOf(false) }
    val todos by viewModel.toDos.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "TODOs",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp))
        })
    }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = inputText, onValueChange ={inputText = it},
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(text = "Enter a task...")}, shape = RoundedCornerShape(16.dp)
                )

                Button(onClick = {
                    if (inputText.isNotEmpty()){
                        viewModel.createEntry(inputText, isComplete)
                        inputText = ""
                    }

                }, modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(text = "ADD")

                }

            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), contentAlignment = Alignment.Center){

                if (todos.isEmpty()) {
                    Text(text = "No tasks available", fontSize = 18.sp, color = Color.Gray)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(todos) { item ->
                            TodoItem(
                                item = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { viewModel.showtodoItems(item) },
                                onClickDelete = { viewModel.showDeleteDioalog(item) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Delete Dialog
    if (viewModel.showDeleteDialog) {
        DeleteDialog(
            onDeleteDismiss = { viewModel.dismissDeleteDialog() },
            onConfirm = {
                viewModel.deleteItem()
                viewModel.dismissDeleteDialog()
            }
        )
    }

    // View Task Dialog
    if (viewModel.todoItem != null) {
        AddDialog(
            item = viewModel.todoItem!!,
            onDismiss = { viewModel.hidetodoItems() }
        )
    }
}


@Composable
fun TodoItem(item: TodoItem, modifier: Modifier, onClickDelete: () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onClickDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.size(22.dp)

                )
            }
        }
    }
}

/*@Composable
fun TodoItem(item: TodoItem, modifier: Modifier, onClickDelete: () -> Unit){
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.description,
            fontSize = 20.sp)
        
        IconButton(onClick = onClickDelete ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete",
                modifier=Modifier.size(19.dp))
            
        }
    }
}*/

@Composable
fun AddDialog(item: TodoItem, onDismiss: () ->Unit){
    Dialog(onDismissRequest = onDismiss ) {
        Card(
            modifier = Modifier.wrapContentSize()
                .padding(16.dp), shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.description, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            }

        }

    }
}
@Composable
fun DeleteDialog(onDeleteDismiss: () -> Unit, onConfirm: () -> Unit){
    Dialog(onDismissRequest = onDeleteDismiss ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Confirm Deletion", fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick =  onDeleteDismiss ) {
                        Text("Cancel")
                    }
                    TextButton(onClick = onConfirm ) {
                        Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
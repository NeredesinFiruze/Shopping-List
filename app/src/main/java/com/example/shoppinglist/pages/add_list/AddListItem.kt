@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.shoppinglist.pages.add_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppinglist.data.model.ListOfGroceries
import com.example.shoppinglist.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListItem(
    navController: NavController,
    name: String,
    color: Int,
    viewModel: AddListViewModel = hiltViewModel()
) {
    var addItem by remember { mutableStateOf(false) }
    var replaceItem by remember { mutableStateOf(false) }
    var listName by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(1) }

    viewModel.state.value.name = name
    viewModel.state.value.color = color

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                ){
            TextField(
                value = viewModel.state.value.searchItem,
                onValueChange = {
                    viewModel.onEvent(AddListEvent.AddItem(it))
                    viewModel.searchItem(it)
                },
                placeholder = {
                    Text(text = "Add item...")
                },
                singleLine = true,
                maxLines = 1
            )
            AnimatedVisibility(visible = viewModel.state.value.searchItem.isNotEmpty()) {
                LazyColumn{
                    items(viewModel.listOfGroceries.value){

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 56.dp, end = 56.dp)
                                .clickable {
                                    viewModel.state.value.searchItem = ""
                                    addItem = true
                                    listName = it.nameOfGroceries
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = it.nameOfGroceries,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 4.dp, start = 8.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(visible = addItem) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp, end = 56.dp)
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = listName, fontSize = 16.sp)

                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(text = "x${count}", fontSize = 16.sp)
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            count++
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            if (count >= 1) {
                                                count -= 1
                                            }
                                        }
                                )
                            }
                        }
                        Button(
                            onClick = {
                                viewModel.onEvent(AddListEvent.AddToList(listName, count))
                                count = 1
                                listName = ""
                                addItem = false
                            }
                        ) {
                            Text(text = "Add")
                        }
                    }
                }
            }
            AnimatedVisibility(visible = replaceItem) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .padding(start = 56.dp, end = 56.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = listName, fontSize = 16.sp)

                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(text = "x${count}", fontSize = 16.sp)
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            count++
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clickable {
                                            if (count >= 1) {
                                                count -= 1
                                            }
                                        }
                                )
                            }
                        }
                        Button(
                            onClick = {
                                viewModel.onEvent(AddListEvent.ReplaceItem(listName, count))
                                count = 1
                                listName = ""
                                replaceItem = false
                            }
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            LazyRow{
                items(viewModel.state.value.list){
                    RowItem(
                        it.nameOfGroceries,
                        it.count,
                        ListOfGroceries.listOfColor[viewModel.state.value.color]
                    ){
                        replaceItem = true
                        listName = it.nameOfGroceries
                        count = it.count
                    }
                }
            }
        }
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = "Done",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 88.dp, end = 12.dp)
                .clip(CircleShape)
                .size(66.dp)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .clickable {
                    viewModel.onEvent(AddListEvent.SaveToDatabase)
                    navController.navigate(Screen.List.route)
                },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun RowItem(name: String, count: Int, color: Color,onClick: ()-> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .border(3.dp, color = color, CircleShape)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .clip(CircleShape),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name,Modifier.padding(vertical = 4.dp, horizontal = 10.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "x${count}",Modifier.padding(end = 8.dp))
        }
    }
}
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.shoppinglist.pages.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppinglist.data.model.ListOfGroceries
import com.example.shoppinglist.navigation.Screen
import com.example.shoppinglist.ui.theme.Pink500000
import java.util.*

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true){
        viewModel.onEvent(ListEvent.ShowList)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            LazyColumn{
                items(viewModel.state.value.showList){ list ->
                    ListItems(
                        name = list.name,
                        color = list.color,
                        percent = list.percent,
                        itemToRemove = {
                            viewModel.onEvent(ListEvent.RemoveList(list))
                            viewModel.onEvent(ListEvent.ShowList)
                        }
                    ){
                        navController.navigate(Screen.EditItem.getId(list.id!!))
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(bottom = 90.dp, end = 12.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .align(Alignment.BottomEnd)
                .clickable {
                    navController.navigate(Screen.AddList.route)
                }
        ) {
            Text(
                text = "Create New List",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp)
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add list",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
                    .size(22.dp)
            )
        }
    }
}

@Composable
fun ListItems(
    name: String,
    color: Int,
    percent: Float,
    itemToRemove: ()-> Unit,
    onClick: ()-> Unit) {

    val float = remember {
        mutableStateOf(0f)
    }
    val animateFloat by animateFloatAsState(
        targetValue = float.value,
        animationSpec = tween(500)
    )

    LaunchedEffect(key1 = true){
        float.value += percent
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ListOfGroceries.listOfColor[color])
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = name.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                fontSize = 26.sp,
                color = Pink500000,
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable {
                        itemToRemove()
                    }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = animateFloat,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .size(10.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))

    }
    Spacer(modifier = Modifier.height(8.dp))
}
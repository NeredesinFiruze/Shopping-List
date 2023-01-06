package com.example.shoppinglist.pages.edit_item

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.data.model.ListOfGroceries
import kotlinx.coroutines.launch

@Composable
fun EditItem(
    viewModel: EditViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val backgroundAnimatable = remember{
        Animatable(
            Color(viewModel.state.value.color)
        )
    }
    val a = remember{mutableStateOf(false)}
    val animateVisibility = remember{mutableStateOf(true)}

    LaunchedEffect(key1 = a.value){
        viewModel.onEvent(EditEvent.Save)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAnimatable.value)
    ) {
    Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(125.dp))
            Text(
                text = viewModel.state.value.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 12.dp)
            )
        Spacer(modifier = Modifier.height(45.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            LazyColumn {
                items(viewModel.state.value.list) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = it.isCheck,
                                onCheckedChange = {_ ->
                                    scope.launch {
                                        it.isCheck = !it.isCheck
                                        viewModel.onEvent(EditEvent.Save)
                                        a.value = !a.value
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = it.nameOfGroceries,
                                fontSize = 20.sp,
                                style = if (it.isCheck) {
                                    TextStyle(textDecoration = TextDecoration.LineThrough)
                                } else {
                                    TextStyle()
                                },
                                color = if (it.isCheck) Color.Gray else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(visible = animateVisibility.value) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        animateVisibility.value = false
                    }
            ) {
                Text(
                    text = "Change Color",
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        }
        AnimatedVisibility(visible = !animateVisibility.value) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.68f)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            Color(
                                ColorUtils
                                    .blendARGB(
                                        backgroundAnimatable.value.toArgb(),
                                        0x000000,
                                        0.1f
                                    )
                            )
                        )
                        .padding(4.dp)
                ) {
                    for (i in 1..ListOfGroceries.listOfColor.size) {

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .shadow(15.dp, CircleShape)
                                .clip(CircleShape)
                                .background(ListOfGroceries.listOfColor[i - 1])
                                .clickable {
                                    scope.launch {
                                        backgroundAnimatable.animateTo(
                                            targetValue = ListOfGroceries.listOfColor[i - 1],
                                            animationSpec = tween(600)
                                        )
                                    }
                                    viewModel.onEvent(EditEvent.ChangeColor(i - 1))
                                    println(viewModel.state.value.color)
                                }
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }
        }}
    }
}
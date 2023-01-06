@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.shoppinglist.pages.add_list

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppinglist.data.model.ListOfGroceries.listOfColor
import com.example.shoppinglist.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddList(
    navController: NavController,
    viewModel: AddListViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val backgroundAnimatable = remember{
        Animatable(
            Color(viewModel.state.value.color)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAnimatable.value)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
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
                for (i in 1..listOfColor.size) {

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(listOfColor[i - 1])
                            .clickable {
                                scope.launch {
                                    backgroundAnimatable.animateTo(
                                        targetValue = listOfColor[i - 1],
                                        animationSpec = tween(600)
                                    )
                                }
                                viewModel.onEvent(AddListEvent.ChangeColor(i - 1))
                                println(viewModel.state.value.color)
                            }
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
        }
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            TextField(
                value = viewModel.state.value.name,
                onValueChange = {
                    viewModel.onEvent(AddListEvent.AddName(it))
                },
                placeholder = {
                    Text(text = "Enter List Name")
                },
                modifier = Modifier
                    .background(Color(ColorUtils.blendARGB(backgroundAnimatable.value.toArgb(), 0x000000, 0.1f)))
            )
        }
        Button(
            onClick = {
                navController.navigate(Screen.AddListItem.getInfo(
                    viewModel.state.value.name,
                    viewModel.state.value.color,
                ))
            },
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
           Text(text = "Next")
        }
    }
}
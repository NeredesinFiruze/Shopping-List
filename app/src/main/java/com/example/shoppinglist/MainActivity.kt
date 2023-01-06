package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.navigation.NavListSetup
import com.example.shoppinglist.navigation.NavSettingSetup
import com.example.shoppinglist.pages.list.ListEvent
import com.example.shoppinglist.pages.list.ListViewModel
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.util.BottomMenuContent
import dagger.hilt.android.AndroidEntryPoint

@Suppress("OPT_IN_IS_NOT_ENABLED")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        setContent {
            ShoppingListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            Text(
                                text = "Lists",
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 12.dp)
                            )
                        }
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Column (
                                Modifier.padding(it)
                                    ){
                                if (!viewModel.state.value.navSetup){
                                    NavListSetup(navHostController = rememberNavController())
                                }else NavSettingSetup(navHostController = rememberNavController())
                            }
                            BottomNavigation(
                                items = listOf(
                                    BottomMenuContent(
                                        title = "List",
                                        iconId = Icons.Default.List
                                    ),
                                    BottomMenuContent(
                                        title = "Settings",
                                        iconId = Icons.Default.Settings
                                    )
                                ),
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    initialSelectedItemIndex: Int = 0,
    viewModel: ListViewModel = hiltViewModel()
) {
    var selectedItemIndex by remember { mutableStateOf(initialSelectedItemIndex) }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex
            ) {
                selectedItemIndex = index
                viewModel.onEvent(ListEvent.NavSetup)
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    onItemClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onItemClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) MaterialTheme.colorScheme.onSecondary else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = item.iconId,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else Color.Gray
        )
    }
}
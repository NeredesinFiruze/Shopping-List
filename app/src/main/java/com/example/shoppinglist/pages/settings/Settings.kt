package com.example.shoppinglist.pages.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.core.content.ContextCompat.startActivity
import com.example.shoppinglist.R

@Composable
fun Settings() {
    
    var animatedVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(45.dp)
                .clip(RoundedCornerShape(30.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            animatedVisibility = !animatedVisibility
                        }
                ){
                    Spacer(modifier = Modifier.height(45.dp))
                    Text(
                        text ="Check My Github Account",
                        fontSize = 45.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(lineHeight = 45.sp)
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                }
                AnimatedVisibility(visible = animatedVisibility) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val openURL = Intent(Intent.ACTION_VIEW)
                                openURL.data = Uri.parse("https://www.github.com/NeredesinFiruze")
                                startActivity(context,openURL,null)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.github_icon),
                            modifier = Modifier
                                .size(160.dp)
                                .padding(12.dp)
                                .clip(CircleShape),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
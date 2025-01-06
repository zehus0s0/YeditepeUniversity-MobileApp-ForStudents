package com.example.myapplication.Views.ChatList

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.DataLayer.Models.ChatData
import com.example.myapplication.DataLayer.Models.UserData
import com.example.myapplication.R
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import coil.compose.AsyncImage
import com.example.myapplication.Utilities.Constants
import java.util.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onChatClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ChatListViewModel = viewModel()
    val chats by viewModel.chats.observeAsState(emptyList())
    var showNewChatDialog by remember { mutableStateOf(false) }
    val users by viewModel.users.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Private Chats") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TitleCircle
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
        ) {
            val circleRadius = 800.dp.toPx()
            val circleCenterX = size.width / 2
            val circleCenterY = -circleRadius + 210f

            drawCircle(
                color = Constants.hubBabyBlue,
                radius = circleRadius,
                center = Offset(circleCenterX, circleCenterY)
            )
        }

        // İçerik
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            TopAppBar(
                title = { 
                    Text(
                        "Chats", 
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 100.dp)
                    ) 
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Geri git */ },
                        modifier = Modifier
                            .padding(start = 0.dp, top = 0.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Geri", 
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .height(48.dp)
                    .padding(top = 0.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tabs with elevation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Private Chats", "Group Chats").forEach { tab ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = tab,
                            modifier = Modifier
                                .clickable { selectedTab = tab }
                                .padding(vertical = 12.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = if (selectedTab == tab) Constants.hubGreen else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chat listesi
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(chats.filter {
                    when (selectedTab) {
                        "Private Chats" -> it.chatType == "PRIVATE"
                        "Group Chats" -> it.chatType == "GROUP"
                        else -> true
                    }
                }) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = { onChatClick(chat.chatId) }
                    )
                }
            }
        }

        // FAB
        FloatingActionButton(
            onClick = { showNewChatDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Constants.hubBabyBlue
        ) {
            Icon(Icons.Default.Add, contentDescription = "Yeni Sohbet", tint = Color.White)
        }
    }

    if (showNewChatDialog) {
        NewChatDialog(
            users = users,
            onDismiss = { showNewChatDialog = false },
            onUserSelected = { user ->
                viewModel.createNewChat(user)
                showNewChatDialog = false
            }
        )
    }
}

@Composable
fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(46.dp)
            .width(196.dp)
            .padding(horizontal = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF3F3F3)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            color = if (selected) Constants.hubGreen else Constants.hubGreen,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ChatListItem(
    chat: ChatData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Constants.hubBabyBlue, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.chatName.firstOrNull()?.uppercase() ?: "?",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.chatName,
                style = MaterialTheme.typography.titleMedium,
                color = Constants.hubDark
            )
            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 82.dp),
        color = Color.LightGray,
        thickness = 0.5.dp
    )
}

fun formatTimestamp(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val now = Date()
    val diff = now.time - date.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "${days.toInt()} gün önce"
        hours > 0 -> "${hours.toInt()} saat önce"
        minutes > 0 -> "${minutes.toInt()} dakika önce"
        else -> "Şimdi"
    }
}

@Composable
fun NewChatDialog(
    users: List<UserData>,
    onDismiss: () -> Unit,
    onUserSelected: (UserData) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Yeni Sohbet") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(users) { user ->
                    UserListItem(
                        user = user,
                        onClick = { onUserSelected(user) }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

@Composable
fun UserListItem(
    user: UserData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.displayName.firstOrNull()?.uppercase() ?: "?",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}




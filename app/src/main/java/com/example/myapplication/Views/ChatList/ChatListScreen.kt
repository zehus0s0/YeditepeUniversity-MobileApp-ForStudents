package com.example.myapplication.Views.ChatList

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

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Chats", color = Constants.hubDark) },
                    navigationIcon = {
                        IconButton(onClick = { /* Geri git */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Constants.hubDark)
                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Private Chats", "Group Chats").forEach { tab ->
                        Text(
                            text = tab,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { selectedTab = tab }
                                .background(
                                    if (selectedTab == tab) Constants.hubBabyBlue.copy(alpha = 0.1f)
                                    else Color.Transparent
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (selectedTab == tab) Constants.hubGreen
                                   else Constants.hubDark
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showNewChatDialog = true },
                containerColor = Constants.hubBabyBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Sohbet", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            // Top Bar
            TopAppBar(
                title = { Text("Chats", color = Constants.hubDark) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri", tint = Constants.hubDark)
                    }
                }
            )

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Private Chats", "Group Chats").forEach { tab ->
                    Text(
                        text = tab,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { }
                            .background(
                                if (tab == "Private Chats") Constants.hubBabyBlue.copy(alpha = 0.1f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (tab == "Private Chats") Constants.hubGreen
                               else Constants.hubDark
                    )
                }
            }

            // Chat List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(3) { index ->
                    ChatListItem(
                        chat = ChatData(
                            chatId = index.toString(),
                            chatName = when(index) {
                                0 -> "Ahmet Yılmaz"
                                1 -> "VCD 471 Grup"
                                else -> "Merve Çaşkurlu"
                            },
                            chatType = if(index == 1) "GROUP" else "PRIVATE",
                            lastMessage = when(index) {
                                0 -> "Merhaba, ödev hakkında konuşabilir miyiz?"
                                1 -> "Proje teslim tarihi ne zaman?"
                                else -> "Teşekkür ederim"
                            },
                            lastMessageTimestamp = Timestamp(Date()),
                            participants = listOf("1", "2")
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
} 
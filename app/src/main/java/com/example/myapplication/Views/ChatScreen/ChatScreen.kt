package com.example.myapplication.Views.ChatScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.DataLayer.Models.MessageData
import com.example.myapplication.Utilities.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Chat ekranı, mesajları gösterir ve mesaj gönderme butonu içerir.
 */

@Composable
fun ChatView(chatId: String) {
    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory(chatId))
    // Rest of the implementation
    val message by chatViewModel.message.observeAsState("")
    val messages by chatViewModel.messages.observeAsState(emptyList())
    val firestoreConnectionStatus by chatViewModel.firestoreConnectionStatus.observeAsState("")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Firestore bağlantı durumu
        Text(
            text = "Firestore Status: $firestoreConnectionStatus",
            modifier = Modifier.padding(8.dp)
        )

        // Mesaj Listesi
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(8.dp),
            reverseLayout = true
        ) {
            items(messages) { messageData ->
                SingleMessage(
                    messageData = messageData,
                    isCurrentUser = messageData.senderId == Firebase.auth.currentUser?.uid
                )
            }
        }

        OutlinedTextField(
            value = message,
            onValueChange = { newValue -> chatViewModel.updateMessage(newValue) }, // Doğru format
            label = { Text("Type your message") },
            trailingIcon = {
                IconButton(onClick = { chatViewModel.addMessage() }) { // onClick doğru türde olmalı
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        )


        // Firestore Bağlantı Testi Butonu
        Button(
            onClick = { chatViewModel.testFirestoreReadConnection() },
            modifier = Modifier
                .padding(50.dp)
        ) {
            Text("Test Firestore Connection")
        }
    }
}

@Composable
fun SingleMessage(messageData: MessageData, isCurrentUser: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = messageData.message,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (isCurrentUser) Constants.hubGreen else Constants.hubDarkBlue
                )
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

package com.example.myapplication.DataLayer.Models

import com.google.firebase.Timestamp

// ChatData: Ana sohbet dokümanını temsil eder
data class ChatData(
    val chatId: String,                 // Unique chat ID
    val chatName: String = "",
    val chatType: String = "",          // PRIVATE or GROUP
    val lastMessage: String = "",
    val lastMessageTimestamp: Timestamp? = null,
    val participants: List<String> = emptyList()
)

// MessageData: Bir mesajı temsil eder
data class MessageData(
    val messageId: String = "",              // Firestore'daki message document ID
    val chatId: String = "",                 // Bağlı olduğu chatId
    val senderId: String = "",
    val message: String = "",
    val timestamp: Timestamp? = null
)

